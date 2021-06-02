package com.example.latihansqlitefaza;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.latihansqlitefaza.adapter.RecyclerViewAdapter;
import com.example.latihansqlitefaza.database.DatabaseHelper;
import com.example.latihansqlitefaza.models.PersonBean;

import java.util.List;

public class MainFragment extends Fragment implements
        View.OnClickListener, RecyclerViewAdapter.OnUserClickListener {

    RecyclerView recyclerView;
    EditText editName, editAge;
    Button btnSubmit;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    List<PersonBean> listPersonBean;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = this.getActivity();
        this.recyclerView = view.findViewById(R.id.recycleview);
        this.layoutManager = new LinearLayoutManager(this.context);
        this.recyclerView.setLayoutManager(layoutManager);

        this.editName = view.findViewById(R.id.editname);
        this.editAge = view.findViewById(R.id.editage);
        this.btnSubmit = view.findViewById(R.id.btnsubmit);

        this.btnSubmit.setOnClickListener(this);
        this.setupRecyclerView();
    }

    private void setupRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(this.context);
        this.listPersonBean = db.selectUserData();

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.context, this.listPersonBean, this);
        this.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnsubmit) {
            DatabaseHelper db = new DatabaseHelper(this.context);
            PersonBean currentPerson = new PersonBean();
            String btnStatus = this.btnSubmit.getText().toString();

            if(btnStatus.equals("Submit")) {
                currentPerson.setName(editName.getText().toString());
                currentPerson.setAge(Integer.parseInt(editAge.getText().toString()));
                db.insert(currentPerson);
            }

            if(btnStatus.equals("Update")) {
                currentPerson.setName(editName.getText().toString());
                currentPerson.setAge(Integer.parseInt(editAge.getText().toString()));
                db.update(currentPerson);
            }

            this.setupRecyclerView();
            this.editName.setText("");
            this.editAge.setText("");
            this.editName.setFocusable(true);
            this.btnSubmit.setText("Submit");
        }
    }

    @Override
    public void onUserClick(PersonBean currentPerson, String action) {
        if(action.equals("Edit")) {
            this.editName.setText(currentPerson.getName());
            this.editName.setFocusable(true);
            this.editAge.setText(currentPerson.getAge() + "");
            this.btnSubmit.setText("Update");
        }

        if(action.equals("Delete")) {
            DatabaseHelper db = new DatabaseHelper(this.context);
            db.delete(currentPerson.getName());
            this.setupRecyclerView();
        }
    }
}