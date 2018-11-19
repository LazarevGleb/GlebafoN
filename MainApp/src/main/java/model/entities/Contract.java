package model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Presents table Contract in DB
 */
@Entity
@Table(name = "contract")
public class Contract extends AbstractEntity implements Serializable {

    public enum BLOCK {UNBLOCKED, CLIENT_BLOCKED, MANAGER_BLOCKED}

    @Column(name = "NUMBER")
    private String number;

    @ManyToOne
    @JoinColumn(name = "CONTRACT_CLIENT_ID")
    @JsonBackReference
    private Client client;

    @ManyToOne
    @JoinColumn(name = "CONTRACT_TARIFF_ID")
    private Tariff tariff;

    @Column(name = "BLOCKED")
    @Enumerated(EnumType.STRING)
    private BLOCK block = BLOCK.UNBLOCKED;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "expense",
            joinColumns = @JoinColumn(name = "EXPENSE_CONTRACT_ID"),
            inverseJoinColumns = @JoinColumn(name = "EXPENSE_ADDITION_ID"))
    private List<Addition> additions = new ArrayList<>();

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Addition> getAdditions() {
        return additions;
    }

    public void setAdditions(List<Addition> additions) {
        this.additions = additions;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public BLOCK getBlock() {
        return block;
    }

    public void setBlock(BLOCK block) {
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
        Contract contract = (Contract) o;
        return Objects.equals(number, contract.number) &&
                Objects.equals(tariff, contract.tariff) &&
                block == contract.block &&
                Objects.equals(password, contract.password) &&
                Objects.equals(balance, contract.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, tariff, block, password, balance);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "number='" + number + '\'' +
                ", client=" + client.getName() + client.getSurname() +
                ", tariff=" + tariff +
                ", block=" + block +
                ", password='" + password + '\'' +
                ", balance=" + balance + '\'' +
                '}';
    }
}
