package com.example.xeva.service.impl;

import com.example.xeva.service.interfaces.GeneratorService;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneratorDateImpl implements GeneratorService {

    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    @Override
    public HashMap<LocalDateTime,LocalDateTime> generatedates(String daysOfWeek, LocalDateTime start, LocalDateTime finish) {
        //find weekDays as numbers
        List<String> daysList = Arrays.asList(daysOfWeek.split(","));
        daysList = daysList.stream().map(String :: trim).collect(Collectors.toList());
        List<LocalDateTime> totalDates = new ArrayList<>();
        LocalDateTime now= LocalDateTime.now();
        long duration = generateMinuteDuration(start,finish);
        HashMap<LocalDateTime,LocalDateTime> result = new HashMap<>();


        for(String weekDay: daysList ) { // znalezienie wszystkich powtórzeń
            LocalDateTime firstWeekDate = findFirstDate(start,finish,weekDay); // najblizszy dzien o zadanym ddniu tygodnia
            if(firstWeekDate.isBefore(now)) {
                firstWeekDate = firstWeekDate.plusDays(7);
            }
            while (!firstWeekDate.isAfter(finish)) {
                LocalDateTime finishOneEvent = firstWeekDate.plusMinutes(duration);
                totalDates.add(firstWeekDate);
                result.put(firstWeekDate,finishOneEvent);
                firstWeekDate = firstWeekDate.plusDays(7);
            }
        }

        return result;
    }

    @Override
    public String getOnlyDate(LocalDateTime event) {

        return event.toLocalDate().toString();
    }

    @Override
    public String getOnlyTime(LocalDateTime event) {

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm");
        return event.toLocalTime().format(pattern);
    }

    public static long generateMinuteDuration(LocalDateTime start, LocalDateTime finish) {
        Duration duration = Duration.between(start, finish);
        long seconds = duration.getSeconds();

        long hours = (seconds / SECONDS_PER_HOUR % 24)*60;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        System.out.println("DURATION "+hours+" "+minutes);
        return hours+minutes;
    }



    public static LocalDateTime findFirstDate(LocalDateTime datestart, LocalDateTime datefinish, String day) {

        while(!datestart.isAfter(datefinish)) { // znalezienie pierwszego najblizszego poniedziałku i X kolejnych do przodu

            if(datestart.getDayOfWeek().toString().equals(day)) {
                return datestart;
            } else {
                datestart = datestart.plusDays(1);
            }
        }
        return null;
    }
}
