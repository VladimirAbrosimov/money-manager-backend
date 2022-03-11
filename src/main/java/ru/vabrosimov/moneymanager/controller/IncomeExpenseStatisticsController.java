package ru.vabrosimov.moneymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.entity.NoteType;
import ru.vabrosimov.moneymanager.entity.TotalMoney;
import ru.vabrosimov.moneymanager.service.IncomeExpenseStatisticsService;
import ru.vabrosimov.moneymanager.service.NoteService;

import java.util.List;
import java.util.Map;

@RestController
public class IncomeExpenseStatisticsController {
    @Autowired
    private IncomeExpenseStatisticsService incomeExpenseStatisticsService;

//    @GetMapping("/getTotalMoneyInYearAndMonth")
//    public int getTotalMoneyInYearAndMonth(@RequestParam int year, @RequestParam int month, Authentication authentication) {
//        return noteService.getTotalMoneyInYearAndMonth(authentication, year, month);
//    }
//
    @GetMapping("/getTotalMoneyInCurrentMonth")
    public long getTotalMoneyInCurrentMonth(Authentication authentication) {
        return incomeExpenseStatisticsService.getTotalMoneyInCurrentMonth(authentication);
    }

//    @GetMapping("/getTotalMoneyInYearAndMonthAndTypeSortedByCategory")
//    public Map<NoteCategory, Long> getTotalMoneyInYearAndMonthAndTypeSortedByCategory(@RequestParam int year, @RequestParam int month, @RequestParam NoteType type, Authentication authentication) {
//        return incomeExpenseStatisticsService.getTotalMoneyInYearAndMonthAndTypeSortedByCategory(authentication, year, month, type);
//    }

    @GetMapping("/getTotalMoneyInCurrentMonthAndTypeSortedByCategory")
    public List<TotalMoney> getTotalMoneyInCurrentMonthAndTypeSortedByCategory(@RequestParam NoteType type, Authentication authentication) {
        return incomeExpenseStatisticsService.getTotalMoneyInCurrentMonthAndTypeSortedByCategory(authentication, type);
    }
}
