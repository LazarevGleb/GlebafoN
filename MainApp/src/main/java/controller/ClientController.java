package controller;

import exceptions.BusinessLogicException;
import exceptions.ControllerException;
import model.dto.AdditionDto;
import model.dto.BigClientDto;
import model.dto.TariffDto;
import model.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import utils.AttributeName;
import utils.ControllerPath;
import utils.ViewPath;

import java.util.List;

@Controller
public class ClientController {
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_NEW_CLIENT)
    @ControllerException
    public String showForm(ModelMap model) throws BusinessLogicException {
        logger.debug("showForm() : {}", model);
        populateModel(model);
        return ViewPath.CLIENT_ADD;
    }

    private void populateModel(ModelMap model) throws BusinessLogicException {
        List<TariffDto> tariffList = clientService.getTariffList();
        List<AdditionDto> additionList = clientService.getAdditionList();
        model.addAttribute(AttributeName.TARIFF_LIST, tariffList);
        model.addAttribute(AttributeName.ADDITION_LIST, additionList);
        model.addAttribute(AttributeName.CLIENT, new BigClientDto());
        model.addAttribute(AttributeName.NUMBER_LIST, clientService.getNumberList());
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_ADD_CLIENT)
    @ControllerException
    public String submit(@ModelAttribute("client") BigClientDto bigClient,
                         BindingResult result, ModelMap model) throws BusinessLogicException {
        logger.debug("submit() : {}, {}, {}", bigClient, result, model);
        if (result.hasErrors()) {
            populateModel(model);
            return ViewPath.CLIENT_ADD;
        }

        model.addAttribute("name", bigClient.getName());
        model.addAttribute("surname", bigClient.getSurname());
        model.addAttribute("birthday", bigClient.getBirthday());
        model.addAttribute("series", bigClient.getPassportSeries());
        model.addAttribute("number", bigClient.getPassportNumber());
        model.addAttribute("issuedBy", bigClient.getPassportIssuedBy());
        model.addAttribute("issueDate", bigClient.getPassportIssueDate());
        model.addAttribute("divisionCode", bigClient.getPassportDivisionCode());
        model.addAttribute("address", bigClient.getAddress());
        model.addAttribute("email", bigClient.getEmail());
        model.addAttribute("contractNumber", bigClient.getNumber());
        model.addAttribute("balance", bigClient.getBalance());

        clientService.createFromBigClient(bigClient);
        model.addAttribute(AttributeName.CLIENTS_LIST, clientService.getAll());
        return ViewPath.CLIENT_LIST;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_SHOW_CLIENTS)
    @ControllerException
    public String showAllClients(Model model) throws BusinessLogicException {
        logger.debug("showAllClients() : {}", model);
        model.addAttribute(AttributeName.CLIENTS_LIST, clientService.getAll());
        return ViewPath.CLIENT_LIST;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_DELETE_CLIENT)
    @ControllerException
    public String deleteClient(@PathVariable("id") int id, Model model) throws BusinessLogicException {
        logger.debug("deleteClient() : {}, {}", id, model);
        clientService.deleteById(id);
        model.addAttribute(AttributeName.CLIENTS_LIST, clientService.getAll());
        return ViewPath.CLIENT_LIST;
    }
}
