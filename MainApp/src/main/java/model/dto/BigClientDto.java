package model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class BigClientDto extends AbstractDto {
    private String name;
    private String surname;
    private String birthday;
    private String passportSeries;
    private String passportNumber;
    private String passportIssuedBy;
    private String passportIssueDate;
    private String passportDivisionCode;
    private String address;
    private String email;
    private String number;
    private Integer tariffId;
    private List<Integer> additionIds;
    private BigDecimal balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportIssuedBy() {
        return passportIssuedBy;
    }

    public void setPassportIssuedBy(String passportIssuedBy) {
        this.passportIssuedBy = passportIssuedBy;
    }

    public String getPassportIssueDate() {
        return passportIssueDate;
    }

    public void setPassportIssueDate(String passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public String getPassportDivisionCode() {
        return passportDivisionCode;
    }

    public void setPassportDivisionCode(String passportDivisionCode) {
        this.passportDivisionCode = passportDivisionCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public void setTariffId(Integer tariffId) {
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
        BigClientDto that = (BigClientDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(passportSeries, that.passportSeries) &&
                Objects.equals(passportNumber, that.passportNumber) &&
                Objects.equals(passportIssuedBy, that.passportIssuedBy) &&
                Objects.equals(passportIssueDate, that.passportIssueDate) &&
                Objects.equals(passportDivisionCode, that.passportDivisionCode) &&
                Objects.equals(address, that.address) &&
                Objects.equals(email, that.email) &&
                Objects.equals(number, that.number) &&
                Objects.equals(tariffId, that.tariffId) &&
                Objects.equals(additionIds, that.additionIds) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthday, passportSeries, passportNumber, passportIssuedBy,
                passportIssueDate, passportDivisionCode, address, email, number, tariffId, additionIds,
                balance);
    }

    @Override
    public String toString() {
        return "BigClientDto{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", passportSeries='" + passportSeries + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", passportIssuedBy='" + passportIssuedBy + '\'' +
                ", passportIssueDate='" + passportIssueDate + '\'' +
                ", passportDivisionCode='" + passportDivisionCode + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                '}';
    }
}
