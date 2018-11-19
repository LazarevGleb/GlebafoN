package model.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class Basket implements Serializable {
    private TariffDto chosenTariff;
    private Set<AdditionDto> chosenAdditions = new HashSet<>();
    private Set<AdditionDto> deletedAdditions = new HashSet<>();
    private BigDecimal totalPrice;
    private BigDecimal totalActivationCost;
    private BigDecimal totalSum;

    public void totalSum() {
        totalSum = totalPrice.add(totalActivationCost);
    }

    public boolean isFree() {
        return chosenTariff == null && chosenAdditions.isEmpty();
    }

    public boolean isEmpty() {
        return chosenTariff == null &&
                chosenAdditions.isEmpty() &&
                deletedAdditions.isEmpty();
    }

    public void clear() {
        this.totalActivationCost = BigDecimal.ZERO;
        this.totalPrice = BigDecimal.ZERO;
        this.totalSum = BigDecimal.ZERO;
        this.chosenAdditions = new HashSet<>();
        this.deletedAdditions = new HashSet<>();
        this.chosenTariff = null;
    }

    public void chooseTariff(TariffDto tariff) {
        TariffDto previousTariff = chosenTariff;
        setChosenTariff(tariff);
        chosenTariff = tariff;
        if (previousTariff != null) {
            totalPrice = totalPrice.subtract(previousTariff.getPrice())
                    .add(chosenTariff.getPrice());
        } else {
            totalPrice = totalPrice.add(chosenTariff.getPrice());
        }
        totalSum();
    }

    public void removeTariff() {
        TariffDto previousTariff = chosenTariff;
        setChosenTariff(null);
        totalPrice = totalPrice.subtract(previousTariff.getPrice());
        totalSum();
    }

    public void addAddition(Set<AdditionDto> additionList) {
        for (AdditionDto addition : additionList) {
            if (!chosenAdditions.contains(addition)) {
                chosenAdditions.add(addition);
                totalPrice = totalPrice.add(addition.getPrice());
                totalActivationCost = totalActivationCost.add(
                        addition.getAdditionActivationCost());
                totalSum();
            }
        }
    }

    public void removeAllAdditions(Set<AdditionDto> additionList) {
        for (AdditionDto addition : additionList) {
            removeAddition(addition);
        }
    }

    public void removeBasketAdd(AdditionDto additionDto) {
        removeAddition(additionDto);
        if (deletedAdditions.contains(additionDto)) {
            deletedAdditions.remove(additionDto);
        }
    }

    public void removeAddition(AdditionDto additionDto) {
        if (chosenAdditions.contains(additionDto)) {
            chosenAdditions.remove(additionDto);
            totalPrice = totalPrice.subtract(additionDto.getPrice());
            totalActivationCost = totalActivationCost.subtract(
                    additionDto.getAdditionActivationCost());
            totalSum();
        }
    }

    public TariffDto getChosenTariff() {
        return chosenTariff;
    }

    public void setChosenTariff(TariffDto chosenTariff) {
        this.chosenTariff = chosenTariff;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalActivationCost() {
        return totalActivationCost;
    }

    public void setTotalActivationCost(BigDecimal totalActivationCost) {
        this.totalActivationCost = totalActivationCost;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public Set<AdditionDto> getChosenAdditions() {
        return chosenAdditions;
    }

    public void setChosenAdditions(Set<AdditionDto> chosenAdditions) {
        this.chosenAdditions = chosenAdditions;
    }

    public Set<AdditionDto> getDeletedAdditions() {
        return deletedAdditions;
    }

    public void setDeletedAdditions(Set<AdditionDto> deletedAdditions) {
        this.deletedAdditions = deletedAdditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(totalPrice, basket.totalPrice) &&
                Objects.equals(totalActivationCost, basket.totalActivationCost) &&
                Objects.equals(totalSum, basket.totalSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPrice, totalActivationCost, totalSum);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "totalPrice=" + totalPrice +
                ", totalActivationCost=" + totalActivationCost +
                ", totalSum=" + totalSum +
                '}';
    }
}
