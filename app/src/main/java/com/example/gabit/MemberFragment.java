package com.example.gabit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberFragment extends Fragment {
    private LinearLayout memberListLayout;
    private TextView userNickname;
    private String userId;
    private MemberController memberController;

    public void setMemberController(MemberController memberController) {
        this.memberController = memberController;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member, container, false);
        memberListLayout = view.findViewById(R.id.MemberList);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");

        if (memberController == null) {
            memberController = new MemberController(new MemberService());
        }
        fetchMember(userId);

        return view;
    }

    void fetchMember(String userId) {
        memberController.fetchMember(userId, new MemberService.MemberCallback() {
            @Override
            public void onSuccess(List<Member> members) {
                populateGridWithMember(members);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("MemberFragment", errorMessage);
            }
        });
    }

    private void populateGridWithMember(List<Member> members) {
        if (members == null || members.isEmpty()) {
            Log.e("MemberFragment", "No members found or member list is null.");
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        memberListLayout.removeAllViews();  // 이전 뷰들을 제거

        for (Member member : members) {
            View memberView = inflater.inflate(R.layout.member_item, memberListLayout, false);

            TextView userNickname = memberView.findViewById(R.id.MemberNickname);
            userNickname.setText(member.getUserName());

            memberView.setOnClickListener(v -> {
                MissionListFragment missionListFragment = new MissionListFragment();
                Bundle bundle = new Bundle();

                bundle.putString("userId", String.valueOf(member.getUserId()));
                bundle.putString("userName", member.getUserName());
                missionListFragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main_container, missionListFragment)
                        .addToBackStack(null)
                        .commit();
            });

            memberListLayout.addView(memberView);
        }
    }

    public static class MemberResponse {
        @SerializedName("status")
        public int status;

        @SerializedName("message")
        public String message;

        @SerializedName("data")
        public List<Member> data;

        @NonNull
        @Override
        public String toString() {
            return "MemberResponse{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public static class Member {
        @SerializedName("userId")
        private int userId;

        @SerializedName("userName")
        private String userName;

        @NonNull
        @Override
        public String toString() {
            return "Member{" +
                    "userId=" + userId +
                    ", userName='" + userName + '\'' +
                    '}';
        }

        public int getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }
    }
}
