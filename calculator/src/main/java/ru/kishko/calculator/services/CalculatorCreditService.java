package ru.kishko.calculator.services;

import ru.kishko.calculator.dtos.CreditDto;
import ru.kishko.calculator.dtos.ScoringDataDto;

public interface CalculatorCreditService {
    CreditDto calculateCredit(ScoringDataDto request);
}
