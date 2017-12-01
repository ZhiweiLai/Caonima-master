package com.example.zhiweilai.caonima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zhiweilai on 2017-11-27.
 */

public class TaskAdapter extends BaseAdapter {

    Context context;
    ArrayList<Task> taskList;

    public TaskAdapter(Context c, ArrayList<Task> a){
        this.context=c;
        this.taskList=a;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.task_mod,viewGroup, false);
        }
        TextView taskName=(TextView)view.findViewById(R.id.title);
        TextView start=(TextView)view.findViewById(R.id.time);
        TextView reciptent=(TextView)view.findViewById(R.id.info);
        Task t=(Task)taskList.get(i);

        taskName.setText(t.getTaskName());
        reciptent.setText(t.getRecipent());
        start.setText(t.getStartDate());

        return view;
    }
}
