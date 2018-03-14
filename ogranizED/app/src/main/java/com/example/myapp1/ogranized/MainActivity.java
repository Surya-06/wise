package com.example.myapp1.ogranized;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import static java.security.AccessController.getContext;
import android.support.v4.content.*;

public class MainActivity extends AppCompatActivity {
    public RecyclerView mRecyclerView;
    public WordListAdapter mAdapter;
    private Intent mRequestIntent;
    private ParcelFileDescriptor mInputPDF;
    AutoCompleteTextView act;
    Intent mRequestFileIntent;


    ArrayList<folder_values> main_contents;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // this does not work as intended
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ( retrieveArray() ) {} // no activity since only restoration of data is done

        mRequestFileIntent = new Intent(Intent.ACTION_PICK);
        mRequestFileIntent.setType("image/jpg");

        Intent received_intent = getIntent();
        if (received_intent != null) {
            Bundle received_values = received_intent.getBundleExtra("updated_intent");
            if (received_values != null) {
                main_contents = (ArrayList<folder_values>) received_values.get("updated_folders");
            }
            else{}
        }

        this.mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new WordListAdapter(this, main_contents);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this , 2));
        mRecyclerView.setAdapter(mAdapter);
        final Intent mRequestFileIntent = new Intent(Intent.ACTION_PICK);
         mRequestFileIntent.setType("image/jpg");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                Bundle send_content = new Bundle();
                send_content.putSerializable("folder_names", main_contents);
                intent.putExtra("bundled_values", send_content);
                startActivity(intent);
            }
    protected void requestFile()
            {
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
    protected void onDestroy() {
        super.onDestroy();
        if ( saveArray() )
            Toast.makeText(this, "save successful", Toast.LENGTH_SHORT).show();
        return;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean saveArray() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor edit = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson ( new wrapper ( main_contents ) );
        edit.putString("store_contents",json);
        edit.commit();
        return true;
    }
    public boolean retrieveArray () {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String json = sp.getString("store_contents","");
        Gson gson = new Gson();
        wrapper temp = gson.fromJson(json , wrapper.class );
        if ( temp!=null )
        main_contents = temp.temp_values;
        else{}
        return true;
    }
}
class wrapper {
    public ArrayList<folder_values> temp_values;
    public wrapper (ArrayList<folder_values> temp_values) {
        this.temp_values = temp_values;
    }
}

