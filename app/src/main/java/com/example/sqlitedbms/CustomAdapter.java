package com.example.sqlitedbms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter <CustomAdapter.MyViewHolder>{


    private List<Model> mPersonName ;
    Context context;
    private ItemClickListener mClickListener;
    public CustomAdapter(Context context, List<Model> PersonName) {
        this.context = context;
        this.mPersonName = PersonName;



    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Model model = mPersonName.get(position);
        holder.nameNumber.setText(model.getName()+"  -  "+model.getPhoneNumber());
        holder.edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ((MainActivity)context).edit_pop(context
                        ,String.valueOf(mPersonName.get(position).getID()), mPersonName.get(position).getName(), mPersonName.get(position).getPhoneNumber());
            }

        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Delete: ", "..............");

                ((MainActivity)context).Delete_pop(String.valueOf(mPersonName.get(position).getID()), mPersonName.get(position).getName(), mPersonName.get(position).getPhoneNumber());

            }
        });


    }

    @Override
    public int getItemCount() {
        return mPersonName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView nameNumber;
        FloatingActionButton delete, edit;
        public MyViewHolder(View itemView) {
            super(itemView);

            nameNumber =  itemView.findViewById(R.id.tvAnimalName);
            delete = (FloatingActionButton)itemView.findViewById(R.id.delete);

            edit = (FloatingActionButton)itemView.findViewById(R.id.edit);

         itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}

