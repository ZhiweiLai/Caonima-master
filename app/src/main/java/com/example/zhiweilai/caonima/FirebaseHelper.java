package com.example.zhiweilai.caonima;

/**
 * Created by zhiweilai on 2017-11-27.
 */

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by zhiweilai on 2017-11-27.
 */

public class FirebaseHelper {

    DatabaseReference databaseReference;
    Boolean save;
    ArrayList<Task> tasklist=new ArrayList<Task>();

    public FirebaseHelper(DatabaseReference db){
        this.databaseReference=db;
    }

    public Boolean saved(Task task){
        if (tasklist==null){
            save=false;

        }else {
            try {
                databaseReference.child("Task").push().setValue(task);
                save=true;

            }catch(DatabaseException e){
                e.printStackTrace();
                save=false;
            }
        }
        return save;

    }

    public void fetchData(DataSnapshot dataSnapshot){
        tasklist.clear();

        for (DataSnapshot ds:dataSnapshot.getChildren()){
            Task task=ds.getValue(Task.class);
            tasklist.add(task);
        }
    }

    public ArrayList<Task> retrieve(){
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return tasklist;
    }

    public void updateProduct(Task newItem) {
        //getting the specified product reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(newItem.getTaskName());
        //updating product
//        Product product = new Product(id, name, price);
        dR.setValue(newItem);
//        Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_LONG).show();
    }




}

