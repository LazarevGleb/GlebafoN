package controller;

import exceptions.BusinessLogicException;
import exceptions.ControllerException;
import model.dto.AdditionDto;
import model.dto.ResponseDto;
import model.dto.TariffDto;
import model.dto.TariffFormDto;
import model.services.TariffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import utils.*;

import java.util.List;
import java.util.Set;

@Controller
public class TariffController {
    private TariffService tariffService;
    private Converter converter;
    private JmsProducer producer;

    @Autowired
    public TariffController(TariffService tariffService, Converter converter,
                            JmsProducer producer) {
        this.tariffService = tariffService;
        this.converter = converter;
        this.producer = producer;
    }

    private final Logger logger = LoggerFactory.getLogger(TariffController.class);

    @GetMapping(value = ControllerPath.EMPLOYEE_TARIFFS)
    @ControllerException
    public String showAllTariffs(Model model) throws BusinessLogicException {
        logger.debug("showAllTariffs()");
        model.addAttribute(AttributeName.TARIFF_LIST, tariffService.getAll());
        producer.send();
        return ViewPath.TARIFF_LIST;
    }

    @GetMapping(value = ControllerPath.CLIENT_TARIFFS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto showAllTariffs() throws BusinessLogicException {
        logger.debug("showAllTariffs()");
        return new ResponseDto(200, "OK", tariffService.getAll());
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_TARIFFS)
    @ControllerException
    public String saveOrUpdateTariff(@ModelAttribute("tariff") TariffFormDto tariffForm) throws BusinessLogicException {
        logger.debug("saveOrUpdateTariff() : {}", tariffForm);
        tariffService.saveOrUpdate(tariffForm);
        return ControllerPath.REDIRECT_EMPLOYEE_TARIFFS;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_TARIFFS_ADD)
    @ControllerException
    public String showAddTariffForm(Model model) throws BusinessLogicException {
        logger.debug("showAddTariffForm()");
        List<AdditionDto> additions = tariffService.getAllAdditions();
        model.addAttribute(AttributeName.TARIFF, new TariffFormDto());
        model.addAttribute(AttributeName.REMAIN_ADDITION_LIST, additions);
        return ViewPath.TARIFF_FORM;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_TARIFFS_UPDATE)
    @ControllerException
    public String showUpdateTariffForm(@PathVariable("id") int id, Model model) throws BusinessLogicException {
        logger.debug("showUpdateTariffForm() : {}", id);
        TariffDto tariff = tariffService.getById(id);
        model.addAttribute(AttributeName.ADDITION_LIST, tariff.getPackages());
        TariffFormDto tariffFormDto = converter.convertToTariffFormDto(tariff);
        model.addAttribute(AttributeName.TARIFF, tariffFormDto);
        Set<AdditionDto> remainAdditions = tariffService.getRemainAdditions(tariff);
        model.addAttribute(AttributeName.REMAIN_ADDITION_LIST, remainAdditions);
        return ViewPath.TARIFF_FORM;
    }

    @PostMapping(value = ControllerPath.EMPLOYEE_TARIFFS_DELETE)
    @ControllerException
    public String deleteTariff(@PathVariable("id") int id) throws BusinessLogicException {
        logger.debug("deleteTariff() : {}", id);
        tariffService.deleteById(id);
        return ControllerPath.REDIRECT_EMPLOYEE_TARIFFS;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_TARIFFS_ID)
    public String showTariff(@PathVariable("id") int id, Model model) {
        logger.debug("showTariff() id: {}", id);
        TariffDto tariff = tariffService.getById(id);
        model.addAttribute(AttributeName.TARIFF, tariff);
        return ViewPath.SHOW_TARIFFS;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_TARIFF_REMOVE_INCOMPATIBLE_OPTION)
    @ControllerException
    public String removeIncompatibleOption(@PathVariable("tId") Integer tId,
                                           @PathVariable("optId") Integer optId,
                                           Model model) throws BusinessLogicException {
        logger.debug("removeIncompatibleOption() : {}, {}, {}", tId, optId, model);
        tariffService.removeIncompatibleOption(tId, optId);
        TariffDto tariff = tariffService.getById(tId);
        model.addAttribute(AttributeName.ADDITION_LIST, tariff.getPackages());
        TariffFormDto tariffFormDto = converter.convertToTariffFormDto(tariff);
        model.addAttribute(AttributeName.TARIFF, tariffFormDto);
        Set<AdditionDto> remainAdditions = tariffService.getRemainAdditions(tariff);
        model.addAttribute(AttributeName.REMAIN_ADDITION_LIST, remainAdditions);
        return ViewPath.TARIFF_FORM;
    }

    @GetMapping(value = ControllerPath.EMPLOYEE_GET_TARIFFS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ControllerException
    public ResponseDto getTariffs() throws BusinessLogicException {
        logger.debug("getTariffs()");
        return new ResponseDto(200, "OK", tariffService.getAll());
    }
}
