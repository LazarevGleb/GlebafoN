package model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Presents table Addition in DB
 */
@Entity
@Table(name = "addition")
public class Addition extends AbstractEntity implements Serializable {

    public enum Parameter {SMS, MINUTES, INTERNET}

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "ACTIVATION_COST")
    private BigDecimal additionActivationCost;

    @Column(name = "PARAMETER")
    @Enumerated(EnumType.STRING)
    private Parameter parameter = Parameter.SMS;

    @Column(name = "VALUE")
    private int value;

    @OneToMany(mappedBy = "addition", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Package> packages = new HashSet<>();

    @ManyToMany(mappedBy = "additions")
    private List<Contract> contracts;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "mandatoryOptions",
            joinColumns = @JoinColumn(name = "MANDATORY_OPTION_1"),
            inverseJoinColumns = @JoinColumn(name = "MANDATORY_OPTION_2")
    )
    private Set<Addition> mandatoryOptions = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "incompatibleOptions",
            joinColumns = @JoinColumn(name = "INCOMPATIBLE_OPTION_1"),
            inverseJoinColumns = @JoinColumn(name = "INCOMPATIBLE_OPTION_2")
    )
    private Set<Addition> incompatibleOptions = new HashSet<>();

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

    public List<Contract> getContracts() {
        return contracts == null ? new ArrayList<>() : contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Set<Package> getPackages() {
        return packages;
    }

    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }

    public Set<Addition> getMandatoryOptions() {
        return mandatoryOptions;
    }

    public void setMandatoryOptions(Set<Addition> mandatoryOptions) {
        this.mandatoryOptions = mandatoryOptions;
    }

    public Set<Addition> getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void setIncompatibleOptions(Set<Addition> incompatibleOptions) {
        this.incompatibleOptions = incompatibleOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Addition addition = (Addition) o;
        return value == addition.value &&
                Objects.equals(name, addition.name) &&
                Objects.equals(price, addition.price) &&
                Objects.equals(additionActivationCost, addition.additionActivationCost) &&
                parameter == addition.parameter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, additionActivationCost,
                parameter, value);
    }
}
