package controller;

import exceptions.BusinessLogicException;
import exceptions.ControllerException;
import model.dto.ContractDto;
import model.dto.ResponseDto;
import model.services.ContractService;
import model.services.TariffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import utils.ControllerPath;

import java.math.BigDecimal;

@RestController
public class RSController {
    private ContractService contractService;
    private TariffService tariffService;
    private static Logger logger = LoggerFactory.getLogger(RSController.class);

    @Autowired
    public RSController(ContractService contractService, TariffService tariffService) {
        this.contractService = contractService;
        this.tariffService = tariffService;
    }

    @GetMapping(value = ControllerPath.CLIENT_BLOCK_SELF,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ControllerException
    public ResponseDto blockSelf() throws BusinessLogicException {
        logger.debug("blockSelf()");
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        ContractDto contract = contractService.getByNumber(user.getUsername());
        contractService.inverseClientBlockStatus(contract);
        return new ResponseDto(200, "OK", contractService.getByNumber(user.getUsername()));
    }

    @CrossOrigin
    @GetMapping(value = ControllerPath.TARIFFS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ControllerException
    public ResponseDto restGetTariffs() throws BusinessLogicException {
        logger.debug("restGetTariffs()");
        return new ResponseDto(200, "OK", tariffService.getTariffsWithOptions());
    }

    @PostMapping(value = ControllerPath.CLIENT_BALANCE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ControllerException
    public ResponseDto topUpBalance(@RequestBody BigDecimal balance) throws BusinessLogicException {
        logger.debug("topUpBalance() : {}", balance);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ContractDto contract = contractService.getByNumber(user.getUsername());
        contract.setBalance(contract.getBalance().add(balance));
        contractService.update(contract);
        return new ResponseDto(200, "OK", contractService.getById(contract.getId()));
    }

    @GetMapping(value = ControllerPath.CLIENT_OPTIONS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ControllerException
    public ResponseDto getConnectedOptions() throws BusinessLogicException {
        logger.debug("getConnectedOptions()");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ContractDto contract = contractService.getByNumber(user.getUsername());
        return new ResponseDto(200, "OK", contract.getAdditions());
    }

    @GetMapping(value = ControllerPath.CLIENT_AVAILABLE_OPTIONS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ControllerException
    public ResponseDto getAvailableOptions() throws BusinessLogicException {
        logger.debug("getAvailableOptions()");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ContractDto contract = contractService.getByNumber(user.getUsername());
        return new ResponseDto(200, "OK", contractService.getAvailableOptions(contract));
    }
}
