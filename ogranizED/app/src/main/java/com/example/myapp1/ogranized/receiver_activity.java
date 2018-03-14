package com.example.myapp1.ogranized;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class receiver_activity extends AppCompatActivity {

    ArrayList<folder_values> main_contents;
    Button submit_button ;
    Spinner folder_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_activity);

        // disable some strict exceptions
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        // StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
        Toast.makeText(this, "loading the contents saved earlier", Toast.LENGTH_SHORT).show();
        if ( onload() ){
            Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "FAIL", Toast.LENGTH_SHORT).show();
        folder_spinner = (Spinner) findViewById(R.id.folder_spinner);
        ArrayList<String> content_names = new ArrayList<String>();
        if ( main_contents == null )    { main_contents = new ArrayList<>(); }
        for ( folder_values i : main_contents ) {
            content_names.add(i.subject_name);
        }
        ArrayAdapter< String > spinner_adapter = new ArrayAdapter<String>(this ,android.R.layout.simple_spinner_item , content_names );
        spinner_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        folder_spinner.setAdapter(spinner_adapter);
        final ImageView iv=findViewById(R.id.ivv);
        submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject_name = folder_spinner.getSelectedItem().toString();
                int find = -1;
                for ( int i=0 ;i < main_contents.size() ; i++ ){
                    if ( main_contents.get(i).subject_name.equals(subject_name) ){
                        Toast.makeText(receiver_activity.this, "found current folder", Toast.LENGTH_SHORT).show();
                        find = i;
                        break;
                    }
                }
                if ( find == -1 )
                    Toast.makeText(receiver_activity.this, "empty , no folder found ", Toast.LENGTH_SHORT).show();

                /*
                Toast.makeText(receiver_activity.this, "the type of the field is "+getIntent().getType(), Toast.LENGTH_SHORT).show();
                Uri receivedUri = (Uri)getIntent().getParcelableExtra( Intent.EXTRA_STREAM );
                Toast.makeText(receiver_activity.this, "the data given is "+getIntent().getData(), Toast.LENGTH_SHORT).show();
                Toast.makeText(receiver_activity.this, "the obtained value is "+receivedUri, Toast.LENGTH_SHORT).show();
                //Toast.makeText(receiver_activity.this, "the file path updated is "+receivedUri, Toast.LENGTH_SHORT).show();
                //Toast.makeText(receiver_activity.this, "the paths is "+receivedUri.getPath().toString(), Toast.LENGTH_SHORT).show();
                if (receivedUri != null) {
                    File new_file = new File ( receivedUri.getPath() );
                    Toast.makeText(receiver_activity.this, "Done updating  new files", Toast.LENGTH_SHORT).show();

                    main_contents.get(find).data.add(new_file);
                    Toast.makeText(receiver_activity.this, "updated length is "+main_contents.get(find).data.size(), Toast.LENGTH_SHORT).show();
                    saveArray();

                }
                */

                Intent intent=getIntent();
                if(intent!=null) {
                    String action=intent.getAction();
                    String type=intent.getType();
                    if(Intent.ACTION_SEND.equals(action) ) {
                        Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                        String filepath = uri.getPath();
                        File new_file = new File(filepath);
                        main_contents.get(find).data.add(new_file);
                        Toast.makeText(receiver_activity.this, "Save array", Toast.LENGTH_SHORT).show();
                        saveArray();
                        // TextView temp_text = (TextView) findViewById(R.id.tester_text);
                       // temp_text.setText(filepath);
                    }
                }

                finish();
            }
        });
    }
    public boolean onload  ( ) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String json = sp.getString("store_contents","");
        Gson gson = new Gson();
        //Toast.makeText(this, "the contents of the json file are "+json, Toast.LENGTH_SHORT).show();
        wrapper temp = gson.fromJson(json , wrapper.class );
        if ( temp!=null )
            main_contents = temp.temp_values;
        else
            Toast.makeText(this, "temp is empty , no data stored earlier", Toast.LENGTH_SHORT).show();
        return true;

    }

    public boolean saveArray() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor edit = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson ( new wrapper ( main_contents ) );
        //Toast.makeText(this, "storing contents "+json, Toast.LENGTH_SHORT).show();
        edit.putString("store_contents",json);
        edit.commit();
        return true;
    }



}
