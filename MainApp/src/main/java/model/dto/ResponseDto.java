package model.dto;

import org.hibernate.type.SerializationException;

import java.io.Serializable;
import java.util.Objects;

public class ResponseDto implements Serializable{
    private int code;
    private String status;
    private Serializable body;

    public ResponseDto(int code, String status, Object body) {
        this.code = code;
        this.status = status;
        try {
            this.body = (Serializable) body;
        } catch (ClassCastException ex){
            throw new SerializationException("Not serializable", null);
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Serializable getBody() {
        return body;
    }

    public void setBody(Serializable body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseDto that = (ResponseDto) o;
        return code == that.code &&
                Objects.equals(status, that.status) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, status, body);
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", body=" + body +
                '}';
    }
}
