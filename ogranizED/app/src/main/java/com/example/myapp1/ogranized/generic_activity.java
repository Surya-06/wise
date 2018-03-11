package com.example.myapp1.ogranized;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.File;

public class generic_activity extends AppCompatActivity {
    private File mPrivateRootDir;
    // The path to the "images" subdirectory
    private File mImagesDir;
    // Array of files in the images subdirectory
    File[] mImageFiles;

    Object fileUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_activity);

        Intent received_value = getIntent();
        folder_values show_folder = (folder_values) received_value.getSerializableExtra("folder");

        TextView subject_name = findViewById(R.id.subject_name);
        subject_name.setText(show_folder.subject_name);

        RecyclerView content = findViewById(R.id.file_display_recycler_view);
        adapter_files content_adapter = new adapter_files(generic_activity.this, show_folder);
        content.setLayoutManager(new LinearLayoutManager(this));
        content.setAdapter(content_adapter);
    }

}
