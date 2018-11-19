package model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContractFormDto extends AbstractDto {
    private String number;
    private int clientId;
    private int tariffId;
    private List<Integer> additionIds = new ArrayList<>();
    private BigDecimal balance;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getTariffId() {
        return tariffId;
    }

    public void setTariffId(int tariffId) {
        this.tariffId = tariffId;
    }

    public List<Integer> getAdditionIds() {
        return additionIds;
    }

    public void setAdditionIds(List<Integer> additionIds) {
        this.additionIds = additionIds;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractFormDto that = (ContractFormDto) o;
        return clientId == that.clientId &&
                tariffId == that.tariffId &&
                Objects.equals(number, that.number) &&
                Objects.equals(additionIds, that.additionIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, clientId, tariffId, additionIds);
    }

    @Override
    public String toString() {
        return "ContractFormDto{" +
                "number='" + number + '\'' +
                ", balance=" + balance +
                '}';
    }
}
