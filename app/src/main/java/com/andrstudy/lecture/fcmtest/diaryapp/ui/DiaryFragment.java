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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DiaryFragment extends Fragment {

    private FragmentDiaryWriteBinding binding;

    private DbOpenHelper helper;
    private SQLiteDatabase db;
    private String tag = "SQLite";
    private int dbVersion = 1;

    private String strId;

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
            String btn = (String) binding.buttonApply.getText();

            String title = binding.editTitle.getText().toString();
            String content = binding.editContent.getText().toString();

            if(btn.equals("확인")) {
                if ("".equals(title)){
                    Toast.makeText(requireContext(), "제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;// 그냥 빠져나감
                }

                insert(title, content);

                binding.editTitle.setText("");
                binding.editContent.setText("");
            }
            else {
                update(strId, title, content);

                binding.buttonApply.setText("확인");
                binding.editTitle.setText("");
                binding.editContent.setText("");
            }
        });

        binding.imageTimeStamp.setOnClickListener(v->{
            String content = binding.editContent.getText().toString();

            SimpleDateFormat sdf = new  SimpleDateFormat("HH:mm", Locale.KOREA);
            Date _date = new Date();
            TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
            sdf.setTimeZone(tz);

            String date = sdf.format(_date);

            binding.editContent.setText(content + date);
        });

        binding.dbListView.setOnItemClickListener((parent, _view, position, id)->{
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("수정");
            builder.setMessage("수정 하시겠습니까?");
            builder.setPositiveButton("예",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(requireContext(),"상단을 통해 수정해주세요.",Toast.LENGTH_LONG).show();
                            TextView t = (TextView) _view.findViewById(R.id.id);
                            strId = t.getText().toString();
                            changeUpdate();
                        }
                    });
            builder.setNegativeButton("아니오",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(requireContext(),"취소하셨습니다.",Toast.LENGTH_LONG).show();
                        }
                    });
            builder.show();
        });

        binding.dbListView.setOnItemLongClickListener((parent, _view, position, id)->{

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("삭제");
            builder.setMessage("삭제 하시겠습니까?");
            builder.setPositiveButton("예",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(requireContext(),"삭제되었습니다.",Toast.LENGTH_LONG).show();
                            TextView t = (TextView) _view.findViewById(R.id.id);
                            String _id = t.getText().toString();
                            delete(_id);
                        }
                    });
            builder.setNegativeButton("아니오",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(requireContext(),"취소되었습니다.",Toast.LENGTH_LONG).show();
                        }
                    });
            builder.show();

            return true;
        });
    }

    void changeUpdate() {
        binding.buttonApply.setText("수정");
    }

    void delete(String id) {
        int result = db.delete(Databases.DBdata.TABLENAME, "id=?", new String[] {id});

        select(); // delete 후에 select 하도록
    }

    void update (String id, String title, String content) {
        ContentValues values = new ContentValues();
        values.put("title", title);         // 바꿀값
        values.put("content", content);     // 바꿀값

        int result = db.update(Databases.DBdata.TABLENAME,
                values,    // 뭐라고 변경할지 ContentValues 설정
                "id=?", // 바꿀 항목을 찾을 조건절
                new String[]{id});// 바꿀 항목으로 찾을 값 String 배열

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
            String created = c.getString(3);
//

            adapter.addItem(
                    title, content, created, String.valueOf(id));
        }
    }

    void insert (String title, String content) {
        ContentValues values = new ContentValues();
        // 키,값의 쌍으로 데이터 입력
        values.put("title", title);
        values.put("content", content);
        long result = db.insert(Databases.DBdata.TABLENAME, null, values);

        select(); // insert 후에 select 하도록
    }
}
