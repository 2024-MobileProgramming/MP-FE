package com.example.gabit;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MissionCalendarViewModel extends ViewModel {

    private MutableLiveData<Integer> selectedYearPosition = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedMonthPosition = new MutableLiveData<>();

    private MutableLiveData<List<String>> yearList = new MutableLiveData<>();
    private MutableLiveData<List<String>> monthList = new MutableLiveData<>();

    private MutableLiveData<List<Integer>> monthlyMissions = new MutableLiveData<>();

    private MissionService missionService;

    public MissionCalendarViewModel() {
        missionService = new MissionService();

        // 년도 목록 초기화
        List<String> years = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        for (int i = currentYear - 5; i <= currentYear + 5; i++) {
            years.add(String.valueOf(i));
        }
        yearList.setValue(years);

        // 월 목록 초기화
        List<String> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(String.valueOf(i));
        }
        monthList.setValue(months);
    }

    public LiveData<Integer> getSelectedYearPosition() {
        return selectedYearPosition;
    }

    public LiveData<Integer> getSelectedMonthPosition() {
        return selectedMonthPosition;
    }

    public LiveData<List<String>> getYearList() {
        return yearList;
    }

    public LiveData<List<String>> getMonthList() {
        return monthList;
    }

    public LiveData<List<Integer>> getMonthlyMissions() {
        return monthlyMissions;
    }

    public void updateCalendar_model() {
        // ViewModel에서 선택된 년도와 월을 가져옵니다.
        int selectedYear = selectedYearPosition.getValue();
        int selectedMonth = selectedMonthPosition.getValue();
        Log.d("Update Month", String.valueOf(selectedMonth));
        // CalendarAdapter에 새로운 년도와 월을 설정하고 갱신
        // calendarAdapter.setDate(selectedYear, selectedMonth);
        // calendarAdapter.notifyDataSetChanged();
    }

    public void fetchMonthlyMissions(int year, int month) {
        // API 호출하여 월별 미션 데이터 가져오기
        missionService.getMonthlyMissions(new MonthlyMissionRequest("3", year, month), new MissionService.MonthlyMissionCallback() {
            @Override
            public void onSuccess(List<Integer> missions) {
                monthlyMissions.setValue(missions);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("MissionCalendarViewModel", "Error fetching monthly missions: " + errorMessage);
            }
        });
    }

    public void setSelectedYearPosition(int position) {
        selectedYearPosition.setValue(position);
    }

    public void setSelectedMonthPosition(int position) {
        selectedMonthPosition.setValue(position+2);
        Log.d("Position : ", String.valueOf(position));
    }
}