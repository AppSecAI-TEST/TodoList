package com.cameronweigel.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import layout.DeleteDialog;
import layout.ListFragment;

/**
 * Created by cameronweigel on 7/15/17.
 */

public class Task_Adapter extends RealmRecyclerViewAdapter<Task, Task_Adapter.TaskViewHolder> {

    private OrderedRealmCollection<Task> taskData;
    private Context context;
    private FragmentManager fragmentManager;


    public interface onItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }



    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskTitle, taskBody;


        public TaskViewHolder(final View itemView) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.titleTextView);
            taskBody =  itemView.findViewById(R.id.bodyTextView);


        }

    }


    public Task_Adapter(final OrderedRealmCollection<Task> realmResults, Context context, FragmentManager fragmentManager) {
        super(realmResults, true);
        this.taskData = realmResults;
        this.context = context;
        this.fragmentManager = fragmentManager;


    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row, parent, false);

        return new TaskViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder,final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                Log.d("InsideLongClick",holder.taskTitle.getText().toString());
                //TODO add alert dialog
                DeleteDialog deleteDialog = new DeleteDialog();
                deleteDialog.show(fragmentManager, "DeleteDialog");
                return true;
            }
        });

        Task item = getItem(position);
        if (item != null) {
            holder.taskTitle.setText(item.getTaskTitle());
            holder.taskBody.setText(item.getTaskBody());
        }
    }

    public int getItemCount() {
        return taskData.size();
    }


}