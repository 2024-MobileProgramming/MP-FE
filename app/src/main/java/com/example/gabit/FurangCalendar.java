package com.example.gabit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FurangCalendar {
    private ArrayList<Integer> dateList;
    private Calendar calendar;
    private static final int DAYS_OF_WEEK = 7;
    private static final int LOW_OF_CALENDAR = 6;

    private int prevTail = 0;
    private int nextHead = 0;
    private int currentMaxDate = 0;

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
        calendar.set(Calendar.DATE, 1);

        currentMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int targetStartDay = Calendar.SATURDAY; // 예를 들어, 달력이 토요일에 시작해야 한다고 가정
        prevTail = firstDayOfWeek - targetStartDay;
        if (prevTail < 0) {
            prevTail += DAYS_OF_WEEK;
        }

        makePrevTail((Calendar) calendar.clone());
        makeCurrentMonth(calendar);

        nextHead = LOW_OF_CALENDAR * DAYS_OF_WEEK - (prevTail + currentMaxDate);
        if (nextHead > DAYS_OF_WEEK) {
            nextHead -= DAYS_OF_WEEK;
        }
        makeNextHead();
    }

    private void makeCurrentMonth(Calendar calendar) {
        int maxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= maxDate; i++) {
            dateList.add(i);
        }
    }

    private void makePrevTail(Calendar calendar) {
        calendar.add(Calendar.MONTH, -1);
        int maxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int startDate = maxDate - prevTail + 1;

        for (int i = startDate; i <= maxDate; i++) {
            dateList.add(i);
        }
    }

    private void makeNextHead() {
        int date = 1;
        for (int i = 1; i <= nextHead; i++) {
            dateList.add(date++);
        }
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

    public int getCurrentMaxDate() {
        return currentMaxDate;
    }
}
