package controller;

import exceptions.BusinessLogicException;
import exceptions.ControllerException;
import model.dto.AdditionDto;
import model.dto.ResponseDto;
import model.services.AdditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import utils.AttributeName;
import utils.ControllerPath;
import utils.ViewPath;

import java.util.List;

@Controller
public class AdditionController {
    private AdditionService additionService;

    @Autowired
    public AdditionController(AdditionService additionService) {
        this.additionService = additionService;
    }

    private final Logger logger = LoggerFactory.getLogger(AdditionController.class);

    @GetMapping(value = ControllerPath.EMPLOYEE_ADDITIONS)
    @ControllerException
    public String showAllAdditions(ModelMap model) throws BusinessLogicException {
        logger.debug("showAllAdditions()");
        model.addAttribute(AttributeName.ADDITION_LIST, additionService.getAll());
        return ViewPath.ADDITION_LIST;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_ADDITIONS_ADD)
    @ControllerException
    public String showAddOptionForm(Model model) {
        logger.debug("showAddOptionForm() : {}", model);
        model.addAttribute(AttributeName.OPTION_ID, 0);
        return ViewPath.ADDITION_FORM;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_ADDITIONS_UPDATE)
    @ControllerException
    public String showUpdateOptionForm(@PathVariable("id") int id, Model model) {
        logger.debug("showUpdateOptionForm() : {}", id);
        model.addAttribute(AttributeName.OPTION_ID, id);
        return ViewPath.ADDITION_FORM;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_OPTION_FORM,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto getOptionForm(@RequestBody Integer id) throws BusinessLogicException {
        logger.debug("getOptionForm() : {}", id);
        return new ResponseDto(200, "OK", additionService.getAddWithTariffsById(id));
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_ADDITIONS_DELETE)
    @ControllerException
    public String deleteAddition(@PathVariable("id") int id) throws BusinessLogicException {
        logger.debug("deleteAddition() : {}", id);
        additionService.deleteById(id);
        return ControllerPath.REDIRECT + ControllerPath.EMPLOYEE_ADDITIONS;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_ADDITIONS_ID)
    @ControllerException
    public String showAddition(@PathVariable("id") int id, Model model) {
        logger.debug("showAddition() : {}, {}", id, model);
        AdditionDto addition = additionService.getById(id);
        model.addAttribute(AttributeName.ADDITION, addition);
        return ViewPath.SHOW_ADDITIONS;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_ADDITION_REMOVE_INCOMPATIBLE_TARIFF,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto removeIncompatibleTariff(@RequestBody String tariff) throws BusinessLogicException {
        logger.debug("removeIncompatibleTariff() : {}", tariff);
        Integer id = additionService.removeIncompatibleTariff(tariff);
        return new ResponseDto(200, "OK", additionService.getById(id));
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_STATUS_TABLE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto getAddStatusTable(@RequestBody Integer id) throws BusinessLogicException {
        logger.debug("getAddStatusTable() : {}", id);
        return new ResponseDto(200, "OK", additionService.getAddStatusSet(id));
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_CHECK_RELATIONS,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto checkRelations(@RequestBody String relations) throws BusinessLogicException {
        logger.debug("checkRelations() : {}", relations);
        return new ResponseDto(200, "OK", additionService.checkRelations(relations));
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_SUBMIT_RELATIONS,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto submitRelations(@RequestBody String req) throws BusinessLogicException {
        logger.debug("submitRelations() : {}", req);
        AdditionDto additionDto = additionService.parseRequest(req);
        additionService.saveOrUpdate(additionDto);
        for (AdditionDto ad : additionService.getAll()) {
            additionService.removeIncompatibleOption(additionDto, ad);
        }
        return new ResponseDto(200, "OK", additionService.getById(1));
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_ADDITION_TARIFFS_REMAINED,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto getRemainedTariffs(@RequestBody List<String> packStr) throws BusinessLogicException {
        logger.debug("getRemainedTariffs() : {}", packStr);
        return new ResponseDto(200, "OK", additionService.getRemainedTariffs(packStr));
    }
}
