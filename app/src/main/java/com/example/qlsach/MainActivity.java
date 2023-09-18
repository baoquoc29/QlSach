package com.example.qlsach;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        Toolbar toolbar = new Toolbar(MainActivity.this);
        db = new SqliteHelper(MainActivity.this);
        list = db.display_view();
        list_clone = db.display_view();
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1, list);
        listView.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int get_id = Integer.valueOf(id.getText().toString());
                String get_name = name.getText().toString();
                int get_page = Integer.valueOf(page.getText().toString());
                float get_price = Float.valueOf(price.getText().toString());
                String get_des = des.getText().toString();
                long newRowId = db.insertData(get_id, get_name, get_page, get_price, get_des);
                if (newRowId != -1) {
                    list.add(new Books(get_id, get_name, get_page, get_price, get_des));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    page.setText("");
                    des.setText("");
                    name.setText("");
                    price.setText("");

                } else {
                    Toast.makeText(MainActivity.this, "Lỗi khi thêm dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Books get_list = list.get(i);
                String values = String.valueOf(get_list);
                String [] array = values.split("/");
                id.setText(array[0]);
                page.setText(array[2]);
                des.setText(array[4]);
                name.setText(array[1]);
                price.setText(array[3]);
                index = i;
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_remove = Integer.valueOf(id.getText().toString());
                db.deleteColumns(id_remove);
                for(int i = 0 ;i<list.size();i++){
                    if(list.get(i).getBookId() == id_remove){
                        list.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int get_id = Integer.valueOf(id.getText().toString());
                String get_name = name.getText().toString();
                int get_page = Integer.valueOf(page.getText().toString());
                float get_price = Float.valueOf(price.getText().toString());
                String get_des = des.getText().toString();
                db.updateColumn(get_id,get_des,get_price,get_name,get_page);
                list.set(index,new Books(get_id, get_name, get_page, get_price, get_des));
                adapter.notifyDataSetChanged();
            }
        });
    search.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
        filter(editable.toString());
        }
    });
    }
    public void filter(String text){
        List<Books> list2 = new ArrayList<>();
        for(Books books : list){
            if(books.getBookname().toLowerCase().contains(text)){
                list2.add(books);
            }
        }
        if(text.isEmpty()){
            adapter.clear();
            adapter.addAll(list_clone);
            adapter.notifyDataSetChanged();
        }
        else{
            adapter.clear();
            adapter.addAll(list2);
            adapter.notifyDataSetChanged();
        }
    }
    public void mapping(){
        id = findViewById(R.id.bookID);
        name = findViewById(R.id.name);
        page = findViewById(R.id.page);
        des = findViewById(R.id.des);
        price = findViewById(R.id.price);
        btn = findViewById(R.id.clickAdd);
        listView = findViewById(R.id.listView);
        edit = findViewById(R.id.clickEdit);
        remove = findViewById(R.id.clickRemove);
        list_clone = list;
        search = findViewById(R.id.search);
    }
    private EditText search;
    private Button remove;
    private Button edit;
    private EditText id;
    private EditText name;
    private EditText page;
    private EditText des;
    private EditText price;
    private Button btn;
    private ListView listView;
    private SqliteHelper db;
    private ArrayAdapter<Books> adapter;
    private   int index;
    private List<Books> list = new ArrayList<>();;
    private Button btn_search;
    private List<Books> list_clone = new ArrayList<>();


}