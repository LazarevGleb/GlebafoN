package controller;

import exceptions.BusinessLogicException;
import exceptions.ControllerException;
import model.dto.*;
import model.services.AccountService;
import model.services.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import utils.AttributeName;
import utils.ControllerPath;
import utils.ViewPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashSet;

@Controller
public class AccountController {
    private AccountService accountService;
    private HttpServletRequest request;
    private Basket basket;
    private SecurityService securityService;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    public AccountController(AccountService accountService, HttpServletRequest request,
                             Basket basket, SecurityService securityService) {
        this.accountService = accountService;
        this.request = request;
        this.basket = basket;
        this.securityService = securityService;
    }

    @GetMapping(value = ControllerPath.CREATE_ACCOUNT_FORM)
    @ControllerException
    public String registration(Model model) {
        logger.debug("registration() : {}", model);
        model.addAttribute(AttributeName.USER, new UserDto());

        return ViewPath.REGISTRATION;
    }

    @PostMapping(value = ControllerPath.REGISTRATION)
    @ControllerException
    public String registration(@ModelAttribute("user") UserDto user,
                               BindingResult bindingResult) throws BusinessLogicException {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return ViewPath.REGISTRATION;
        }

        if (bindingResult.hasErrors()) {
            return ViewPath.REGISTRATION;
        }
        ContractDto contractDto = accountService.getContractByNumber(user.getNumber());
        if (contractDto == null) {
            return ViewPath.ERROR_NO_CONTRACT_FOUND;
        }
        accountService.update(user);
        securityService.autoLogin(user.getNumber(), user.getConfirmPassword());

        return ControllerPath.REDIRECT + ControllerPath.WELCOME;
    }

    @GetMapping(value = {ControllerPath.ROOT, ControllerPath.WELCOME})
    @ControllerException
    public String welcome() {
        return ViewPath.WELCOME;
    }

    @GetMapping(value = ControllerPath.DIRECT)
    @ControllerException
    public String direct() {
        logger.debug("direct()");
        String number = request.getUserPrincipal().getName();
        switch (number) {
            case "admin":
                return ControllerPath.REDIRECT + ControllerPath.ADMIN;
            case AttributeName.MANAGER:
                return ControllerPath.REDIRECT + ControllerPath.EMPLOYEE;
            default:
                return ControllerPath.REDIRECT + ControllerPath.CLIENT;
        }
    }

    @GetMapping(value = ControllerPath.CLIENT)
    @ControllerException
    public String client(Model model) throws BusinessLogicException {
        logger.debug("client() : {}", model);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String number = user.getUsername();

        HttpSession session = request.getSession(true);
        session.setAttribute(AttributeName.TARIFF, new TariffDto());
        session.setAttribute(AttributeName.CHOSEN_ADDITIONS, new HashSet<AdditionDto>());
        session.setAttribute(AttributeName.DELETE_ADDITIONS, new HashSet<AdditionDto>());
        basket.clear();

        ContractDto contract = accountService.getContractByNumber(number);
        model.addAttribute(AttributeName.CONTRACT, contract);
        model.addAttribute(AttributeName.BASKET, basket);

        return ViewPath.CLIENT_PAGE;
    }

    @GetMapping(value = ControllerPath.LOGIN)
    @ControllerException
    public String loginForm(Model model, String error) {
        logger.debug("loginForm() : {}, {}", model, error);
        model.addAttribute(AttributeName.ERROR, "Error is here!");
        model.addAttribute(AttributeName.CONTRACT, new ContractDto());
        return ViewPath.LOGIN;
    }

    @PostMapping(value = ControllerPath.LOGIN)
    @ControllerException
    public String loginClient(@ModelAttribute("contract") ContractDto contractDto) throws BusinessLogicException{
        ContractDto contract = accountService.getContractByNumber(contractDto.getNumber());
        return ControllerPath.REDIRECT + ControllerPath.CLIENT_PAGE
                + ControllerPath.ROOT + contract.getId();
    }

    @GetMapping(value = ControllerPath.EMPLOYEE)
    @ControllerException
    public String managerPage() {
        return ViewPath.MANAGER_PAGE;
    }

    // for 403 access denied page
    @GetMapping(value = ControllerPath.ERROR403)
    @ControllerException
    public ModelAndView accessDenied(Principal user) throws BusinessLogicException {
        logger.debug("accessDenied() : {}", user.getName());
        ModelAndView model = new ModelAndView();
        ContractDto contractDto = accountService.getContractByNumber(user.getName());

        model.addObject(AttributeName.MESSAGE, "Unfortunately, " +
                contractDto.getClient().getName() + ", you are forbidden " +
                "to access the manager's page :'( ");
        model.setViewName(ViewPath.ERROR403);
        return model;
    }

    @GetMapping(value = ControllerPath.LOG_IN_FORM)
    @ControllerException
    public ModelAndView logForm() {
        return new ModelAndView(ViewPath.LOG_AS_EMPLOYEE, AttributeName.ACCOUNT, new AccountDto());
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_LOGIN)
    @ControllerException
    public String logIn(@ModelAttribute("admin") final AdminDto dto) {
        logger.debug("logIn() : {}", dto);
        if (dto.getLogin().equals(AttributeName.MANAGER) && dto.getPassword().equals(AttributeName.MANAGER))
            return ViewPath.MANAGER_PAGE;
        else
            return ViewPath.LOGIN_ERROR_PAGE;
    }
}
