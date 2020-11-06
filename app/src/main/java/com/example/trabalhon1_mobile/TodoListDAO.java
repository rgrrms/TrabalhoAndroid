package com.example.trabalhon1_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TodoListDAO {

    public static void create(Context context, TodoList todoList) {

        ContentValues list = new ContentValues();
        list.put("name", todoList.getName());
        list.put("email", todoList.getEmail());

        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getWritableDatabase();

        db.insert("list", null, list);
    }

    public static void update(Context context, TodoList todoList) {

        ContentValues list = new ContentValues();
        list.put("name", todoList.getName());
        list.put("email", todoList.getEmail());

        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getWritableDatabase();

        db.update("list", list, "id = " + todoList.getId(), null);
    }

    public static void delete(Context context, int idTodoList) {

        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getWritableDatabase();

        db.delete("list","id = " + idTodoList, null);
    }

    public static List<TodoList> select(Context context) {
        List<TodoList> list = new ArrayList<>();

        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM list ORDER BY name", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                TodoList todoList = new TodoList();
                todoList.setId(cursor.getInt(0));
                todoList.setName(cursor.getString(1));
                todoList.setEmail(cursor.getString(2));

                list.add(todoList);

            } while (cursor.moveToNext());
        }

        return list;
    }

    public static TodoList selectItemById(Context context, int idTodoList) {

        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM list WHERE id = " + idTodoList, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            TodoList todoList = new TodoList();
            todoList.setId(cursor.getInt(0));
            todoList.setName(cursor.getString(1));
            todoList.setEmail(cursor.getString(2));

            return todoList;

        } else {
            return null;
        }
    }
}
