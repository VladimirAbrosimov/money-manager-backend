package ru.vabrosimov.moneymanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.vabrosimov.moneymanager.entity.Note;
import ru.vabrosimov.moneymanager.entity.NoteCategory;
import ru.vabrosimov.moneymanager.entity.NoteType;
import ru.vabrosimov.moneymanager.entity.TotalMoney;
import ru.vabrosimov.moneymanager.repository.NoteRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class IncomeExpenseStatisticsService {
    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteCategoryService noteCategoryService;


    public long getTotalMoneyInYearAndMonth(Authentication authentication, int year, int month) {
        var notes = noteService.findAllByYearAndMonth(authentication, year, month);
        return countTotalMoney(notes);
    }

    public long getTotalMoneyInCurrentMonth(Authentication authentication) {
        var notes = noteService.findAllInCurrentMonth(authentication);
        return countTotalMoney(notes);
    }

    public List<TotalMoney> getTotalMoneyInYearAndMonthAndTypeSortedByCategory(Authentication authentication, int year, int month, NoteType type) {
        List<TotalMoney> totalMoneys = new LinkedList<>();

        List<NoteCategory> categories = noteCategoryService.findAllByType(authentication, type);

        for (NoteCategory category : categories) {
            List<Note> notes = noteService.findAllByYearAndMonthAndTypeAndCategory(authentication, year, month, type, category);
            long totalMoney = 0;
            for (var note : notes) {
                totalMoney += note.getAmount();
            }
            if (totalMoney == 0) continue;
            totalMoneys.add(new TotalMoney(category, totalMoney));
        }

        return totalMoneys;
    }

    public List<TotalMoney> getTotalMoneyInCurrentMonthAndTypeSortedByCategory(Authentication authentication, NoteType type) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        return getTotalMoneyInYearAndMonthAndTypeSortedByCategory(authentication, currentYear, currentMonth, type);
    }

    private long countTotalMoney(List<Note> notes) {
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
}
