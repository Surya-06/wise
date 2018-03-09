package com.example.myapp1.ogranized;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {
    public RecyclerView mRecyclerView;
    public WordListAdapter mAdapter;
    private Intent mRequestIntent;
    private ParcelFileDescriptor mInputPDF;

    // private ArrayList<String> mWordList = new ArrayList<String>();


    ArrayList<folder_values> main_contents;
         /*saving files
        public MainActivity() throws FileNotFoundException {
        file = new File(this.getFilesDir(), String.valueOf(file));
        String filename="my file";
        String fileContents="hello world";
                FileOutputStream outputStream;
        try {
            outputStream = openFileOutput("file", MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
        outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent received_intent = getIntent();
        if (received_intent != null) {
            Bundle received_values = received_intent.getBundleExtra("updated_intent");
            if (received_values != null)
                main_contents = (ArrayList<folder_values>) received_values.get("updated_folders");

            else
                Toast.makeText(this, "bundle is null , so you are done", Toast.LENGTH_SHORT).show();
        }

        this.mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new WordListAdapter(this, main_contents);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        final Intent mRequestFileIntent = new Intent(Intent.ACTION_PICK);
         mRequestFileIntent.setType("image/jpg");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                //intent.putStringArrayListExtra("folder_names",mWordList);

                Bundle send_content = new Bundle();
                send_content.putSerializable("folder_names", main_contents);
                intent.putExtra("bundled_values", send_content);

                startActivity(intent);
            }

            protected void requestFile()
            {
                /*user requests file, send an intent to server app */
            startActivityForResult(mRequestFileIntent,0);}

            public void onActivityResult(int requestCode, int resultCode, Intent returnIntent)

            {
                if(resultCode!= RESULT_OK) {
                    return;
                }
                else {
                    Uri returnUri = returnIntent.getData();
                    try {
                        mInputPDF = getContentResolver().openFileDescriptor(returnUri, "r");
                    }
                    catch(FileNotFoundException e)
                    {
                        e.printStackTrace();
                        Log.e("MainActivity","File Not Found");
                        return;

                    }
                    FileDescriptor fd=mInputPDF.getFileDescriptor();

                }





            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean saveArray() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sp.edit();
        Toast.makeText(MainActivity.this, "saving array ", Toast.LENGTH_SHORT).show();

        // saving size of files
        editor.putInt("files_size", main_contents.size());
        Gson gson;
        gson = new Gson();
        String json = gson.toJson(main_contents);
        editor.putString("main_contents", json);
        editor.commit();

        return true;


    }
    public boolean retrieveArray() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Gson gson=new Gson();
        String json= sp.getString("main_contents", "");
        folder_values mc = gson.fromJson(json, (Type) main_contents.getClass());
        return true;

    }
    File file;
}




