package com.example.readingisgood.service;

import com.example.readingisgood.model.Statistics;

import java.util.List;

public interface StatisticsService {

    List<Statistics> getMonthlyStatistics(String id);
}
