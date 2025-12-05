package com.example.labandroiddemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labandroiddemo.database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardAdaptor extends RecyclerView.Adapter<LeaderboardAdaptor.UserViewHolder> {
    private List<User> users = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_leaderboard_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User currentUser = users.get(position);
        holder.usernameTextView.setText(currentUser.getUsername());

        if (currentUser.getLastPlayed() != null) {
            holder.dateTextView.setText(currentUser.getLastPlayed().format(formatter));
        } else {
            holder.dateTextView.setText("-");
        }

        holder.winsTextView.setText(String.valueOf(currentUser.getWins()));
        holder.lossesTextView.setText(String.valueOf(currentUser.getLosses()));
        holder.balanceTextView.setText("$" + currentUser.getBalance());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView usernameTextView;
        private final TextView winsTextView;
        private final TextView lossesTextView;
        private final TextView balanceTextView;
        private final TextView dateTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            winsTextView = itemView.findViewById(R.id.winsTextView);
            lossesTextView = itemView.findViewById(R.id.lossesTextView);
            balanceTextView = itemView.findViewById(R.id.balanceTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
