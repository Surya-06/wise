package com.example.myapp1.ogranized;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Sireesha on 2/17/2018.
 */
public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>   {

    private LayoutInflater mInflater;
    private Context current_context;
    private ArrayList<folder_values> received_folders;

    public WordListAdapter(Context context, ArrayList<folder_values> folder_list ) {
        //Toast.makeText(context, "inside contructor", Toast.LENGTH_SHORT).show();
        mInflater = LayoutInflater.from(context);
        this.current_context = context;
        this.received_folders = folder_list;
    }
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wordlist_item,parent,false);
        return new WordViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(WordListAdapter.WordViewHolder holder, int position) {
        final folder_values folder = received_folders.get(position);
        holder.word.setText(folder.subject_name);
        //Toast.makeText(current_context, (CharSequence) folder.data,Toast.LENGTH_LONG).show();

        holder.word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_folder= new Intent(current_context,generic_activity.class);
                open_folder.putExtra("folder" , folder);
                current_context.startActivity(open_folder);
            }
        });
    }

    @Override
    public int getItemCount() {
        if ( received_folders==null )
                return 0;
        return received_folders.size();
    }


    public class WordViewHolder extends RecyclerView.ViewHolder {
            Button word;

            public WordViewHolder ( View itemView ) {
            super(itemView);
            this.word = (Button) itemView.findViewById(R.id.content_open);
        }

    }
}