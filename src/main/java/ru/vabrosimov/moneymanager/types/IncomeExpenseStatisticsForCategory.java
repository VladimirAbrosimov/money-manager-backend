package ru.vabrosimov.moneymanager.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vabrosimov.moneymanager.entity.NoteCategory;

@Data
@AllArgsConstructor
public class IncomeExpenseStatisticsByCategory {
    private NoteCategory category;
    private Long amount;
}
