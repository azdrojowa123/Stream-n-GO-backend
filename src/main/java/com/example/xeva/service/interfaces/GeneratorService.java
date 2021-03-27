package com.example.xeva.service.interfaces;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface GeneratorService {

    public HashMap<LocalDateTime,LocalDateTime> generatedates(String daysOfWeek, LocalDateTime startDate, LocalDateTime finishDate);
}
