package com.example.myapp1.ogranized;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        File current_file = files.get(position);
        holder.show_file.setText(current_file.getName());
    }

    @Override
    public int getItemCount() {
        if(files==null) return 0;
        return files.size();
    }

    public class files_view_holder extends RecyclerView.ViewHolder{
        Button show_file;
        public files_view_holder(View itemView) {
            super(itemView);
            show_file = itemView.findViewById(R.id.main_file_display);
        }
    }
}
