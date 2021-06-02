package com.example.latihansqlitefaza.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latihansqlitefaza.R;
import com.example.latihansqlitefaza.models.PersonBean;

import java.util.List;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.UserViewHolder> {

    Context context;
    OnUserClickListener listener;
    List<PersonBean> listPersonBean;

    public RecyclerViewAdapter(Context context, List<PersonBean> listPersonBean, OnUserClickListener listener) {
        this.context = context;
        this.listPersonBean = listPersonBean;
        this.listener = listener;
    }

    public interface OnUserClickListener {
        void onUserClick(PersonBean currentPerson, String action);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);

        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        final PersonBean currentPerson = this.listPersonBean.get(position);
        holder.ctxName.setText(currentPerson.getName());
        holder.ctxAge.setText(currentPerson.getAge() + "");
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClick(currentPerson, "Edit");
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClick(currentPerson, "Delete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPersonBean.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView ctxAge, ctxName;
        ImageView imgDelete, imgEdit;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ctxAge = itemView.findViewById(R.id.ctxAge);
            this.ctxName = itemView.findViewById(R.id.ctxName);
            this.imgDelete = itemView.findViewById(R.id.imgdelete);
            this.imgEdit = itemView.findViewById(R.id.imgedit);
        }
    }
}
