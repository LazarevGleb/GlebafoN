package model.dto;

import java.util.Objects;

public class UserDto extends AbstractDto {
    private String number;
    private String password;
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(number, userDto.number) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(confirmPassword, userDto.confirmPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, password, confirmPassword);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "number='" + number + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
