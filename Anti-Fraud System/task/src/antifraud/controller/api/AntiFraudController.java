package antifraud.controller.api;

import antifraud.controller.request.CardRequest;
import antifraud.controller.request.IpRequest;
import antifraud.controller.request.TransactionRequest;
import antifraud.dto.CardResponse;
import antifraud.dto.IpResponse;
import antifraud.dto.StatusResponse;
import antifraud.model.Card;
import antifraud.service.CardService;
import antifraud.service.IpService;
import antifraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/antifraud")
public class AntiFraudController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    IpService ipService;

    @Autowired
    CardService cardService;

    @PostMapping("/transaction")
    public Map<String, String> doTransaction(@Valid @RequestBody TransactionRequest transactionRequest){
        return transactionService.doTransaction(transactionRequest);
    }

    @PostMapping("/suspicious-ip")
    public IpResponse saveIp(@RequestBody @Valid IpRequest request){
        return ipService.saveSuspiciousIp(request);
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    public StatusResponse deleteIp(@PathVariable("ip") String ip){
        return ipService.delete(ip);
    }

    @GetMapping("/suspicious-ip")
    public List<IpResponse> getAllIp(){
        return ipService.getAll();
    }

    @PostMapping("/stolencard")
    public CardResponse addStolenCard(@RequestBody @Valid CardRequest cardRequest){
        return cardService.add(cardRequest);
    }

    @DeleteMapping("/stolencard/{number}")
    public StatusResponse deleteCard(@PathVariable("number") String number){
        return cardService.delete(number);
    }

    @GetMapping("/stolencard")
    public List<Card> getAllCards(){
        return cardService.getAll();
    }
}
