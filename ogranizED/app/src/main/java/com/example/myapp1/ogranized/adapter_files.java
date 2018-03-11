package com.example.myapp1.ogranized;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Sireesha on 2/21/2018.
 */

public class adapter_files extends RecyclerView.Adapter<adapter_files.files_view_holder> {
    folder_values folder_contents;
    Context current_context;
    ArrayList<File> files;
    public adapter_files (Context current_context , folder_values folder ){
        this.current_context = current_context ;
        this.folder_contents = folder ;
        this.files = folder.data;
    }

    @Override
    public files_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(current_context);
        View temp_view = layoutInflater.inflate(R.layout.file_layout,parent,false);
        return new files_view_holder(temp_view);
    }

    @Override
    public void onBindViewHolder(files_view_holder holder, int position) {
        final File current_file = files.get(position);
        //holder.show_file.setText(current_file.getName());
        String path=current_file.getAbsolutePath();
        File imgfile=new File(path);
        holder.show_file.setText(path);
        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog=new AlertDialog.Builder(current_context);
                alertDialog.setTitle("Choose an option");

                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                             //delete
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });
        holder.show_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(current_context,  " clicked the file to open , does not work though", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
                Uri uri_creater = Uri.parse ( current_file.getAbsolutePath() );
                intent.setDataAndType(uri_creater,"application/pdf");
                current_context.startActivity(intent);
            }
        });
        Toast.makeText(current_context, Integer.toString(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        if(files==null) return 0;
        return files.size();
    }

    public class files_view_holder extends RecyclerView.ViewHolder{
        Button show_file;
        ImageButton options;
        public files_view_holder(View itemView) {
            super(itemView);
            show_file = itemView.findViewById(R.id.main_file_display);
            options=itemView.findViewById(R.id.options);
        }
    }
}
