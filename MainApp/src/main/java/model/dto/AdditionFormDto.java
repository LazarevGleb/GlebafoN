package model.dto;

import model.entities.Addition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AdditionFormDto extends AbstractDto {
    private String name;
    private Addition.Parameter parameter;
    private int value;
    private BigDecimal price;
    private BigDecimal additionActivationCost;
    private List<Integer> tariffIds;
    private Set<AddStatus> addStatuses;

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

    public BigDecimal getAdditionActivationCost() {
        return additionActivationCost;
    }

    public void setAdditionActivationCost(BigDecimal additionActivationCost) {
        this.additionActivationCost = additionActivationCost;
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

    public List<Integer> getTariffIds() {
        return tariffIds == null ? new ArrayList<>() : tariffIds;
    }

    public void setTariffIds(List<Integer> tariffIds) {
        this.tariffIds = tariffIds;
    }

    public Set<AddStatus> getAddStatuses() {
        return addStatuses;
    }

    public void setAddStatuses(Set<AddStatus> addStatuses) {
        this.addStatuses = addStatuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionFormDto that = (AdditionFormDto) o;
        return value == that.value &&
                Objects.equals(name, that.name) &&
                parameter == that.parameter &&
                Objects.equals(price, that.price) &&
                Objects.equals(additionActivationCost, that.additionActivationCost) &&
                Objects.equals(tariffIds, that.tariffIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parameter, value, price, additionActivationCost, tariffIds);
    }

    @Override
    public String toString() {
        return "AdditionFormDto{" +
                "name='" + name + '\'' +
                ", parameter=" + parameter +
                ", value=" + value +
                ", price=" + price +
                ", additionActivationCost=" + additionActivationCost +
                '}';
    }
}
