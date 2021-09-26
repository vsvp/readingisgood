package com.example.readingisgood.controller;

import com.example.readingisgood.entity.Order;
import com.example.readingisgood.model.Response;
import com.example.readingisgood.model.Statistics;
import com.example.readingisgood.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/{customerId}", method = RequestMethod.POST)
    public List<Statistics> createOrder(@PathVariable(value ="customerId" , required = true) String customerId) {
        return statisticsService.getMonthlyStatistics(customerId);
    }
}
