package model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExpenseDto extends AbstractDto {
    private ContractDto contract;
    private List<Integer> optionIds = new ArrayList<>();
    private List<AdditionDto> options = new ArrayList<>();

    public ContractDto getContract() {
        return contract;
    }

    public void setContract(ContractDto contract) {
        this.contract = contract;
    }

    public List<Integer> getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(List<Integer> optionIds) {
        this.optionIds = optionIds;
    }

    public List<AdditionDto> getOptions() {
        return options;
    }

    public void setOptions(List<AdditionDto> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseDto that = (ExpenseDto) o;
        return Objects.equals(contract, that.contract) &&
                Objects.equals(optionIds, that.optionIds) &&
                Objects.equals(options, that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, optionIds, options);
    }

    @Override
    public String
    toString() {
        return "ExpenseDto{" +
                '}';
    }
}
