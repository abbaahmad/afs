package antifraud.service;

import antifraud.controller.request.CardRequest;
import antifraud.dto.CardResponse;
import antifraud.dto.StatusResponse;
import antifraud.model.Card;
import antifraud.repository.CardRepository;
import antifraud.util.CardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardValidator validator;

    public CardResponse add(CardRequest cardRequest) {
        if(cardRepository.existsByNumber(cardRequest.getNumber()))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        if(!validator.validate(cardRequest.getNumber()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Card card = cardRepository.save(new Card().setNumber(cardRequest.getNumber()));

        return new CardResponse().setId(card.getId()).setNumber(card.getNumber());
    }

    public StatusResponse delete(String number) {
        if(!validator.validate(number))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Card card = cardRepository.findByNumber(number)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        cardRepository.delete(card);

        return new StatusResponse().setStatus("Card " + number + " successfully removed!");
    }

    public List<Card> getAll() {
        return cardRepository.findAll();
    }
}
