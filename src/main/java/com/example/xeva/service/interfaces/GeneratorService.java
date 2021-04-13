package com.example.xeva.service.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public interface GeneratorService {

    public HashMap<LocalDateTime,LocalDateTime> generatedates(String daysOfWeek, LocalDateTime startDate, LocalDateTime finishDate);
    public String getOnlyDate(LocalDateTime event);
    public String getOnlyTime(LocalDateTime event);
}
