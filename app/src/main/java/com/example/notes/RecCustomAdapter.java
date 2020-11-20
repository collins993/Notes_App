package com.example.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecCustomAdapter extends RecyclerView.Adapter<RecCustomAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<Note> notes;

    public RecCustomAdapter(Context context, Activity activity, ArrayList<Note> notes) {
        this.context = context;
        this.activity = activity;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_adapter, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.noteId.setText(notes.get(position).getId());
        holder.noteTitle.setText(notes.get(position).getTitle());
        holder.noteDate.setText(notes.get(position).getDate());
        holder.noteTime.setText(notes.get(position).getTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditNoteActivity.class);
                intent.putExtra("id", notes.get(position).getId());
                intent.putExtra("title", notes.get(position).getTitle());
                intent.putExtra("content", notes.get(position).getContent());

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteId, noteTitle, noteDate, noteTime;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteId = itemView.findViewById(R.id.txtId);
            noteTitle = itemView.findViewById(R.id.txtNoteTitle);
            noteDate = itemView.findViewById(R.id.txtNoteDate);
            noteTime = itemView.findViewById(R.id.txtNoteTime);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
