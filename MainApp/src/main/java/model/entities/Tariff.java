package model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;


/**
 * Tariff entity
 */
@Entity
@Table(name = "tariff")
public class Tariff extends AbstractEntity implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "SMS")
    private int sms;

    @Column(name="MINUTES")
    private int minutes;

    @Column(name="INTERNET_GB")
    private int internet;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private BigDecimal price;

    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Package> packages = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Contract> getContracts() {
        return contracts==null? new ArrayList<>() : contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getInternet() {
        return internet;
    }

    public void setInternet(int internet) {
        this.internet = internet;
    }

    public Set<Package> getPackages() {
        return packages;
    }

    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return sms == tariff.sms &&
                minutes == tariff.minutes &&
                internet == tariff.internet &&
                Objects.equals(name, tariff.name) &&
                Objects.equals(description, tariff.description) &&
                Objects.equals(price, tariff.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sms, minutes, internet, description, price);
    }
}
