package com.example.jetpack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jetpack.room.MyDataBase;
import com.example.jetpack.room.Student;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MyLocationListener myLocationListener;


    private MyDataBase myDataBase;
    private List<Student> studentList;
    private StudentAdapter mStudentAdapter;

    @BindView(R.id.lvStudent)
    ListView lvStudent;


    @Override
    public void initViews() {
        myLocationListener = new MyLocationListener(this, new MyLocationListener.OnLocationChangedListener() {
            @Override
            public void onChanged(double latitude, double longitude) {
                Log.i(TAG, "The latitude and longitude is " + latitude + " * " + longitude);
            }
        });
        getLifecycle().addObserver(myLocationListener);
        studentList = new ArrayList<>();
        mStudentAdapter = new StudentAdapter(MainActivity.this, studentList);
        lvStudent.setAdapter(mStudentAdapter);
        lvStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteOrUpdateDialog(studentList.get(position));
                return false;
            }
        });
        myDataBase = MyDataBase.getInstance(this);
        new QueryStudentTask().execute();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @OnClick(R.id.mine)
    void onMineClick() {
        Intent intent = new Intent(this, MineActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.insert_button)
    void onInsetClicked(){
        openAddStudentDialog();
    }



    private void DeleteOrUpdateDialog(final Student student){
        final String[] options = new String[]{"Update", "Delete"};
        new AlertDialog.Builder(this)
                .setTitle("")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            openUpdateStudentDialog(student);
                        } else {
                            new DeleteStudentTask(student).execute();
                        }
                    }
                }).show();

    }

    private void openUpdateStudentDialog(final Student student){
        if (student == null){
            return;
        }
        View customView = this.getLayoutInflater().inflate(R.layout.dialog_layout_student, null);
        final EditText etName = customView.findViewById(R.id.etName);
        final EditText etAge = customView.findViewById(R.id.etAge);
        etName.setText(student.name);
        etAge.setText(student.age);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Update Student");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etAge.getText().toString())){
                    Toast.makeText(MainActivity.this,"输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new UpdateStudentTask(student.id, etName.getText().toString(), etAge.getText().toString()).execute();
                }
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(customView);
        dialog.show();
    }



    private void openAddStudentDialog(){
        View customView = this.getLayoutInflater().inflate(R.layout.dialog_layout_student, null);
        final EditText etName = customView.findViewById(R.id.etName);
        final EditText etAge = customView.findViewById(R.id.etAge);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Add Student");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etAge.getText().toString())){
                    Toast.makeText(MainActivity.this,"输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new InsertStudentTask(etName.getText().toString(), etAge.getText().toString()).execute();
                }
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(customView);
        dialog.show();
    }


    /**
     * Insert Student Task Async Task Thread
     */
    private class InsertStudentTask extends AsyncTask<Void, Void, Void>{
        String name;
        String age;

        public InsertStudentTask(final String name, final String age){
            this.name = name;
            this.age = age;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myDataBase.studentDao().insertStudent(new Student(name, age));
            studentList.clear();
            studentList.addAll(myDataBase.studentDao().getStudentList());
            return null;
        }
    }



    /**
     * Update Student Task Async Task Thread
     */
    private class UpdateStudentTask extends AsyncTask<Void, Void, Void>{
        int id;
        String name;
        String age;

        public UpdateStudentTask(final int id, final String name, final String age){
            this.id = id;
            this.name = name;
            this.age = age;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myDataBase.studentDao().updateStudent(new Student(id, name, age));
            studentList.clear();
            studentList.addAll(myDataBase.studentDao().getStudentList());
            return null;
        }
    }


    /**
     * Delete Student Task Async Task Thread
     */
    private class DeleteStudentTask extends AsyncTask<Void, Void, Void>{
        Student student;

        public DeleteStudentTask(Student student){
            this.student = student;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myDataBase.studentDao().deleteStudent(student);
            studentList.clear();
            studentList.addAll(myDataBase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mStudentAdapter.notifyDataSetChanged();
        }
    }


    /**
     * Query Student Task Async Task Thread
     */
    private class QueryStudentTask extends AsyncTask<Void, Void, Void> {
        public QueryStudentTask() {

        }


        @Override
        protected Void doInBackground(Void... voids) {
            studentList.clear();
            studentList.addAll(myDataBase.studentDao().getStudentList());
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mStudentAdapter.notifyDataSetChanged();
        }
    }
}