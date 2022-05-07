package antifraud.dto;

import antifraud.model.TransactionResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TransactionResponse {
    private TransactionResult result;
    private String info;
}
