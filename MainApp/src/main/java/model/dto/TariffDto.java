package model.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for Tariff.
 * It contains: tariff's name, its price and set of available options.
 */

public class TariffDto extends AbstractDto {
    private String name;
    private int sms;
    private int minutes;
    private int internet;
    private String description;
    private BigDecimal price;
    @JsonManagedReference
    private Set<PackageDto> packages = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Set<PackageDto> getPackages() {
        return packages;
    }

    public void setPackages(Set<PackageDto> packages) {
        this.packages = packages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TariffDto tariffDto = (TariffDto) o;
        return sms == tariffDto.sms &&
                minutes == tariffDto.minutes &&
                internet == tariffDto.internet &&
                Objects.equals(name, tariffDto.name) &&
                Objects.equals(description, tariffDto.description) &&
                Objects.equals(price, tariffDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sms, minutes, internet, description, price);
    }

    @Override
    public String toString() {
        return "TariffDto{" +
                "name='" + name + '\'' +
                ", sms=" + sms +
                ", minutes=" + minutes +
                ", internet=" + internet +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
