package com.example.trabalhon1_mobile;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_g);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewTodo = (ListView) findViewById(R.id.listViewTodo);

        FloatingActionButton buttonAdd = (FloatingActionButton) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("action", "insert");
                startActivity(intent);
            }
        });

        loadingTodo();

        listViewTodo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoList todoList = (TodoList) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("action", "update");
                intent.putExtra("id", todoList.getId());
                startActivity(intent);
            }
        });

        listViewTodo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TodoList todoList = (TodoList) parent.getItemAtPosition(position);
                delete(todoList);
                return true;
            }
        });

    }

    private void delete(final TodoList todo){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Deleted item!");
        alert.setMessage("Confirm the deletion of the " + todo.getName() + " item?");
        alert.setNeutralButton("Cancel", null);
        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TodoListDAO.delete(MainActivity.this, todo.getId());
                loadingTodo();
            }
        });
        alert.show();
    }

    private void loadingTodo() {
        List<TodoList> list = TodoListDAO.select(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listViewTodo.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadingTodo();
    }
}