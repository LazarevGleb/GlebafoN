package model.dto;

import java.io.Serializable;

public abstract class AbstractDto implements Serializable {

    public boolean isNew() {
        return (this.getId() == null);
    }

    private Integer id;
    private Boolean valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
