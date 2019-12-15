package com.discover.tunisia.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.discover.tunisia.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.List;

public class DayEnableDecoratorSpan implements DayViewDecorator {
    private final List<CalendarDay> dates;
    private final Context context;

    public DayEnableDecoratorSpan(List<CalendarDay> dates, Context context) {
        this.dates = dates;
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(false);
        view.addSpan(new DotSpan(5, context.getResources().getColor(R.color.colorPrimary)));
        view.addSpan(new ForegroundColorSpan(Color.BLACK));
    }
}
