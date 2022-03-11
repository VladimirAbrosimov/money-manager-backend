package ru.vabrosimov.moneymanager.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vabrosimov.moneymanager.entity.NoteCategory;

@Data
@AllArgsConstructor
public class IncomeExpenseStatisticsForCategory {
    private NoteCategory category;
    private Long amount;
}
