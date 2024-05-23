package com.example.gabit;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Mission_Calendar extends AppCompatActivity {
    ArrayAdapter<CharSequence> adspin_year, adspin_month;
    Spinner spin1;
    Spinner spin2;
    Button btn_show;
    String choice_year = "";
    String choice_month = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_calendar_xml);

        spin1 = findViewById(R.id.spinner_year);
        spin2 = findViewById(R.id.spinner_month);
        btn_show = findViewById(R.id.btn_show);

        adspin_year = ArrayAdapter.createFromResource(this, R.array.spinner_year, android.R.layout.simple_spinner_dropdown_item);
        adspin_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adspin_year);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_year = adspin_year.getItem(position).toString();
                adspin_month = ArrayAdapter.createFromResource(Mission_Calendar.this, R.array.spinner_month, android.R.layout.simple_spinner_dropdown_item);
                adspin_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin2.setAdapter(adspin_month);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_month = adspin_month.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarFragment(choice_year, choice_month);
            }
        });
    }

    private void showCalendarFragment(String year, String month) {
        CalendarFragment calendarFragment = CalendarFragment.newInstance(year, month);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, calendarFragment);
        fragmentTransaction.commit();
    }
}
