package com.example.gabit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gabit.CalendarAdapter;
import com.example.gabit.MissionCalendarViewModel;
import com.example.gabit.databinding.CalendarBinding;

import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment {
    private static final String TAG = CalendarFragment.class.getSimpleName();

    private LinearLayout calendarLayout;
    private RecyclerView calendarView;
    private CalendarAdapter calendarAdapter;

    private MissionCalendarViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int count = 0;
        // DataBinding과 ViewModel 설정
        CalendarBinding binding = DataBindingUtil.inflate(inflater, R.layout.calendar, container, false);
        viewModel = new ViewModelProvider(this).get(MissionCalendarViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // 현재 날짜로 초기화
        Calendar today = Calendar.getInstance();
        int currentYear = today.get(Calendar.YEAR);
        int currentMonth = today.get(Calendar.MONTH)+1; // Calendar.MONTH는 0부터 시작하므로 +1
        Log.d("current Month", String.valueOf(currentMonth));

        // Spinner 설정
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, viewModel.yearList.getValue());
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerYear.setAdapter(yearAdapter);
        int yearIndex = viewModel.yearList.getValue().indexOf(String.valueOf(currentYear));
        binding.spinnerYear.setSelection(yearIndex); // 현재 연도 위치로 설정

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, viewModel.monthList.getValue());
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerMonth.setAdapter(monthAdapter);
        binding.spinnerMonth.setSelection(currentMonth-1); // 현재 월 위치로 설정

        viewModel.selectedYearPosition.setValue(currentYear);
        viewModel.selectedMonthPosition.setValue(currentMonth);
        // Spinner 이벤트 리스너 설정
        binding.spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.selectedYearPosition.setValue(Integer.valueOf(viewModel.yearList.getValue().get(position)));
                viewModel.updateCalendar_model();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.selectedMonthPosition.setValue(position + 1);
                viewModel.updateCalendar_model();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        View view = binding.getRoot();

        initView(view, viewModel); // ViewModel을 initView에 전달합니다.

        // LiveData 관찰 설정
        viewModel.selectedYearPosition.observe(getViewLifecycleOwner(), year -> updateCalendar(viewModel));
        viewModel.selectedMonthPosition.observe(getViewLifecycleOwner(), month -> updateCalendar(viewModel));

        // Setting the initial values for selectedYearPosition and selectedMonthPosition
        // viewModel.selectedYearPosition.setValue(currentYear);
        //viewModel.selectedMonthPosition.setValue(currentMonth);

        return view;
    }

    private void initView(View view, MissionCalendarViewModel viewModel) {
        calendarLayout = view.findViewById(R.id.calendar_layout);
        calendarView = view.findViewById(R.id.calendar_view);

        // 여기에 LayoutManager 설정 코드 추가
        int numberOfColumns = 7; // 달력의 경우 주로 7일(일주일)을 한 줄로 표시하므로 7로 설정
        calendarView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));

        // ViewModel에서 선택된 년도와 월을 가져옵니다.
        int selectedYear = viewModel.selectedYearPosition.getValue();
        int selectedMonth = viewModel.selectedMonthPosition.getValue();

        Log.d("initView Month", String.valueOf(selectedMonth));

        // CalendarAdapter 초기화 및 설정
        calendarAdapter = new CalendarAdapter(calendarLayout, selectedYear, selectedMonth);
        calendarView.setAdapter(calendarAdapter);
    }

    private void updateCalendar(MissionCalendarViewModel viewModel) {
        // ViewModel에서 선택된 년도와 월을 가져옵니다.
        int selectedYear = viewModel.selectedYearPosition.getValue();
        int selectedMonth = viewModel.selectedMonthPosition.getValue();
        Log.d("Update Month", String.valueOf(selectedMonth));
        // CalendarAdapter에 새로운 년도와 월을 설정하고 갱신
        calendarAdapter.setDate(selectedYear, selectedMonth);
        calendarAdapter.notifyDataSetChanged();
    }
}

