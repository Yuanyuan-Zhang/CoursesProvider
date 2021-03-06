package ca.senecacollege.provider;

import android.app.Activity;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CoursesProvidersActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClickAddTitle(View view) {

        ContentValues values = new ContentValues();
        values.put("title", ((EditText)
                findViewById(R.id.txtTitle)).getText().toString());
        values.put("code", ((EditText)
                findViewById(R.id.txtCode)).getText().toString());
        values.put("classroom", ((EditText)
                findViewById(R.id.txtRoom)).getText().toString());
        Uri uri = getContentResolver().insert(
                Uri.parse(
                        "content://ca.senecacollege.provider.Courses/courses"),
                values);

        Toast.makeText(getBaseContext(),uri.toString(),
                Toast.LENGTH_LONG).show();
    }

    public void onClickRetrieveTitles(View view) {
        //---retrieve the titles---
        Uri allTitles = Uri.parse(
                "content://ca.senecacollege.provider.Courses/courses");

        Cursor c;
        if (android.os.Build.VERSION.SDK_INT <11) {
            //---before Honeycomb---
            c = managedQuery(allTitles, null, null, null,
                    "title desc");
        } else {
            //---Honeycomb and later---
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allTitles, null, null, null,
                    "title desc");
            c = cursorLoader.loadInBackground();
        }

        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(
                                CoursesProvider._ID)) + ", " +
                                c.getString(c.getColumnIndex(
                                        CoursesProvider.TITLE)) + ", " +
                                c.getString(c.getColumnIndex(
                                        CoursesProvider.CODE))+", " +
                                c.getString(c.getColumnIndex(
                                        CoursesProvider.ROOM)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }

    public void updateTitle() {
        ContentValues editedValues = new ContentValues();
        editedValues.put(CoursesProvider.TITLE, "Android Tips and Tricks");
        getContentResolver().update(
                Uri.parse(
                        "content://ca.senecacollege.provider.Courses/courses/2"),
                editedValues,
                null,
                null);
    }

    public void deleteTitle() {

        //---delete a title---
        getContentResolver().delete(
                Uri.parse("content://ca.senecacollege.provider.Courses/courses/2"),
                null, null);


        //---delete all titles---
        getContentResolver().delete(
                Uri.parse("content://ca.senecacollege.provider.Courses/courses"),
                null, null);

    }

}