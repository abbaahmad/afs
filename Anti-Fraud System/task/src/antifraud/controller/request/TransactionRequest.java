package antifraud.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
public class TransactionRequest {
    @Min(1)
    @NotNull
    public Long amount;

    @NotNull
    private String ip;

    @NotNull
    private String number;
}
