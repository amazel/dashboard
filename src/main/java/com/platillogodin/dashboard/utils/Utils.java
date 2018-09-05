package com.platillogodin.dashboard.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

/**
 * Created by Hugo Lezama on September - 2018
 */
@Slf4j
public class Utils {

    public static LocalDate getMondayFromWeekAndYear(final Integer week, final Integer year) {
        LocalDate date = LocalDate.of(year, 7, 1);
        date = date.with(WeekFields.ISO.weekOfWeekBasedYear(), week);
        date = date.with(WeekFields.ISO.dayOfWeek(), 1);

        return date;
    }

    public static LocalDate getNextMonday(LocalDate date) {
        int dayOfWeek = date.get(WeekFields.ISO.dayOfWeek());
        if (dayOfWeek == 0) {
            return date.plusDays(1);
        }
        return date.plusDays(8 - dayOfWeek);
    }
}
