package model.dto;

import java.io.Serializable;
import java.util.Objects;

public class AddStatus implements Serializable {
    public enum Status {NONE, MANDATORY, INCOMPATIBLE}

    private int id;
    private String name;
    private Status status = Status.NONE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddStatus addStatus = (AddStatus) o;
        return Objects.equals(name, addStatus.name) &&
                status == addStatus.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status);
    }

    @Override
    public String toString() {
        return "AddStatus{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
