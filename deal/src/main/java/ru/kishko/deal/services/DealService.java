package ru.kishko.deal.services;

import ru.kishko.deal.dtos.FinishRegistrationRequestDto;
import ru.kishko.deal.dtos.LoanOfferDto;
import ru.kishko.deal.dtos.LoanStatementRequestDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto request);

    void selectLoanOffer(LoanOfferDto request);

    void calculateLoan(String statementId, FinishRegistrationRequestDto request);
}
