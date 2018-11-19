package utils;

public class ControllerPath {
    private ControllerPath() {
    }

    public static final String ADMIN = "/admin";
    public static final String CLIENT = "/client";
    public static final String CLIENT_AVAILABLE_OPTIONS = "/client/avOptions";
    public static final String CLIENT_BALANCE = "/client/balance";
    public static final String CLIENT_BLOCK_SELF = "/client/blockSelf";
    public static final String CLIENT_CHOOSE_OPTION = "/client/chooseOption";
    public static final String CLIENT_OPTIONS = "/client/options";
    public static final String CLIENT_LOGIN = "clientLogIn";
    public static final String CLIENT_LOGIN_FORM = "/clientLogInForm";
    public static final String CLIENT_PAGE = "/clientPage";
    public static final String CLIENT_REMOVE_TARIFF_FROM_BASKET = "/client/removeTariffFromBasket";
    public static final String CLIENT_REMOVE_OPTION_FROM_BASKET = "/client/removeOptionFromBasket";
    public static final String CLIENT_SUBMIT_BASKET = "/client/submitBasket";
    public static final String CLIENT_TARIFFS = "/client/tariffs";
    public static final String CREATE_ACCOUNT_FORM = "/createAccountForm";
    public static final String DIRECT = "/direct";
    public static final String EMPLOYEE = "/employee";
    public static final String EMPLOYEE_ADD_CLIENT = "/employee/addClient";
    public static final String EMPLOYEE_ADD_NUMBER = "/employee/{id}/addNumber";
    public static final String EMPLOYEE_ADDITIONS = "/employee/additions";
    public static final String EMPLOYEE_ADDITIONS_ID = "/employee/additions/{id}";
    public static final String EMPLOYEE_ADDITIONS_ADD = "/employee/additions/add";
    public static final String EMPLOYEE_ADDITIONS_UPDATE = "/employee/additions/{id}/update";
    public static final String EMPLOYEE_ADDITIONS_DELETE = "/employee/additions/{id}/delete";
    public static final String EMPLOYEE_ADDITION_REMOVE_INCOMPATIBLE_TARIFF = "/employee/removeIncompatibleTariff";
    public static final String EMPLOYEE_ADDITION_TARIFFS_REMAINED = "/employee/tariffsRemained";
    public static final String EMPLOYEE_CLIENT_BALANCE = "/employee/{id}/balance";
    public static final String EMPLOYEE_CHANGE_NUMBER = "/employee/{id}/changeNumber";
    public static final String EMPLOYEE_CHANGE_TARIFF = "/employee/{id}/changeTariff";
    public static final String EMPLOYEE_CHECK_RELATIONS = "/employee/checkRelations";
    public static final String EMPLOYEE_CONTRACT_BLOCK = "/employee/{contractId}/block";
    public static final String EMPLOYEE_CONTRACT_REMOVE_OPTION = "/employee/{contractId}/{addId}/removeOption";
    public static final String EMPLOYEE_DELETE_CLIENT = "/employee/{id}/deleteClient";
    public static final String EMPLOYEE_DELETE_CONTRACT = "employee/{id}/deleteContract";
    public static final String EMPLOYEE_EDIT_OPTIONS = "/employee/{id}/editOptions";
    public static final String EMPLOYEE_FIND_CLIENT = "/employee/findClient";
    public static final String EMPLOYEE_GET_TARIFFS = "/employee/getTariffs";
    public static final String EMPLOYEE_LOGIN = "/employee/logIn";
    public static final String EMPLOYEE_NEW_CLIENT = "/employee/newClient";
    public static final String EMPLOYEE_OPTION_FORM = "/employee/optionForm";
    public static final String EMPLOYEE_RETURN_TO_CONTRACTS = "/employee/returnToListContracts";
    public static final String EMPLOYEE_SHOW_CLIENTS = "/employee/showAllClients";
    public static final String EMPLOYEE_SHOW_CONTRACTS = "/employee/showAllContracts";
    public static final String EMPLOYEE_SHOW_SEARCH_RESULT = "/employee/showSearchResult";
    public static final String EMPLOYEE_STATUS_TABLE = "/employee/getAddStatusTable";
    public static final String EMPLOYEE_SUBMIT_RELATIONS = "/employee/submitRelations";
    public static final String EMPLOYEE_TARIFFS = "/employee/tariffs";
    public static final String EMPLOYEE_TARIFFS_ID = "/employee/tariffs/{id}";
    public static final String EMPLOYEE_TARIFFS_ADD = "/employee/tariffs/add";
    public static final String EMPLOYEE_TARIFFS_UPDATE = "/employee/tariffs/{id}/update";
    public static final String EMPLOYEE_TARIFFS_DELETE = "/employee/tariffs/{id}/delete";
    public static final String EMPLOYEE_TARIFF_REMOVE_INCOMPATIBLE_OPTION = "/employee/{tId}/{optId}/removeIncompatibleOption";
    public static final String ERROR403 = "/403";
    public static final String ID_PSW_ADD = "/{id}/pswAdd";
    public static final String ID_CONFIRM_PSW = "/confirmPsw";
    public static final String LOGIN = "/login";
    public static final String LOG_IN_FORM = "/logInForm";
    public static final String REDIRECT = "redirect:";
    public static final String REDIRECT_EMPLOYEE_ADDITIONS = "redirect:/employee/additions";
    public static final String REDIRECT_EMPLOYEE_TARIFFS = "redirect:/employee/tariffs";
    public static final String REGISTRATION = "/registration";
    public static final String ROOT = "/";
    public static final String TARIFFS = "/tariffs";
    public static final String WELCOME = "/welcome";
}
