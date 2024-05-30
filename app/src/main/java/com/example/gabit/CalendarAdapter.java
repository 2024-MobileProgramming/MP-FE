package com.example.gabit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarItemHolder> {

    private ArrayList<Integer> dataList;
    private FurangCalendar furangCalendar;
    private LinearLayout calendarLayout;
    private Integer selectedYear;
    private Integer selectedMonth;

    private boolean[] activityCompleted; // 활동 완료 여부 배열

    public interface ItemClick {
        void onClick(View view, int position);
    }

    private ItemClick itemClick;

    // Context 파라미터를 제거하고, calendarLayout과 date만 받습니다.
    public CalendarAdapter(LinearLayout calendarLayout, int selectedYear, int selectedMonth) {
        this.calendarLayout = calendarLayout;
        this.selectedYear = selectedYear;
        this.selectedMonth = selectedMonth;
        this.furangCalendar = new FurangCalendar(this.selectedYear, this.selectedMonth);
        this.furangCalendar.initBaseCalendar();
        this.dataList = furangCalendar.getDateList();
    }

    @Override
    public CalendarItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // parent.getContext()를 사용하여 Context를 얻습니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calendar, parent, false);
        return new CalendarItemHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarItemHolder holder, int position) {
        int h = calendarLayout.getHeight() / 7;
        holder.itemView.getLayoutParams().height = h;

        // dataList의 Integer 값을 Date로 변환하는 로직이 필요합니다.
        // FurangCalendar의 getDateList()가 날짜 데이터를 Integer로 반환하는 경우를 가정합니다.
        int day = dataList.get(position);
        Date dateData = furangCalendar.convertToDate(day); // FurangCalendar에 이 메서드가 있어야 합니다.

        holder.bind(dateData, position, holder.itemView.getContext());
        if (itemClick != null) {
            holder.itemView.setOnClickListener(v -> itemClick.onClick(v, position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void setDate(int selectedYear, int selectedMonth) {
        this.selectedYear = selectedYear;
        this.selectedMonth = selectedMonth;
        this.furangCalendar = new FurangCalendar(selectedYear, selectedMonth);
        this.furangCalendar.initBaseCalendar();
        this.dataList = furangCalendar.getDateList(); // dateList를 새로 초기화합니다.
        notifyDataSetChanged(); // 데이터가 변경되었음을 알리고, RecyclerView를 다시 그립니다.
    }

    public class CalendarItemHolder extends RecyclerView.ViewHolder {

        private View daySquare;

        public CalendarItemHolder(View itemView) {
            super(itemView);
            daySquare = itemView.findViewById(R.id.viewDaySquare);
        }

        public void bind(Date dateData, int position, Context context) {
            int firstDateIndex = furangCalendar.getPrevTail();
            int lastDateIndex = dataList.size() - furangCalendar.getNextHead() - 1;

            // 현재 날짜와 비교하기 위한 형식 변경
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd", Locale.KOREA);
            String currentDateString = dateFormat.format(new Date()); // 현재 날짜를 가져옵니다.
            String itemDateString = dateFormat.format(dateData);

            // 날짜가 같은지 확인
            if (currentDateString.equals(itemDateString)) {
                daySquare.setBackgroundResource(R.drawable.shape_calendar_day_selected); // 선택된 날짜의 배경 변경
            } else {
                daySquare.setBackgroundResource(R.drawable.shape_calendar_day); // 기본 배경
            }

            if (position < firstDateIndex || position > lastDateIndex) {
                daySquare.setBackgroundResource(R.drawable.shape_calendar_day_inactive); // 이전/다음 달의 날짜 배경 변경
            }
        }
    }
}
