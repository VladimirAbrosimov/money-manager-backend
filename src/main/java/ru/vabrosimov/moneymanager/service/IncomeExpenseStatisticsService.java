package ru.vabrosimov.moneymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.vabrosimov.moneymanager.entity.Note;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.types.IncomeExpenseStatisticsForMonth;
import ru.vabrosimov.moneymanager.types.IncomeExpenseStatisticsForType;
import ru.vabrosimov.moneymanager.types.NoteType;
import ru.vabrosimov.moneymanager.types.IncomeExpenseStatisticsForCategory;
import ru.vabrosimov.moneymanager.repository.NoteRepository;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class IncomeExpenseStatisticsService {
    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteCategoryService noteCategoryService;


    public long getTotalAmountByYearAndMonth(Authentication authentication, int year, int month) {
        var notes = noteService.findAllByYearAndMonth(authentication, year, month);
        return countTotalAmount(notes);
    }

    public long getTotalAmountInCurrentMonth(Authentication authentication) {
        var notes = noteService.findAllInCurrentMonth(authentication);
        return countTotalAmount(notes);
    }

    // IncomeExpenseStatisticsForType

    public List<IncomeExpenseStatisticsForType> getIncomeExpenseStatisticsInCurrentMonthSortedByType(Authentication authentication) {
        List<IncomeExpenseStatisticsForType> incomeExpenseStatisticsForTypes = new ArrayList<>();
        var notesIncome = noteService.findAllInCurrentMonthByType(authentication, NoteType.INCOME);
        var notesExpense = noteService.findAllInCurrentMonthByType(authentication, NoteType.EXPENSE);

        incomeExpenseStatisticsForTypes.add(new IncomeExpenseStatisticsForType(NoteType.INCOME, countTotalAmountAbs(notesIncome)));
        incomeExpenseStatisticsForTypes.add(new IncomeExpenseStatisticsForType(NoteType.EXPENSE, countTotalAmountAbs(notesExpense)));
        return incomeExpenseStatisticsForTypes;
    }


    // IncomeExpenseStatisticsForCategory

    public List<IncomeExpenseStatisticsForCategory> getIncomeExpenseStatisticsByYearAndMonthAndTypeSortedByCategory(Authentication authentication, int year, int month, NoteType type) {
        List<IncomeExpenseStatisticsForCategory> incomeExpenseStatisticsByCategories = new ArrayList<>();

        List<NoteCategory> categories = noteCategoryService.findAllByType(authentication, type);
        for (NoteCategory category : categories) {
            List<Note> notes = noteService.findAllByYearAndMonthAndCategory(authentication, year, month, category);
            long totalAmount = countTotalAmountAbs(notes);
            incomeExpenseStatisticsByCategories.add(new IncomeExpenseStatisticsForCategory(category, totalAmount));
        }

        return incomeExpenseStatisticsByCategories;
    }

    public List<IncomeExpenseStatisticsForCategory> getIncomeExpenseStatisticsInCurrentMonthByTypeSortedByCategory(Authentication authentication, NoteType type) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        return getIncomeExpenseStatisticsByYearAndMonthAndTypeSortedByCategory(authentication, currentYear, currentMonth, type);
    }

    public List<IncomeExpenseStatisticsForCategory> getIncomeExpenseStatisticsForAllTimeByTypeSortedByCategory(Authentication authentication, NoteType type) {
        List<IncomeExpenseStatisticsForCategory> incomeExpenseStatisticsByCategories = new ArrayList<>();

        List<NoteCategory> categories = noteCategoryService.findAllByType(authentication, type);
        for (NoteCategory category : categories) {
            List<Note> notes = noteService.findAllByCategory(authentication, category);
            long totalAmount = countTotalAmountAbs(notes);
            incomeExpenseStatisticsByCategories.add(new IncomeExpenseStatisticsForCategory(category, totalAmount));
        }

        return incomeExpenseStatisticsByCategories;
    }


    // IncomeExpenseStatisticsForMonth

    public IncomeExpenseStatisticsForMonth getIncomeExpenseStatisticsByYearAndMonth(Authentication authentication, int year, int month) {
        var notes = noteService.findAllByYearAndMonth(authentication, year, month);
        var notesIncome = noteService.findAllByYearAndMonthAndType(authentication, year, month, NoteType.INCOME);
        var notesExpense = noteService.findAllByYearAndMonthAndType(authentication, year, month, NoteType.EXPENSE);

        YearMonth yearMonth = YearMonth.of(year, month);
        Long incomeAmount = countTotalAmountAbs(notesIncome);
        Long expenseAmount = countTotalAmountAbs(notesExpense);
        Long totalAmount = countTotalAmount(notes);

        return new IncomeExpenseStatisticsForMonth(yearMonth, incomeAmount, expenseAmount, totalAmount);
    }

    public List<IncomeExpenseStatisticsForMonth> getIncomeExpenseStatisticsForIntervalSortedByMonth(Authentication authentication, LocalDate dateFrom, LocalDate dateTo) {
        List<IncomeExpenseStatisticsForMonth> incomeExpenseStatisticsForMonths = new ArrayList<>();

        for (LocalDate date = dateFrom; date.isBefore(dateTo) || date.isEqual(dateTo); date = date.plusMonths(1)) {
            incomeExpenseStatisticsForMonths.add(
                getIncomeExpenseStatisticsByYearAndMonth(authentication, date.getYear(), date.getMonthValue())
            );
        }

        return incomeExpenseStatisticsForMonths;
    }

    public List<IncomeExpenseStatisticsForMonth> getIncomeExpenseStatisticsForCurrentYearSortedByMonth(Authentication authentication) {
        LocalDate dateTo = LocalDate.now();
        LocalDate dateFrom = dateTo.minusMonths(11);
        return getIncomeExpenseStatisticsForIntervalSortedByMonth(authentication, dateFrom, dateTo);
    }



    private long countTotalAmount(List<Note> notes) {
        long totalMoney = 0;
        for (var note : notes) {
            var type = note.getType();
            var amount = note.getAmount();

            if (type == NoteType.INCOME) {
                totalMoney += amount;
            } else if (type == NoteType.EXPENSE) {
                totalMoney -= amount;
            }
        }
        return totalMoney;
    }

    private long countTotalAmountAbs(List<Note> notes) {
        return Math.abs(countTotalAmount(notes));
    }
}
