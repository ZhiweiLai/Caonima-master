package com.example.zhiweilai.caonima;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.file.OpenOption;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private DatabaseReference db;
    private FirebaseHelper helper;

    private TaskAdapter taskAdapter;

    private EditText editTaskName;
    private EditText editCreator;
    private EditText editReciptent;
    private EditText editStarted;
    private EditText editDeadline;
    private EditText editNote;
    private EditText editEquip;
    private EditText editPoints;

    private Button buttonAddTask;

    private EditText taskName, creator, recipent, start, deadline, note, equip, points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_task_list);

        listView=(ListView)findViewById(R.id.listView);

        db= FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);

        taskAdapter=new TaskAdapter(this, helper.retrieve());

        listView.setAdapter(taskAdapter);

        buttonAddTask=(Button) findViewById(R.id.button_add);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task tmp;
                tmp=(Task)taskAdapter.getItem(i);
                infoDialog(tmp);
            }
        });

    }

    private void displayInputDialog(){
        Dialog dialog =new Dialog(MainActivity.this);

        dialog.setTitle("New Task");
        dialog.setContentView(R.layout.layout_add_new_task);

        editTaskName=(EditText)dialog.findViewById(R.id.editText_add1);
        editCreator=(EditText)dialog.findViewById(R.id.editText_add2) ;
        editReciptent=(EditText)dialog.findViewById(R.id.editText_add_re) ;
        editStarted=(EditText)dialog.findViewById(R.id.editText_add3) ;
        editDeadline=(EditText)dialog.findViewById(R.id.editText_add4) ;
        editNote=(EditText)dialog.findViewById(R.id.editText_add5) ;
        editEquip=(EditText)dialog.findViewById(R.id.editText_add6) ;
        editPoints=(EditText)dialog.findViewById(R.id.editText_add8) ;

        Button saveTask =(Button)dialog.findViewById(R.id.button_save);

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String taskName=editTaskName.getText().toString();
                String creator=editCreator.getText().toString();
                String reciptent=editReciptent.getText().toString();
                String start=editStarted.getText().toString();
                String deadline=editDeadline.getText().toString();
                String notes=editNote.getText().toString();
                String equip=editEquip.getText().toString();
                String points=editPoints.getText().toString();

                Task task =new Task();

                task.setTaskName(taskName);
                task.setCreator(creator);
                task.setRecipent(reciptent);
                task.setStartDate(start);
                task.setDeadline(deadline);
                task.setNote(notes);
                task.setEquipment(equip);
                task.setPoints(points);

                if(taskName!=null && creator!=null && start!=null && points!=null){
                    if (helper.saved(task)){

                        editTaskName.setText("");
                        editCreator.setText("");
                        editReciptent.setText("");
                        editStarted.setText("");
                        editDeadline.setText("");
                        editNote.setText("");
                        editEquip.setText("");
                        editPoints.setText("");

                        taskAdapter= new TaskAdapter(MainActivity.this, helper.retrieve());
                        listView.setAdapter(taskAdapter);
                    }
                }

            }
        });

        dialog.show();
    }

    private void infoDialog(Task task) {
//        final Dialog d=new Dialog(MainActivity.this);
//        d.setTitle("Task");
//        d.setContentView(R.layout.layout_show_info_task);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View d = inflater.inflate(R.layout.layout_show_info_task, null);
        builder.setView(d);

        Button completed = (Button) d.findViewById(R.id.button_completed);
        final Button delete = (Button) d.findViewById(R.id.button_delete);
        final Button updated = (Button) d.findViewById(R.id.button_update);

        builder.setTitle("Task");
        final AlertDialog b = builder.create();
        b.show();

        taskName = (EditText) d.findViewById(R.id.Text_add1);
        creator = (EditText) d.findViewById(R.id.Text_add2);
        recipent = (EditText) d.findViewById(R.id.Text_add_re);
        start = (EditText) d.findViewById(R.id.Text_add3);
        deadline = (EditText) d.findViewById(R.id.Text_add4);
        note = (EditText) d.findViewById(R.id.Text_add5);
        equip = (EditText) d.findViewById(R.id.Text_add6);
        points = (EditText) d.findViewById(R.id.Text_add8);

        taskName.setText(task.getTaskName());
        creator.setText(task.getCreator());
        recipent.setText(task.getRecipent());
        start.setText(task.getStartDate());
        deadline.setText(task.getDeadline());
        note.setText(task.getNote());
        equip.setText(task.getEquipment());
        points.setText(task.getPoints());


//        d.show();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletion(taskName.getText().toString());
                b.dismiss();
            }
        });

        updated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                b.dismiss();
            }
        });
    }

    private void deletion(String taskName){
        DatabaseReference dd= FirebaseDatabase.getInstance().getReference("Task").child(taskName);
        dd.removeValue();

        Toast.makeText(MainActivity.this,"Task is deleted", Toast.LENGTH_LONG).show();
    }

    protected  void onStart(){
        super.onStart();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Task> tmp=helper.retrieve();
                tmp.clear();
                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    Task task=dataSnapshot.getValue(Task.class);
                    tmp.add(task);

                }
                TaskAdapter newOnew=new TaskAdapter(MainActivity.this, tmp);
                listView.setAdapter(newOnew);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void update(){
        String newName=taskName.getText().toString();
        String newCreator=creator.getText().toString();
        String newRe=recipent.getText().toString();
        String newS=start.getText().toString();
        String newD=deadline.getText().toString();
        String newN=note.getText().toString();
        String newE=equip.getText().toString();
        String newP=points.getText().toString();

        Task newTask=new Task(newName,newCreator,newRe,newS,newD,newN,newE,newP);
        helper.updateProduct(newTask);
        Toast.makeText(MainActivity.this, "Task Updated", Toast.LENGTH_LONG).show();

    }

}
