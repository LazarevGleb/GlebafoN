package service;

import exceptions.BusinessLogicException;
import model.dto.AddStatus;
import model.dto.AdditionDto;
import model.entities.Addition;
import model.services.AdditionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dispatcher-servlet.xml")
@WebAppConfiguration
public class AdditionServiceTest {

    private AdditionService additionService;

    @Autowired
    public void setAdditionService(AdditionService additionService) {
        this.additionService = additionService;
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void create() throws Exception {
        AdditionDto addition = new AdditionDto();
        addition.setName("TestAdd");
        addition.setParameter(Addition.Parameter.SMS);
        addition.setValue(0);
        addition.setPrice(BigDecimal.valueOf(100));
        addition.setAdditionActivationCost(BigDecimal.valueOf(0));
        additionService.create(addition);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void create_1() throws Exception {
        try {
            AdditionDto addition = null;
            additionService.create(addition);
            fail("Exception expected");
        } catch (BusinessLogicException e) {
            Assert.assertEquals("Option is null", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAll() throws Exception {
        Assert.assertTrue(additionService.getAll().size() >= 6);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void deleteById() throws Exception {
        Integer id = 1;
        AdditionDto curAdd = additionService.getById(id);
        Assert.assertNotNull(curAdd);
        additionService.deleteById(1);
        List<AdditionDto> allAdditions = additionService.getAll();
        Assert.assertTrue(!allAdditions.contains(curAdd));
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getRemainTariffs() throws Exception {
        try {
            AdditionDto additionDto = null;
            additionService.getRemainTariffs(additionDto);
            fail("Exception expected");
        } catch (BusinessLogicException e) {
            Assert.assertEquals("Option is null!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getRemainTariffs_1() throws Exception {
        AdditionDto additionDto = additionService.getById(2);
        additionService.getRemainTariffs(additionDto);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAddStatusSet() throws Exception {
        additionService.getAddStatusSet(3);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAddStatusSet_1() throws Exception {
        try {
            additionService.getAddStatusSet(10);
            fail("Exception expected");
        } catch (BusinessLogicException e) {
            Assert.assertEquals("Wrong id!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void submitRelations_1() throws Exception {
        try {
            Set<AddStatus> addStatusSet = null;
            additionService.submitRelations(addStatusSet);
            fail("Exception expected");
        } catch (BusinessLogicException e) {
            Assert.assertEquals("Set is null", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getRemainedTariffs() throws Exception{
        try {
            additionService.getRemainedTariffs(null);
            fail("Exception expected");
        } catch (BusinessLogicException e) {
            Assert.assertEquals("List is null", e.getMessage());
        }
    }
}
