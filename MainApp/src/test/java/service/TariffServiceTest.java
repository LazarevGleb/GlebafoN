package service;

import model.dto.TariffDto;
import model.dto.TariffFormDto;
import model.services.AdditionService;
import model.services.TariffService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dispatcher-servlet.xml")
@WebAppConfiguration
public class TariffServiceTest {
    private TariffService tariffService;
    private AdditionService additionService;

    @Autowired
    public void setAdditionService(AdditionService additionService) {
        this.additionService = additionService;
    }

    @Autowired
    public void setTariffService(TariffService tariffService) {
        this.tariffService = tariffService;
    }


    @Test
    @Transactional(rollbackFor = Exception.class)
    public void create() throws Exception {
        try {
            tariffService.create(null);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("Tariff is null!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void create_1() throws Exception {
        TariffDto tariffDto = new TariffDto();
        tariffDto.setName("aa");
        tariffDto.setValid(true);
        tariffDto.setDescription("aaa aaa");
        tariffDto.setSms(1);
        tariffDto.setMinutes(1);
        tariffDto.setInternet(1);
        tariffDto.setPrice(BigDecimal.valueOf(100));
        tariffService.create(tariffDto);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAll() throws Exception {
        Assert.assertTrue(tariffService.getAll().size() >= 6);
    }


    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getById() throws Exception {
        Assert.assertEquals("USSR", tariffService.getById(5).getName());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate() throws Exception {
        try {
            tariffService.saveOrUpdate(null);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("TariffForm is null!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate_1() throws Exception {
        TariffFormDto tfd = new TariffFormDto();
        tfd.setId(0);
        tfd.setName("aa");
        tfd.setSms(11);
        tfd.setMinutes(1);
        tfd.setInternet(10);
        tfd.setDescription("aaa");
        tfd.setPrice(BigDecimal.valueOf(10));
        tfd.setValid(true);
        tfd.setAddIds(new ArrayList<>());
        tariffService.saveOrUpdate(tfd);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void deleteById() throws Exception {
        try {
            tariffService.deleteById(10);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("No tariffs with this id!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void deleteById_1() throws Exception {
        Integer id = 1;
        TariffDto curTariff = tariffService.getById(id);
        Assert.assertNotNull(curTariff);
        tariffService.deleteById(1);
    }


    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAllAdditions() throws Exception {
        Assert.assertTrue(tariffService.getAllAdditions().size() >= 6);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getTariffsWithOptions() throws Exception {
        Assert.assertTrue(tariffService.getTariffsWithOptions().size() >= 6);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getRemainAdditions() throws Exception {
        try {
            tariffService.getRemainAdditions(null);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("Tariff is null!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getRemainAdditions_1() throws Exception {
        Integer id = 1;
        TariffDto curTariff = tariffService.getById(id);
        Assert.assertNotNull(curTariff);
        Assert.assertTrue(tariffService.getRemainAdditions(curTariff).size() >= 6);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void removeIncompatibleOption() throws Exception {
        try {
            tariffService.removeIncompatibleOption(10, 2);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("No tariffs with this id!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void removeIncompatibleOption_1() throws Exception {
        tariffService.removeIncompatibleOption(1, 4);
    }
}
