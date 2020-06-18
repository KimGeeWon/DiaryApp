package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydiary.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private DbOpenHelper helper;
    private SQLiteDatabase db;
    private String tag = "SQLite";
    private int dbVersion = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());      // 버전

        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        //binding.dbListView.setAdapter(adapter);

        ListView listview;

        listview = (ListView) findViewById(R.id.dbListView);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem("Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
        adapter.addItem(
                "Circle", "Account Circle Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem(
                "Ind", "Assignment Ind Black 36dp") ;
        adapter.addItem(
                "Ind", "Assignment Ind Black 36dp") ;
        adapter.addItem(
                "Ind", "Assignment Ind Black 36dp") ;
        adapter.addItem(
                "Ind", "Assignment Ind Black 36dp") ;
        adapter.addItem(
                "Ind", "Assignment Ind Black 36dp") ;

        try {
            db = helper.getsInstance(this).getWritableDatabase();
        }
        catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag, "데이터베이스를 얻어올 수 없음");
            finish(); // 액티비티 종료
        }

        binding.buttonApply.setOnClickListener(v->{
            String title = binding.editTitle.getText().toString();
            String content = binding.editContent.getText().toString();

            if ("".equals(title)){
                Toast.makeText(this, "제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                return;// 그냥 빠져나감
            }

            insert (title, content);
        });
    }

    void delete(String name) {
        int result = db.delete(Databases.DBdata.TABLENAME, "name=?", new String[] {name});
        Log.d(tag, result + "개 row delete 성공");
        select(); // delete 후에 select 하도록
    }

    void update (String name, int age, String address) {
        ContentValues values = new ContentValues();
        values.put("age", age);         // 바꿀값
        values.put("address", address); // 바꿀값

        int result = db.update(Databases.DBdata.TABLENAME,
                values,    // 뭐라고 변경할지 ContentValues 설정
                "name=?", // 바꿀 항목을 찾을 조건절
                new String[]{name});// 바꿀 항목으로 찾을 값 String 배열
        Log.d(tag, result+"번째 row update 성공");

        //binding.dbListView.add();
        select (); // 업데이트 후에 조회하도록
    }

    void select () {
        Cursor c = db.query(Databases.DBdata.TABLENAME, null, null, null, null, null, null);
        while(c.moveToNext()) {
            int _id = c.getInt(0);
            String title = c.getString(1);
            String content = c.getString(2);

            Log.d(tag,"_id:"+_id+",title:"+title
                    +",content:"+content);

            // 키보드 내리기
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    void insert (String title, String content) {
        ContentValues values = new ContentValues();
        // 키,값의 쌍으로 데이터 입력
        values.put("title", title);
        values.put("content", content);
        long result = db.insert(Databases.DBdata.TABLENAME, null, values);
        Log.d(tag, result + "번째 row insert 성공했음");

        select(); // insert 후에 select 하도록
    }
}
