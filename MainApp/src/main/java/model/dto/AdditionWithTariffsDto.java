package model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import model.entities.Addition;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@JsonSerialize(using = AddTariffSerializer.class)
public class AdditionWithTariffsDto extends AbstractDto {
    private String name;
    private Addition.Parameter parameter;
    private int value;
    private BigDecimal price;
    private BigDecimal additionActivationCost;
    private Set<PackageDto> packages = new HashSet<>();
    private Set<TariffDto> remainedTariffs = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Addition.Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Addition.Parameter parameter) {
        this.parameter = parameter;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAdditionActivationCost() {
        return additionActivationCost;
    }

    public void setAdditionActivationCost(BigDecimal additionActivationCost) {
        this.additionActivationCost = additionActivationCost;
    }

    public Set<PackageDto> getPackages() {
        return packages;
    }

    public void setPackages(Set<PackageDto> packages) {
        this.packages = packages;
    }

    public Set<TariffDto> getRemainedTariffs() {
        return remainedTariffs;
    }

    public void setRemainedTariffs(Set<TariffDto> remainedTariffs) {
        this.remainedTariffs = remainedTariffs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionWithTariffsDto that = (AdditionWithTariffsDto) o;
        return value == that.value &&
                Objects.equals(name, that.name) &&
                parameter == that.parameter &&
                Objects.equals(price, that.price) &&
                Objects.equals(additionActivationCost, that.additionActivationCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parameter, value, price, additionActivationCost);
    }

    @Override
    public String toString() {
        return "AdditionWithTariffsDto{" +
                "name='" + name + '\'' +
                ", parameter=" + parameter +
                ", value=" + value +
                ", price=" + price +
                ", additionActivationCost=" + additionActivationCost +
                '}';
    }
}
