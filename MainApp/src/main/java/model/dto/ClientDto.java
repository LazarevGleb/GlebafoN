package model.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO for Client.
 * It contains: client's name and surname, client's birthday, passport data,
 * the address, number of contract, email and password for client's personal account.
 */

public class ClientDto extends AbstractDto {
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
    @JsonManagedReference
    private List<ContractDto> contracts = new ArrayList<>();

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

    public List<ContractDto> getContracts() {
        return contracts;
    }

    public void setContracts(List<ContractDto> contracts) {
        this.contracts = contracts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDto clientDto = (ClientDto) o;
        return Objects.equals(name, clientDto.name) &&
                Objects.equals(surname, clientDto.surname) &&
                Objects.equals(birthday, clientDto.birthday) &&
                Objects.equals(passportSeries, clientDto.passportSeries) &&
                Objects.equals(passportNumber, clientDto.passportNumber) &&
                Objects.equals(passportIssuedBy, clientDto.passportIssuedBy) &&
                Objects.equals(passportIssueDate, clientDto.passportIssueDate) &&
                Objects.equals(passportDivisionCode, clientDto.passportDivisionCode) &&
                Objects.equals(address, clientDto.address) &&
                Objects.equals(email, clientDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthday, passportSeries, passportNumber,
                passportIssuedBy, passportIssueDate, passportDivisionCode, address, email);
    }

    @Override
    public String toString() {
        return "ClientDto{" +
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
                '}';
    }
}
