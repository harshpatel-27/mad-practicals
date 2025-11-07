package com.example.userregistrationapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> userList;
    UserActionListener listener;

    public interface UserActionListener {
        void onDelete(User user);
        void onEdit(User user, int position);
    }

    public UserAdapter(Context context, ArrayList<User> userList, UserActionListener listener) {
        this.context = context;
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.txtName.setText(user.name);
        holder.txtEmail.setText(user.email);
        holder.txtPassword.setText(user.password);

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete User")
                    .setMessage("Are you sure you want to delete this user?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        listener.onDelete(user);
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        holder.btnEdit.setOnClickListener(v -> {
            listener.onEdit(user, position);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail, txtPassword;
        Button btnDelete, btnEdit;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPassword = itemView.findViewById(R.id.txtPassword);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}