package ru.vabrosimov.moneymanager.types;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncomeExpenseStatisticsForType {
    private NoteType type;
    private Long amount;
}
