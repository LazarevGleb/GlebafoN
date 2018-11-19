package utils;

import model.dto.*;
import model.entities.Addition;
import model.entities.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface Converter {
    /**
     * Converts instance of BigClientDto to ClientDto without tariff, option and contract number info
     *
     * @param bcd BigClientDto that has client, tariff, option and contract number info
     * @return instance of ClientDto
     */
    ClientDto convertToClientDto(BigClientDto bcd);

    /**
     * Converts instance of BigClientDto to ContractDto without client info
     *
     * @param bcd       BigClientDto that has client, tariff, option and contract number info
     * @param clientDto ClientDto has client info
     * @return instance of ContractDto
     */
    ContractDto convertToContractDto(BigClientDto bcd, ClientDto clientDto);

    /**
     * Check which options are available for this contract
     *
     * @param contractDto specified contract
     * @param additions   actual list of all additions in DB
     * @return available additions
     */
    List<AdditionDto> checkAdditions(ContractDto contractDto, List<Addition> additions);

    /**
     * Returns all compatible additions
     *
     * @param contractDto  contract with tariff for comparing
     * @param ownAdditions options of specified tariff
     * @return list of options
     */
    List<AdditionDto> checkOwnAdditions(ContractDto contractDto, List<AdditionDto> ownAdditions);

    /**
     * Converts TariffFormDto to TariffDto
     *
     * @param tariffForm specified TFD
     * @return specified TD
     */
    TariffDto convertToTariffDto(TariffFormDto tariffForm);

    /**
     * Converts TariffDto to TariffFormDto
     *
     * @param tariff specified TD
     * @return specified TFD
     */
    TariffFormDto convertToTariffFormDto(TariffDto tariff);

    /**
     * Converts AdditionFormDto to AdditionDto
     *
     * @param additionForm specified AFD
     * @return specified AD
     */
    AdditionDto convertToAdditionDto(AdditionFormDto additionForm);

    /**
     * Converts AdditionDto to AdditionFormDto
     *
     * @param addition specified AD
     * @return specified AFD
     */
    AdditionFormDto convertToAdditionFormDto(AdditionDto addition);

    /**
     * Creates ContractDto from ContractFormDto according to client
     *
     * @param contractFormDto specified CFD
     * @param client          specified client
     * @return specified CD
     */
    ContractDto convertToContractDtoFromForm(ContractFormDto contractFormDto, Client client);

    /**
     * Updates addition according to AdditionFormDto
     *
     * @param additionForm specified AFD
     * @return updated addition
     */
    AdditionDto updateAdditionDto(AdditionFormDto additionForm);

    /**
     * Returns random phone numbers
     *
     * @return set of random numbers
     */
    Set<String> getNumbers();
}
