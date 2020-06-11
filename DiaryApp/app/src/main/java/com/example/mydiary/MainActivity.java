package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
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

        title = String.valueOf(binding.editTitle.getText());
        content = String.valueOf(binding.editContent.getText());
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
