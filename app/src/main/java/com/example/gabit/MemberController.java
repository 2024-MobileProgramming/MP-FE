package com.example.gabit;

import android.util.Log;

import java.util.List;

public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public void fetchMember(String userId, final MemberService.MemberCallback callback) {
        memberService.getMember(userId, new MemberService.MemberCallback() {
            @Override
            public void onSuccess(List<MemberFragment.Member> members) {
                callback.onSuccess(members);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
                Log.e("MemberController", errorMessage);
            }
        });
    };

}
