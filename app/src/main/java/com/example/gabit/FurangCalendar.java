package com.example.gabit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FurangCalendar {
    private ArrayList<Integer> dateList;
    private Calendar calendar;
    private int prevTail;
    private int nextHead;

    public FurangCalendar(Date date) {
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(date);
        this.dateList = new ArrayList<>();
    }

    public void initBaseCalendar() {
        makeMonthDateList();
    }

    private void makeMonthDateList() {
        dateList.clear();

        // 현재 달의 첫 번째 날로 설정
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 첫 번째 날의 요일
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 현재 달의 마지막 날

        // 이전 달의 날짜 추가
        Calendar prevMonth = (Calendar) calendar.clone();
        prevMonth.add(Calendar.MONTH, -1);
        int maxDayOfPrevMonth = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH); // 이전 달의 마지막 날

        for (int i = 1; i <= firstDayOfMonth; i++) {
            dateList.add(maxDayOfPrevMonth - firstDayOfMonth + i);
        }
        prevTail = firstDayOfMonth;

        // 현재 달의 날짜 추가
        for (int i = 1; i <= maxDayOfMonth; i++) {
            dateList.add(i);
        }

        // 다음 달의 날짜 추가
        int nextMonthDay = 1;
        while (dateList.size() % 7 != 0) {
            dateList.add(nextMonthDay++);
        }
        nextHead = nextMonthDay - 1;
    }

    public ArrayList<Integer> getDateList() {
        return dateList;
    }

    public int getPrevTail() {
        return prevTail;
    }

    public int getNextHead() {
        return nextHead;
    }
}
