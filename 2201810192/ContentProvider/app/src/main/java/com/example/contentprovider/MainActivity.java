package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.net.Uri;
import android.database.Cursor;
import android.content.ContentValues;

public class MainActivity extends AppCompatActivity {

    EditText nameEditText, gradeEditText;
    Button addButton, retrieveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.editText2);
        gradeEditText = findViewById(R.id.editText3);
        addButton = findViewById(R.id.btnAdd);
        retrieveButton = findViewById(R.id.btnRetrieve);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddName();
            }
        });

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRetrieveStudents();
            }
        });
    }

    public void onClickAddName() {
        String name = nameEditText.getText().toString().trim();
        String grade = gradeEditText.getText().toString().trim();

        if(name.isEmpty() || grade.isEmpty()) {
            Toast.makeText(this, "يرجى تعبئة كل الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(StudentsProvider.NAME, name);
        values.put(StudentsProvider.GRADE, grade);

        Uri uri = getContentResolver().insert(StudentsProvider.CONTENT_URI, values);

        Toast.makeText(this, "تمت الإضافة: " + uri.toString(), Toast.LENGTH_LONG).show();

        // تنظيف الحقول بعد الإدخال
        nameEditText.setText("");
        gradeEditText.setText("");
    }

    public void onClickRetrieveStudents() {
        Uri studentsUri = StudentsProvider.CONTENT_URI;
        Cursor cursor = getContentResolver().query(studentsUri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder builder = new StringBuilder();
            do {
                String id = cursor.getString(cursor.getColumnIndex(StudentsProvider._ID));
                String name = cursor.getString(cursor.getColumnIndex(StudentsProvider.NAME));
                String grade = cursor.getString(cursor.getColumnIndex(StudentsProvider.GRADE));
                builder.append("ID: ").append(id)
                        .append(", الاسم: ").append(name)
                        .append(", التقدير: ").append(grade)
                        .append("\n");
            } while (cursor.moveToNext());

            Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
            cursor.close();
        } else {
            Toast.makeText(this, "لا توجد بيانات لعرضها", Toast.LENGTH_SHORT).show();
        }
    }
}