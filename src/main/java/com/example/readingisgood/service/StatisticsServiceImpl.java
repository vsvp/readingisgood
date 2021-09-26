package com.example.readingisgood.service;

import com.example.readingisgood.model.Statistics;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl extends BaseService implements StatisticsService {


    private static final String query = "SELECT\n" +
            "       DATE_TRUNC('month',start_date)\n" +
            "         AS  months,\n" +
            "       COUNT(o.id) AS count,\n" +
            "       SUM(cost) as totalcost,\n" +
            "       SUM(book_count) totalbookcount\n" +
            "FROM orders o , customer c \n" +
            "where c.id =:id\n" +
            "GROUP BY DATE_TRUNC('month',start_date);";


    @Override
    public List<Statistics> getMonthlyStatistics(String id) {

        List<Statistics> statisticsList = new ArrayList<>();

        List<Object[]> results = this.em.createNativeQuery(query).setParameter("id", id).getResultList();

        results.stream().forEach((record) -> {
            Date date = (Date) record[0];
            BigInteger count = (BigInteger) record[1];
            BigInteger totalcost = (BigInteger) record[2];
            BigInteger totalbookcount = (BigInteger) record[3];

            Statistics s = new Statistics();
            s.setMonth(getMonth(date));
            s.setTotalbookcount(totalbookcount.intValue());
            s.setTotalcost(totalcost.intValue());
            s.setCount(count.intValue());
            statisticsList.add(s);

        });

        statisticsList.forEach(statistics -> {

        });

        return statisticsList;
    }

    private String getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int monthNumber = calendar.get(Calendar.MONTH);
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (monthNumber >= 0 && monthNumber <= 11) {
            month = months[monthNumber];
        }
        return month;
    }
}
