package ru.kishko.calculator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.calculator.dtos.CreditDto;
import ru.kishko.calculator.dtos.LoanOfferDto;
import ru.kishko.calculator.dtos.LoanStatementRequestDto;
import ru.kishko.calculator.dtos.ScoringDataDto;
import ru.kishko.calculator.services.CalculatorCreditService;
import ru.kishko.calculator.services.CalculatorOfferService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/calculator")
public class CalculatorController {

    private final CalculatorOfferService calculatorService;
    private final CalculatorCreditService creditService;

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> calculateLoanOffers(@RequestBody @Valid LoanStatementRequestDto request) {
        return new ResponseEntity<>(calculatorService.calculateLoanOffers(request), HttpStatus.OK);
    }

    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calculateCredit(@RequestBody @Valid ScoringDataDto request) {
        return new ResponseEntity<>(creditService.calculateCredit(request), HttpStatus.OK);
    }

}
