package antifraud.service;

import antifraud.controller.request.TransactionRequest;
import antifraud.dto.TransactionResponse;
import antifraud.model.TransactionResult;
import antifraud.repository.CardRepository;
import antifraud.repository.IpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService{
    @Autowired
    IpRepository ipRepository;

    @Autowired
    CardRepository cardRepository;


    public TransactionResponse doTransaction(TransactionRequest transactionRequest) {
        List<String> reason = new ArrayList<>();

        if(!cardRepository.existsByNumber(transactionRequest.getNumber())){
            return new TransactionResponse().setResult(TransactionResult.PROHIBITED).setInfo()
        }

        if


        if(transactionRequest.getAmount() <= 200)
            return Map.of("result", "ALLOWED");

        if(transactionRequest.getAmount() > 200 && transactionRequest.getAmount() <= 1500)
            return Map.of("result", "MANUAL_PROCESSING");

        return Map.of("result", "PROHIBITED");
    }

}
