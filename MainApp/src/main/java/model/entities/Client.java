package model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Presents table Client in DB
 */
@Entity
@Table(name = "client")
public class Client extends AbstractEntity implements Serializable {
    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "BIRTHDAY")
    private String birthday;

    @Column(name = "PASSPORT_SERIES")
    private String passportSeries;

    @Column(name = "PASSPORT_NUMBER")
    private String passportNumber;

    @Column(name = "PASSPORT_ISSUED_BY")
    private String passportIssuedBy;

    @Column(name = "PASSPORT_ISSUE_DATE")
    private String passportIssueDate;

    @Column(name = "PASSPORT_DIVISION_CODE")
    private String passportDivisionCode;

    @Column(name = "ADDRESS")
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Contract> contracts;

    @Column(name = "EMAIL")
    private String email;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Contract> getContracts() {
        if (contracts == null)
            return new ArrayList<>();
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) &&
                Objects.equals(surname, client.surname) &&
                Objects.equals(birthday, client.birthday) &&
                Objects.equals(passportSeries, client.passportSeries) &&
                Objects.equals(passportNumber, client.passportNumber) &&
                Objects.equals(passportIssuedBy, client.passportIssuedBy) &&
                Objects.equals(passportIssueDate, client.passportIssueDate) &&
                Objects.equals(passportDivisionCode, client.passportDivisionCode) &&
                Objects.equals(address, client.address) &&
                Objects.equals(contracts, client.contracts) &&
                Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthday, passportSeries, passportNumber, passportIssuedBy,
                passportIssueDate, passportDivisionCode, address, email);
    }

    @Override
    public String toString() {
        return "Client{" +
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
