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
    private Date date;

    private boolean[] activityCompleted; // 활동 완료 여부 배열

    public interface ItemClick {
        void onClick(View view, int position);
    }

    private ItemClick itemClick;

    // Context 파라미터를 제거하고, calendarLayout과 date만 받습니다.
    public CalendarAdapter(LinearLayout calendarLayout, Date date) {
        this.calendarLayout = calendarLayout;
        this.date = date;
        this.furangCalendar = new FurangCalendar(date);
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
        int h = calendarLayout.getHeight() / 6;
        holder.itemView.getLayoutParams().height = h;

        // context 대신 holder.itemView.getContext()를 사용합니다.
        holder.bind(dataList.get(position), position, holder.itemView.getContext());
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

    public void setDate(Date date) {
        this.date = date; // 선택된 날짜로 date 필드를 업데이트합니다.
        this.furangCalendar = new FurangCalendar(date); // 새로운 날짜로 FurangCalendar 객체를 업데이트합니다.
        this.furangCalendar.initBaseCalendar(); // 캘린더 기본 설정을 초기화합니다.
        this.dataList = furangCalendar.getDateList(); // 새로운 날짜 리스트를 가져옵니다.
        notifyDataSetChanged(); // 데이터가 변경되었음을 알리고, RecyclerView를 다시 그립니다.
    }

    public class CalendarItemHolder extends RecyclerView.ViewHolder {

        private View daySquare;

        public CalendarItemHolder(View itemView) {
            super(itemView);
            daySquare = itemView.findViewById(R.id.viewDaySquare);
        }

        public void bind(int data, int position, Context context) {
            int firstDateIndex = furangCalendar.getPrevTail();
            int lastDateIndex = dataList.size() - furangCalendar.getNextHead() - 1;

            // 여기서 사각형 뷰의 속성을 변경할 수 있습니다.
            daySquare.setVisibility(View.VISIBLE);

            String dateString = new SimpleDateFormat("dd", Locale.KOREA).format(date);
            int dateInt = Integer.parseInt(dateString);
            if (dataList.get(position) == dateInt) {
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
