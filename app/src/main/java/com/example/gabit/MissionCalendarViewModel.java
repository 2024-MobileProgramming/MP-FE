package com.example.gabit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MissionCalendarViewModel extends ViewModel {
    public final MutableLiveData<List<String>> yearList = new MutableLiveData<>();
    public final MutableLiveData<List<String>> monthList = new MutableLiveData<>();
    public final MutableLiveData<Integer> selectedYearPosition = new MutableLiveData<>();
    public final MutableLiveData<Integer> selectedMonthPosition = new MutableLiveData<>();

    public MissionCalendarViewModel() {
        // 현재 연도와 월을 기본값으로 설정
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        List<String> years = Arrays.asList("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028");
        List<String> months = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");

        yearList.setValue(years);
        monthList.setValue(months);
        selectedYearPosition.setValue(years.indexOf(String.valueOf(currentYear)));
        selectedMonthPosition.setValue(currentMonth); // 0부터 시작하므로 현재 월 그대로 사용
    }

    public void updateCalendar() {
        // 선택된 연도와 월을 사용하여 달력을 업데이트
        String selectedYear = getSelectedYear();
        String selectedMonth = getSelectedMonth();
    }

    public String getSelectedYear() {
        Integer position = selectedYearPosition.getValue();
        List<String> years = yearList.getValue();
        if (position != null && years != null && position >= 0 && position < years.size()) {
            return years.get(position);
        } else {
            // 유효하지 않은 경우 기본값 또는 예외 처리
            return "Invalid Year";
        }
    }

    public String getSelectedMonth() {
        Integer position = selectedMonthPosition.getValue();
        List<String> months = monthList.getValue();
        if (position != null && months != null && position >= 0 && position < months.size()) {
            return months.get(position);
        } else {
            // 유효하지 않은 경우 기본값 또는 예외 처리
            return "Invalid Month";
        }
    }

    public void setSelectedYearPosition(int position) {
        selectedYearPosition.setValue(position);
    }

    public void setSelectedMonthPosition(int position) {
        selectedMonthPosition.setValue(position);
    }
}
