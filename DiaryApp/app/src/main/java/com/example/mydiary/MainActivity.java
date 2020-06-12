package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydiary.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private String title;
    private String content;
    private long diaryId = -1;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DbOpenHelper dbHelper = new DbOpenHelper(this);
        mDb = dbHelper.getWritableDatabase();



        binding.buttonApply.setOnClickListener(v -> {

            title = String.valueOf(binding.editTitle.getText());
            content = String.valueOf(binding.editContent.getText());

            insertDatabase();
        });
    }

    private Cursor getDiaryCursor() {
        DbOpenHelper dbHelper = DbOpenHelper.getsInstance(this);

        return dbHelper.getReadableDatabase()
                .query(Databases.DBdata.TABLENAME,
                        null, null, null, null, null, null);
    }

    private static class DiaryAdapter extends CursorAdapter {

        public DiaryAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView titleText = view.findViewById(android.R.id.text1);
            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(Databases.DBdata.TITLE)));
        }
    }

    private void insertDatabase() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Databases.DBdata.TITLE, title);
        contentValues.put(Databases.DBdata.CONTENT, content);

        SQLiteDatabase db = DbOpenHelper.getsInstance(this).getWritableDatabase();

        if(diaryId == -1) {
            long newRowId = db.insert(Databases.DBdata.TABLENAME, null, contentValues);

            if(newRowId == -1) {
                Toast.makeText(this, "save error", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        }
    }


}
