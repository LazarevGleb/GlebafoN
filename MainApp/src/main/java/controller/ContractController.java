package controller;

import exceptions.BusinessLogicException;
import exceptions.ControllerException;
import model.dto.*;
import model.services.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import utils.AttributeName;
import utils.ControllerPath;
import utils.ViewPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ContractController {
    private ContractService contractService;
    private HttpServletRequest request;
    private Basket basket;

    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    public ContractController(ContractService contractService, HttpServletRequest request,
                              Basket basket) {
        this.contractService = contractService;
        this.request = request;
        this.basket = basket;
    }

    @PostMapping(value = ControllerPath.ID_PSW_ADD)
    @ControllerException
    public String pswAdd(@PathVariable("id") int id,
                         @ModelAttribute("contract") final ContractDto dto) throws BusinessLogicException {
        ContractDto contractDto = contractService.getById(id);
        contractDto.setPassword(dto.getPassword());
        contractService.updateContract(contractDto);
        return ViewPath.SUCCESS;
    }

    @PostMapping(value = ControllerPath.ID_CONFIRM_PSW)
    @ControllerException
    public String confirmPsw(@ModelAttribute("contract") final ContractDto dto) throws BusinessLogicException {
        ContractDto contractDto = contractService.getByPassword(dto.getPassword());
        if (contractDto == null)
            return ViewPath.ERROR_LOGIN_CLIENT;
        else {
            return ControllerPath.REDIRECT + ControllerPath.CLIENT;
        }
    }

    @SuppressWarnings("unchecked")
    private void populateBasket(HttpSession session, String attribute) {
        logger.debug("populateBasket() : {}, {}", session, attribute);
        switch (attribute) {
            case AttributeName.TARIFF:
                TariffDto tDto = ((TariffDto) session.getAttribute(AttributeName.TARIFF));
                basket.chooseTariff(tDto);
                break;
            case AttributeName.CHOSEN_ADDITIONS:
                Set<AdditionDto> adds = (Set<AdditionDto>) session.getAttribute(AttributeName.CHOSEN_ADDITIONS);
                basket.addAddition(adds);
                session.setAttribute(AttributeName.CHOSEN_ADDITIONS, new HashSet<AdditionDto>());
                break;
            case AttributeName.DELETE_ADDITIONS:
                basket.getDeletedAdditions().addAll(
                        (Set<AdditionDto>) session.getAttribute(AttributeName.DELETE_ADDITIONS));
                basket.removeAllAdditions(basket.getDeletedAdditions());
                session.setAttribute(AttributeName.DELETE_ADDITIONS, new HashSet<AdditionDto>());
                break;
            default:
                break;
        }
    }

    @GetMapping(value = ControllerPath.CLIENT_LOGIN_FORM)
    @ControllerException
    public ModelAndView clientLogInForm() {
        return new ModelAndView(ViewPath.CLIENT_LOGIN_FORM, AttributeName.CONTRACT, new ContractDto());
    }

    @PostMapping(value = ControllerPath.CLIENT_LOGIN)
    @ControllerException
    public String clientLogIn(@ModelAttribute("contract") final ContractDto dto,
                              final ModelMap model) throws BusinessLogicException {
        ContractDto contract = contractService.getByNumber(dto.getNumber());
        if (contract == null)
            return ViewPath.ERROR_LOGIN_CLIENT;
        String password = contract.getPassword();
        model.addAttribute(AttributeName.CONTRACT, contract);
        if (password == null) {
            return ViewPath.CLIENT_NEW_PSW;
        } else {
            return ViewPath.CLIENT_ENTER_PSW;
        }
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_SHOW_CONTRACTS)
    @ControllerException
    public String showAllContracts(Model model) throws BusinessLogicException {
        logger.debug("showAllContracts() : {}", model);
        model.addAttribute(AttributeName.CONTRACT_LIST, contractService.getAll());
        return ViewPath.CONTRACT_LIST;
    }

    @PostMapping(value = ControllerPath.CLIENT_TARIFFS,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto chooseTariff(@RequestBody Integer tId) throws BusinessLogicException {
        logger.debug("chooseTariff() : {}", tId);
        TariffDto tariff = contractService.getTariffById(tId);

        HttpSession session = request.getSession();
        session.setAttribute(AttributeName.TARIFF, tariff);
        populateBasket(session, AttributeName.TARIFF);

        return new ResponseDto(200, "OK", basket);
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_CONTRACT_BLOCK)
    @ControllerException
    public String blockContract(@PathVariable("contractId") int id) throws BusinessLogicException {
        logger.debug("blockContract() : {}", id);
        ContractDto contract = contractService.getById(id);
        contractService.inverseBlockStatus(contract);
        return ControllerPath.REDIRECT + ControllerPath.EMPLOYEE_SHOW_CONTRACTS;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_CHANGE_NUMBER)
    @ControllerException
    public String showChangeNumber(@PathVariable("id") int id, Model model) throws BusinessLogicException {
        model.addAttribute(AttributeName.NUMBER_LIST, contractService.getNumberList());
        model.addAttribute(AttributeName.CONTRACT, contractService.getById(id));

        return ViewPath.CHANGE_NUMBER;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_CHANGE_NUMBER)
    @ControllerException
    public String changeNumber(@PathVariable("id") int id,
                               @ModelAttribute("contract") ContractDto dto) throws BusinessLogicException {
        logger.debug("changeNumber() : {}, {}", id, dto);
        ContractDto contract = contractService.getById(id);
        contract.setNumber(dto.getNumber());
        contractService.updateContract(contract);
        return ControllerPath.REDIRECT + ControllerPath.EMPLOYEE_SHOW_CONTRACTS;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_CHANGE_TARIFF)
    @ControllerException
    public String showChangeTariff(@PathVariable("id") int id, Model model) throws BusinessLogicException {
        model.addAttribute(AttributeName.CONTRACT, contractService.getById(id));
        model.addAttribute(AttributeName.TARIFF_LIST, contractService.getTariffList());
        return ViewPath.CHANGE_TARIFF;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_CHANGE_TARIFF)
    @ControllerException
    public String changeTariff(@PathVariable("id") int id,
                               @ModelAttribute("contract") ContractDto dto) throws BusinessLogicException {
        logger.debug("changeTariff() : {}, {}", id, dto);
        ContractDto contract = contractService.getById(id);
        int tariffId = dto.getTariff().getId();
        contract.setTariff(contractService.getTariffById(tariffId));
        contractService.updateContract(contract);
        return ControllerPath.REDIRECT + ControllerPath.EMPLOYEE_SHOW_CONTRACTS;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_EDIT_OPTIONS)
    @ControllerException
    public String showEditOptions(@PathVariable("id") int id, Model model) throws BusinessLogicException {
        ContractDto contract = contractService.getById(id);
        ExpenseDto expense = new ExpenseDto();
        expense.setContract(contract);
        expense.setOptionIds(contractService.getAvailableOptionsIds(contract));
        expense.setOptions(contractService.getAvailableOptions(contract));
        model.addAttribute(AttributeName.CONTRACT, contract);
        model.addAttribute(AttributeName.EXPENSE, expense);

        return ViewPath.EDIT_OPTIONS;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_CONTRACT_REMOVE_OPTION)
    @ControllerException
    public String removeOption(@PathVariable("contractId") int cId,
                               @PathVariable("addId") int aId, Model model) throws BusinessLogicException {
        logger.debug("removeOption() : {}, {}, {}", cId, aId, model);
        ContractDto contract = contractService.getById(cId);
        contract.getAdditions().remove(contractService.getAdditionById(aId));
        contractService.updateContract(contract);
        model.addAttribute(AttributeName.CONTRACT, contract);
        model.addAttribute(AttributeName.OPTIONS, contractService.getAvailableOptions(contract));

        ExpenseDto expense = new ExpenseDto();
        expense.setContract(contract);
        expense.setOptionIds(contractService.getAvailableOptionsIds(contract));
        expense.setOptions(contractService.getAvailableOptions(contract));
        model.addAttribute(AttributeName.EXPENSE, expense);

        return String.format(ViewPath.EMPLOYEE_EDIT_OPTIONS, cId);
    }

    @PostMapping(value = ControllerPath.CLIENT_OPTIONS,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto removeConnectedOptions(@RequestBody Integer aId) throws BusinessLogicException {
        logger.debug("removeConnectedOptions() : {}", aId);
        AdditionDto additionDto = contractService.getAdditionById(aId);

        HttpSession session = request.getSession();
        Set<AdditionDto> delAdds = (Set<AdditionDto>)
                session.getAttribute(AttributeName.DELETE_ADDITIONS);
        delAdds.add(additionDto);
        session.setAttribute(AttributeName.DELETE_ADDITIONS, delAdds);
        populateBasket(session, AttributeName.DELETE_ADDITIONS);

        return new ResponseDto(200, "OK", basket);
    }

    @PostMapping(value = ControllerPath.CLIENT_CHOOSE_OPTION,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto chooseOption(@RequestBody Integer id) throws BusinessLogicException {
        logger.debug("chooseOption() : {}", id);
        AdditionDto additionDto = contractService.getAdditionById(id);

        HttpSession session = request.getSession();
        Set<AdditionDto> chAdds = (Set<AdditionDto>)
                session.getAttribute(AttributeName.CHOSEN_ADDITIONS);
        chAdds.add(additionDto);
        session.setAttribute(AttributeName.CHOSEN_ADDITIONS, chAdds);
        populateBasket(session, AttributeName.CHOSEN_ADDITIONS);

        return new ResponseDto(200, "OK", basket);
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_EDIT_OPTIONS)
    @ControllerException
    public String editOptions(@PathVariable("id") int id,
                              @ModelAttribute("contract") ContractDto dto,
                              @ModelAttribute("expense") ExpenseDto expenseDto,
                              Model model) throws BusinessLogicException {
        if (expenseDto.getOptionIds().isEmpty()) return ViewPath.EDIT_OPTIONS;
        logger.debug("editOptions() : {}, {}, {}, {}", id, dto,
                expenseDto, model);
        ContractDto contract = contractService.getById(id);
        contract.getAdditions().addAll(contractService.getAdditionListByIdList(expenseDto.getOptionIds()));
        contractService.updateContract(contract);

        model.addAttribute(AttributeName.CONTRACT, contract);
        model.addAttribute(AttributeName.OPTIONS, contractService.getAvailableOptions(contract));

        ExpenseDto expense = new ExpenseDto();
        expense.setContract(contract);
        expense.setOptionIds(contractService.getAvailableOptionsIds(contract));
        expense.setOptions(contractService.getAvailableOptions(contract));
        model.addAttribute(AttributeName.EXPENSE, expense);
        return ViewPath.EDIT_OPTIONS;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_RETURN_TO_CONTRACTS)
    @ControllerException
    public ModelAndView returnToListContracts() throws BusinessLogicException {
        return new ModelAndView(ViewPath.CONTRACT_LIST, AttributeName.CONTRACT_LIST, contractService.getAll());
    }

    @GetMapping(value = ControllerPath.CLIENT_SUBMIT_BASKET)
    @ControllerException
    public String submitBasket() throws BusinessLogicException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String number = user.getUsername();
        contractService.submitBasket(contractService.getByNumber(number), basket);

        HttpSession session = request.getSession();
        session.invalidate();
        session = request.getSession(true);
        session.setAttribute(AttributeName.TARIFF, new TariffDto());
        session.setAttribute(AttributeName.CHOSEN_ADDITIONS, new HashSet<AdditionDto>());
        session.setAttribute(AttributeName.DELETE_ADDITIONS, new HashSet<AdditionDto>());

        return ControllerPath.REDIRECT + ControllerPath.CLIENT;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_FIND_CLIENT)
    @ControllerException
    public String showFindForm(ModelMap modelMap) {
        modelMap.addAttribute(AttributeName.CONTRACT, new ContractDto());
        return ViewPath.FIND_CLIENT_FORM;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_SHOW_SEARCH_RESULT)
    @ControllerException
    public String showSearchResult(@ModelAttribute("contract") final ContractDto contract,
                                   final ModelMap model) throws BusinessLogicException {
        logger.debug("showSearchResult() : {}, {}", contract, model);
        ClientDto clientDto = contractService.getByNumber(contract.getNumber()).getClient();
        model.addAttribute(AttributeName.CLIENT, clientDto);
        model.addAttribute(AttributeName.CONTRACT, new ContractDto());
        return ViewPath.FIND_CLIENT_FORM;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_ADD_NUMBER)
    @ControllerException
    public String addNumberForm(@PathVariable("id") int id, Model model) throws BusinessLogicException {
        model.addAttribute(AttributeName.CLIENT, contractService.getClientById(id));
        model.addAttribute(AttributeName.CONTRACT, new ContractFormDto());
        model.addAttribute(AttributeName.NUMBER_LIST, contractService.getNumberList());
        model.addAttribute(AttributeName.TARIFF_LIST, contractService.getTariffList());
        model.addAttribute(AttributeName.ADDITION_LIST, contractService.getAllAdditions());
        return ViewPath.NEW_CONTRACT;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_ADD_NUMBER)
    @ControllerException
    public String addNumber(@PathVariable("id") int id,
                            @ModelAttribute("contract") ContractFormDto contractFormDto) throws BusinessLogicException {
        logger.debug("addNumber() : {}, {}", id, contractFormDto);
        contractService.addClientContract(id, contractFormDto);
        return ControllerPath.REDIRECT + ControllerPath.EMPLOYEE_SHOW_CLIENTS;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_DELETE_CONTRACT)
    @ControllerException
    public String deleteContract(@PathVariable("id") int id) throws BusinessLogicException {
        logger.debug("deleteContract() : {}", id);
        contractService.deleteById(id);
        return ControllerPath.REDIRECT + ControllerPath.EMPLOYEE_SHOW_CONTRACTS;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_CLIENT_BALANCE)
    @ControllerException
    public String getClientBalance(@PathVariable("id") int id, Model model) throws BusinessLogicException {
        ContractDto contractDto = contractService.getById(id);
        model.addAttribute(AttributeName.CONTRACT, contractDto);
        return ViewPath.EMPLOYEE_BALANCE_FORM;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_CLIENT_BALANCE)
    @ControllerException
    public String setClientBalance(@PathVariable("id") int id,
                                   @ModelAttribute("contract") ContractDto dto) throws BusinessLogicException {
        logger.debug("setClientBalance() : {}, {}", id, dto);
        ContractDto contract = contractService.getById(id);
        contract.setBalance(contract.getBalance().add(dto.getBalance()));
        contractService.updateContract(contract);
        return ControllerPath.REDIRECT + ControllerPath.EMPLOYEE_SHOW_CONTRACTS;
    }

    @GetMapping(value = ControllerPath.CLIENT_REMOVE_TARIFF_FROM_BASKET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto removeTariffFromBasket() {
        logger.debug("removeTariffFromBasket()");
        basket.removeTariff();
        return new ResponseDto(200, "OK", basket);
    }

    @PostMapping(value = ControllerPath.CLIENT_REMOVE_OPTION_FROM_BASKET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto removeOptionFromBasket(@RequestBody Integer id) throws BusinessLogicException {
        logger.debug("removeOptionFromBasket() : {}", id);
        AdditionDto add = contractService.getAdditionById(id);
        basket.removeBasketAdd(add);
        return new ResponseDto(200, "OK", basket);
    }
}
