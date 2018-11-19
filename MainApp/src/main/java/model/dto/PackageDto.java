package model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PackageDto implements Serializable {
    private int id;
    @JsonBackReference
    private TariffDto tariff;
    @JsonBackReference
    private AdditionDto addition;

    public TariffDto getTariff() {
        return tariff;
    }

    public void setTariff(TariffDto tariff) {
        this.tariff = tariff;
    }

    public AdditionDto getAddition() {
        return addition;
    }

    public void setAddition(AdditionDto addition) {
        this.addition = addition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageDto that = (PackageDto) o;
        return id == that.id &&
                Objects.equals(tariff, that.tariff) &&
                Objects.equals(addition, that.addition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tariff, addition);
    }

    @Override
    public String toString() {
        return "PackageDto{" +
                "id=" + id +
                '}';
    }
}
