package layout;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.cameronweigel.todolist.R;

import Model.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskItemFragment extends Fragment {

    private EditText taskTitle;
    private EditText taskBody;

    private String TAG = "TaskItemFragment";

    TaskItemFragmentListener mCallback;

    public interface TaskItemFragmentListener {
        public void onTaskItemAddition();
    }

    public TaskItemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FloatingActionButton fab = getActivity().findViewById(R.id.addTaskFab);
        fab.hide();

        return inflater.inflate(R.layout.fragment_task_item, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        FloatingActionButton fab = getActivity().findViewById(R.id.addTaskFab);
        fab.show();
        mCallback.onTaskItemAddition();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.actionbar,menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (TaskItemFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TaskItemFragmentListener");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.saveActionBarButton:
                onCheckButtonClick();
                getFragmentManager().popBackStack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onCheckButtonClick() {

        String title, body;

        Task task = new Task();
        taskTitle = getActivity().findViewById(R.id.editTextTitle);
        taskBody = getActivity().findViewById(R.id.editTextBody);

        title = taskTitle.getText().toString();
        body = taskBody.getText().toString();

    if (!(title.isEmpty() && body.isEmpty())) {

        task.setTaskTitle(taskTitle.getText().toString());
        task.setTaskBody(taskBody.getText().toString());
        task.taskUpdate();
        Log.d(TAG, "After Realm commit");

    } else {
        Toast.makeText(getActivity(), "Title and Body must not be empty", Toast.LENGTH_LONG).show();
    }

        Log.d("task check", task.getTaskBody() + task.getTaskTitle());


    }
}
