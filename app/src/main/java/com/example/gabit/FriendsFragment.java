package com.example.gabit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {
    private LinearLayout friendsListLayout;
    private TextView userNickname;
    private List<Friends> friends;
    private int userId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        friendsListLayout = view.findViewById(R.id.FriendsList);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);

        friends = fetchFriendsFromServer(userId);
        populateGridWithFriends();

        return view;
    }

    private List<Friends> fetchFriendsFromServer(Integer userId) {
        List<Friends> friendsList = new ArrayList<>();
        // 미션 리스트 추가 예시
        friendsList.add(new Friends("가천짱짱맨", 1));
        friendsList.add(new Friends("모프", 2));
        friendsList.add(new Friends("내가이김", 3));
        friendsList.add(new Friends("개발자내옆", 4));
        friendsList.add(new Friends("클러스터링", 5));
        friendsList.add(new Friends("우땨땨", 6));
        friendsList.add(new Friends("손흥민", 7));
        friendsList.add(new Friends("AI수학", 8));
        friendsList.add(new Friends("듣는중", 9));
        friendsList.add(new Friends("죄송합니다", 10));
        return friendsList;
    }

    private void populateGridWithFriends() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (Friends friends : friends) {
            View friendsView = inflater.inflate(R.layout.friends_item, friendsListLayout, false);

            TextView userNickname = friendsView.findViewById(R.id.FriendsNickname);

            userNickname.setText(friends.getUserNickname());

            friendsListLayout.addView(friendsView);
        }
    }
}
