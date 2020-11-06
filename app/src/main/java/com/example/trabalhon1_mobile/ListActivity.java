package com.example.trabalhon1_mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ListActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private Button add;
    private String action;
    private TodoList todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        add = (Button) findViewById(R.id.add);

        action = getIntent().getExtras().getString("action");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrUpdate();
            }
        });

        if (action.equals("update")) {
            int id = getIntent().getExtras().getInt("id");
            todoList = TodoListDAO.selectItemById(this, id);
            name.setText(todoList.getName());
            email.setText(todoList.getEmail());
        }
    }

    private void addOrUpdate() {
        if (todoList == null) {
            todoList = new TodoList();
        }

        String nameDigited = name.getText().toString();
        String emailDigited = email.getText().toString();
        if (nameDigited.isEmpty() & emailDigited.isEmpty()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Warning!");
            alert.setMessage("Name and email is required");
            alert.setIcon(android.R.drawable.ic_dialog_alert);
            alert.setPositiveButton("OK", null);
            alert.show();
        } else {
            todoList.setName(nameDigited);
            todoList.setEmail(emailDigited);
            if (action.equals("insert")) {
                TodoListDAO.create(this, todoList);
                todoList = null;
                name.setText("");
                email.setText("");
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Success!");
                alert.setMessage("Name and email saved successfully!");
                alert.setIcon(android.R.drawable.ic_menu_save);
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                TodoListDAO.update(this, todoList);
                finish();
            }
        }
    }
}
