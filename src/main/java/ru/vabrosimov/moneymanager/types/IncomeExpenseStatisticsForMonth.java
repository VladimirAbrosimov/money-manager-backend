package ru.vabrosimov.moneymanager.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.YearMonth;

@Data
@AllArgsConstructor
public class IncomeExpenseStatisticsForMonth {
    private YearMonth yearMonth;
    private Long incomeAmount;
    private Long expenseAmount;
    private Long totalAmount;
}
