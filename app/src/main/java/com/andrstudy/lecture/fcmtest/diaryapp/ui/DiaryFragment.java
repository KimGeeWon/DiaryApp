package com.andrstudy.lecture.fcmtest.diaryapp.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.andrstudy.lecture.fcmtest.diaryapp.ListViewAdapter;
import com.andrstudy.lecture.fcmtest.diaryapp.R;
import com.andrstudy.lecture.fcmtest.diaryapp.database.Databases;
import com.andrstudy.lecture.fcmtest.diaryapp.database.DbOpenHelper;
import com.andrstudy.lecture.fcmtest.diaryapp.databinding.FragmentDiaryWriteBinding;

public class DiaryFragment extends Fragment {

    private FragmentDiaryWriteBinding binding;

    private DbOpenHelper helper;
    private SQLiteDatabase db;
    private String tag = "SQLite";
    private int dbVersion = 1;

    ListViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDiaryWriteBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            helper = new DbOpenHelper(requireContext());

            db = helper.getsInstance(requireContext()).getWritableDatabase();
            select();
        }
        catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag, "데이터베이스를 얻어올 수 없음");
        }

        binding.buttonApply.setOnClickListener(v->{
            String title = binding.editTitle.getText().toString();
            String content = binding.editContent.getText().toString();

            if ("".equals(title)){
                Toast.makeText(requireContext(), "제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                return;// 그냥 빠져나감
            }

            insert(title, content);

            binding.editTitle.setText("");
            binding.editContent.setText("");
        });

        binding.dbListView.setOnItemLongClickListener((parent, _view, position, id)->{

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("삭제");
            builder.setMessage("삭제 하시겠습니까?");
            builder.setPositiveButton("예",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(requireContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                            TextView t = (TextView) view.findViewById(R.id.id);
                            String _id = t.getText().toString();
                            delete(String.valueOf(_id));
                        }
                    });
            builder.setNegativeButton("아니오",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(requireContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                        }
                    });
            builder.show();

            return true;
        });
    }

    void delete(String id) {
        int result = db.delete(Databases.DBdata.TABLENAME, "id=?", new String[] {id});
        Log.d(tag, result + "개 row delete 성공");

        select(); // delete 후에 select 하도록
        //adapter.notifyDataSetChanged();
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

        select (); // 업데이트 후에 조회하도록
    }

    void select () {

        adapter = new ListViewAdapter();

        binding.dbListView.setAdapter(adapter);

        Cursor c = db.query(Databases.DBdata.TABLENAME, null, null, null, null, null, null);

        while(c.moveToNext()) {
            int id = c.getInt(0);
            String title = c.getString(1);
            String content = c.getString(2);
//            String created = c.getString(3);
//
//            String arr[] = created.split(" ");
//
//            Log.i(tag, arr[0]); // 날짜
//            Log.i(tag, arr[1]); // 시간

            adapter.addItem(
                    title, content, String.valueOf(id));

            Log.i(tag, String.valueOf(adapter));

            Log.d(tag,"id:"+id+",title:"+title
                    +",content:"+content);

            // 키보드 내리기
//            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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
