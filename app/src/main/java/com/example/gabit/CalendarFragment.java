package com.example.gabit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment {
    private static final String TAG = CalendarFragment.class.getSimpleName();

    private String selectedYear;
    private String selectedMonth;
    private LinearLayout calendarLayout;
    private RecyclerView calendarView;
    private CalendarAdapter calendarAdapter;

    private static CalendarFragment instance;

    public static CalendarFragment getInstance() {
        return instance;
    }

    public static CalendarFragment newInstance(String year, String month) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString("YEAR", year);
        args.putString("MONTH", month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        if (getArguments() != null) {
            selectedYear = getArguments().getString("YEAR");
            selectedMonth = getArguments().getString("MONTH");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        calendarLayout = view.findViewById(R.id.calendar_layout);
        calendarView = view.findViewById(R.id.calendar_view);

        // 선택된 년도와 월을 사용하여 Date 객체 생성
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(selectedYear));
        calendar.set(Calendar.MONTH, Integer.parseInt(selectedMonth) - 1); // Calendar.MONTH는 0부터 시작
        calendar.set(Calendar.DAY_OF_MONTH, 1); // 달의 첫날로 설정
        Date selectedDate = calendar.getTime();

        // GridLayoutManager를 사용하여 한 줄에 7개의 아이템이 표시되도록 설정
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 7);
        calendarView.setLayoutManager(gridLayoutManager);

        // CalendarAdapter 초기화 및 설정
        calendarAdapter = new CalendarAdapter(calendarLayout, selectedDate);
        calendarView.setAdapter(calendarAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }
}
