package model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class TariffFormDto extends AbstractDto {
    private String name;
    private int sms;
    private int minutes;
    private int internet;
    private String description;
    private BigDecimal price;
    private List<Integer> addIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Integer> getAddIds() {
        return addIds;
    }

    public void setAddIds(List<Integer> addIds) {
        this.addIds = addIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TariffFormDto that = (TariffFormDto) o;
        return sms == that.sms &&
                minutes == that.minutes &&
                internet == that.internet &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(addIds, that.addIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sms, minutes, internet,
                description, price, addIds);
    }

    @Override
    public String toString() {
        return "TariffFormDto{" +
                "name='" + name + '\'' +
                ", sms=" + sms +
                ", minutes=" + minutes +
                ", internet=" + internet +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
