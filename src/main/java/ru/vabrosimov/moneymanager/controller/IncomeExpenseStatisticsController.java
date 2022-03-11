package ru.vabrosimov.moneymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vabrosimov.moneymanager.types.IncomeExpenseStatisticsForMonth;
import ru.vabrosimov.moneymanager.types.IncomeExpenseStatisticsForType;
import ru.vabrosimov.moneymanager.types.NoteType;
import ru.vabrosimov.moneymanager.types.IncomeExpenseStatisticsForCategory;
import ru.vabrosimov.moneymanager.service.IncomeExpenseStatisticsService;

import java.util.List;

@RestController
public class IncomeExpenseStatisticsController {
    @Autowired
    private IncomeExpenseStatisticsService incomeExpenseStatisticsService;

    @GetMapping("/getTotalAmountInCurrentMonth")
    public long getTotalAmountInCurrentMonth(Authentication authentication) {
        return incomeExpenseStatisticsService.getTotalAmountInCurrentMonth(authentication);
    }

    @GetMapping("/getIncomeExpenseStatisticsInCurrentMonthSortedByType")
    public List<IncomeExpenseStatisticsForType> getIncomeExpenseStatisticsInCurrentMonthSortedByType(Authentication authentication) {
        return incomeExpenseStatisticsService.getIncomeExpenseStatisticsInCurrentMonthSortedByType(authentication);
    }

    @GetMapping("/getIncomeExpenseStatisticsInCurrentMonthByTypeSortedByCategory")
    public List<IncomeExpenseStatisticsForCategory> getIncomeExpenseStatisticsInCurrentMonthByTypeSortedByCategory(@RequestParam NoteType type, Authentication authentication) {
        return incomeExpenseStatisticsService.getIncomeExpenseStatisticsInCurrentMonthByTypeSortedByCategory(authentication, type);
    }

    @GetMapping("/getIncomeExpenseStatisticsForAllTimeByTypeSortedByCategory")
    public List<IncomeExpenseStatisticsForCategory> getIncomeExpenseStatisticsForAllTimeByTypeSortedByCategory(@RequestParam NoteType type, Authentication authentication) {
        return incomeExpenseStatisticsService.getIncomeExpenseStatisticsForAllTimeByTypeSortedByCategory(authentication, type);
    }

    @GetMapping("/getIncomeExpenseStatisticsForCurrentYearSortedByMonth")
    public List<IncomeExpenseStatisticsForMonth> getIncomeExpenseStatisticsForCurrentYearSortedByMonth(Authentication authentication) {
        return incomeExpenseStatisticsService.getIncomeExpenseStatisticsForCurrentYearSortedByMonth(authentication);
    }
}
