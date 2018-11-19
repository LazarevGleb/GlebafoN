package model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import model.entities.Contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO for Contract
 * It contains: contract (telephone) number, chosen tariff
 * and list of active options for this tariff.
 */

public class ContractDto extends AbstractDto {
    private String number;
    @JsonBackReference
    private ClientDto client;
    @JsonBackReference
    private TariffDto tariff;
    @JsonBackReference
    private List<AdditionDto> additions = new ArrayList<>();
    private Contract.BLOCK block;
    @JsonIgnore
    private String password;
    private BigDecimal balance;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public TariffDto getTariff() {
        return tariff;
    }

    public void setTariff(TariffDto tariff) {
        this.tariff = tariff;
    }

    public List<AdditionDto> getAdditions() {
        return additions;
    }

    public void setAdditions(List<AdditionDto> additions) {
        this.additions = additions;
    }

    public Contract.BLOCK getBlock() {
        return block;
    }

    public void setBlock(Contract.BLOCK block) {
        this.block = block;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        ContractDto that = (ContractDto) o;
        return Objects.equals(number, that.number) &&
                Objects.equals(client, that.client) &&
                Objects.equals(tariff, that.tariff) &&
                Objects.equals(additions, that.additions) &&
                block == that.block &&
                Objects.equals(password, that.password) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, client, tariff, additions, block,
                password, balance);
    }

    @Override
    public String toString() {
        return "ContractDto{" +
                "number='" + number + '\'' +
                ", client=" + client.getSurname() +
                ", tariff=" + tariff.getName() +
                ", block=" + block +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
