package ru.kishko.deal.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kishko.deal.dtos.FinishRegistrationRequestDto;
import ru.kishko.deal.dtos.LoanOfferDto;
import ru.kishko.deal.dtos.LoanStatementRequestDto;
import ru.kishko.deal.repositories.ClientRepository;
import ru.kishko.deal.repositories.CreditRepository;
import ru.kishko.deal.repositories.StatementRepository;
import ru.kishko.deal.services.DealService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final StatementRepository statementRepository;

    @Override
    public List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto request) {
        return null;
    }

    @Override
    public void selectLoanOffer(LoanOfferDto request) {

    }

    @Override
    public void calculateLoan(String statementId, FinishRegistrationRequestDto request) {

    }
}
