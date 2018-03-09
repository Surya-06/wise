package com.example.myapp1.ogranized;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main2Activity extends AppCompatActivity {
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main2);
       Button submit = (Button) findViewById(R.id.button);


       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               EditText entered_value = (EditText) findViewById(R.id.et1);
               Editable value = entered_value.getText();
               if ( value.toString().length()==0 )
                   Toast.makeText(Main2Activity.this, "null text entered ", Toast.LENGTH_SHORT).show();
               else {
                   Intent intent = getIntent();
                   Bundle received_bundle = intent.getBundleExtra("bundled_values");
                   ArrayList<folder_values> existing_folders = (ArrayList<folder_values>) received_bundle.get("folder_names");
                   folder_values new_folder = new folder_values();
                   new_folder.subject_name = value.toString();
                   new_folder.data = new ArrayList<>();
                   if(existing_folders!=null)
                        existing_folders.add(new_folder);
                   else{
                       existing_folders = new ArrayList<>();
                       existing_folders.add(new_folder);
                   }
                   Bundle send_bundle = new Bundle();
                   send_bundle.putSerializable("updated_folders",existing_folders);
                   Intent new_intent = new Intent(Main2Activity.this, MainActivity.class);
                   new_intent.putExtra("updated_intent",send_bundle);
                   startActivity(new_intent);
               }
           }
       });

    }
}

