package model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "package")
public class Package implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "PACKAGE_TARIFF_ID")
    private Tariff tariff;

    @ManyToOne
    @JoinColumn(name = "PACKAGE_ADDITION_ID")
    private Addition addition;

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Addition getAddition() {
        return addition;
    }

    public void setAddition(Addition addition) {
        this.addition = addition;
    }

    @Override
    public String toString() {
        return "Package{" +
                "tariff=" + tariff.getName() +
                ", addition=" + addition.getName() +
                '}';
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
        Package aPackage = (Package) o;
        return id == aPackage.id &&
                Objects.equals(tariff, aPackage.tariff) &&
                Objects.equals(addition, aPackage.addition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tariff, addition);
    }
}
