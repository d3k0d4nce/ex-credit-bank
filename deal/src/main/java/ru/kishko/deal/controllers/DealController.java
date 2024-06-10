package ru.kishko.deal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.deal.dtos.FinishRegistrationRequestDto;
import ru.kishko.deal.dtos.LoanOfferDto;
import ru.kishko.deal.dtos.LoanStatementRequestDto;
import ru.kishko.deal.services.DealService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    // 2.4.1. Расчет возможных условий кредита
    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> getLoanOffers(@RequestBody LoanStatementRequestDto request) {
        return new ResponseEntity<>(dealService.getLoanOffers(request), HttpStatus.OK);
    }

    // 2.4.2. Выбор одного из предложений
    @PostMapping("/offer/select")
    public void selectLoanOffer(@RequestBody LoanOfferDto request) {
        dealService.selectLoanOffer(request);
    }

    // 2.4.3. Завершение регистрации + полный подсчёт кредита
    @PostMapping("/calculate/{statementId}")
    public void calculateLoan(@PathVariable("statementId") String statementId, @RequestBody FinishRegistrationRequestDto request) {
        dealService.calculateLoan(statementId, request);
    }

}
