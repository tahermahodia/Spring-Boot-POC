package rc.bootsecurity.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class SendMoneyForm {

    @NotNull(message = "From Account Id Cannot Be Null")
    @Min(1)
    @Max(10)
    private Long fromAccountId;

    @NotNull
    private Long toAccountId;
    @NotNull
    private Double amount;

    public SendMoneyForm() {
    }

    public SendMoneyForm(Long fromAccountId, Long toAccountId, Double amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
