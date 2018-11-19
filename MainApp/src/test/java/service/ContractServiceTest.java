package service;

import model.dto.Basket;
import model.dto.ContractDto;
import model.dto.ContractFormDto;
import model.entities.Contract;
import model.services.AdditionService;
import model.services.ClientService;
import model.services.ContractService;
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
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dispatcher-servlet.xml")
@WebAppConfiguration
public class ContractServiceTest {
    private ContractService contractService;
    private ClientService clientService;
    private TariffService tariffService;
    private AdditionService additionService;

    @Autowired
    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setTariffService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @Autowired
    public void setAdditionService(AdditionService additionService) {
        this.additionService = additionService;
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void create() throws Exception {
        ContractDto contractDto = new ContractDto();
        contractDto.setNumber("111");
        contractDto.setClient(clientService.getById(1));
        contractDto.setTariff(tariffService.getById(2));
        contractDto.setValid(true);
        contractDto.setBalance(BigDecimal.valueOf(100.00));
        contractDto.setPassword("aaaaaaaaa");
        contractDto.setBlock(Contract.BLOCK.UNBLOCKED);
        contractService.create(contractDto);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void create_1() throws Exception {
        try {
            ContractDto contractDto = null;
            contractService.create(contractDto);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("Parameter is null", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getByNumber() throws Exception {
        try {
            contractService.getByNumber("1111");
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("No contract with this number", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getByNumber_1() throws Exception {
        Assert.assertEquals(Integer.valueOf(5), contractService.getByNumber("+7-925-702-1956").getId());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getById() throws Exception {
        try {
            contractService.getById(10);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("No contract with this id!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getById_1() throws Exception {
        Assert.assertEquals("+7-925-702-1956", contractService.getById(5).getNumber());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void updateContract() throws Exception {
        try {
            contractService.updateContract(null);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("Wrong argument", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void updateContract_1() throws Exception {
        ContractDto contractDto = contractService.getById(2);
        contractDto.setTariff(tariffService.getById(4));
        contractDto.setAdditions(Arrays.asList(additionService.getById(1), additionService.getById(2)));
        contractService.updateContract(contractDto);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void update() throws Exception {
        try {
            contractService.updateContract(null);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("Wrong argument", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void update_1() throws Exception {
        ContractDto contractDto = contractService.getById(2);
        contractDto.setTariff(tariffService.getById(6));
        contractService.updateContract(contractDto);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void inverseBlockStatus() throws Exception {
        try {
            ContractDto contractDto = null;
            contractService.inverseBlockStatus(contractDto);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("Contract is null", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void inverseBlockStatus_1() throws Exception {
        ContractDto contractDto = contractService.getById(3);
        contractService.inverseBlockStatus(contractDto);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void inverseClientBlockStatus() throws Exception {
        try {
            ContractDto contractDto = null;
            contractService.inverseClientBlockStatus(contractDto);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("ContractDto is null", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void inverseClientBlockStatus_1() throws Exception {
        ContractDto contractDto = contractService.getById(3);
        contractService.inverseClientBlockStatus(contractDto);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAvailableOptionsIds() throws Exception {
        try {
            ContractDto contractDto = null;
            contractService.getAvailableOptionsIds(contractDto);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("ContractDto is null!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAvailableOptionsIds_1() throws Exception {
        ContractDto contractDto = contractService.getById(3);
        contractService.getAvailableOptionsIds(contractDto);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAvailableOptions() throws Exception {
        try {
            ContractDto contractDto = null;
            contractService.getAvailableOptions(contractDto);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("Argument is null", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAvailableOptions_1() throws Exception {
        ContractDto contractDto = contractService.getById(3);
        contractService.getAvailableOptions(contractDto);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAdditionById() throws Exception {
        try {
            contractService.getAdditionById(10);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("No additions with this id!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAdditionById_1() throws Exception {
        Assert.assertEquals("100 extra sms", contractService.getAdditionById(1).getName());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void submitBasket() throws Exception {
        try {
            ContractDto contractDto = null;
            contractService.submitBasket(contractDto, new Basket());
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("ContractDto null", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void submitBasket_1() throws Exception {
        try {
            ContractDto contractDto = contractService.getById(3);
            contractService.submitBasket(contractDto, null);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("Basket is null", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void submitBasket_2() throws Exception {
        ContractDto contractDto = contractService.getById(3);
        Basket basket = new Basket();
        basket.setChosenTariff(tariffService.getById(2));
        basket.setChosenAdditions(new HashSet<>());
        basket.setDeletedAdditions(new HashSet<>());
        basket.setTotalPrice(BigDecimal.valueOf(1000));
        basket.setTotalActivationCost(BigDecimal.valueOf(200));
        basket.setTotalSum(BigDecimal.valueOf(1200));
        contractService.submitBasket(contractDto, basket);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getClientById() throws Exception {
        try {
            contractService.getClientById(10);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("No clients with this id!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getClientById_1() throws Exception {
        Assert.assertEquals("Gleb", contractService.getClientById(3).getName());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getAllAdditions() throws Exception {
        Assert.assertTrue(contractService.getAllAdditions().size() >= 6);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void deleteById() throws Exception {
        try {
            contractService.deleteById(10);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("No contracts with this id!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void deleteById_1() throws Exception {
        Integer id = 1;
        ContractDto curCon = contractService.getById(id);
        Assert.assertNotNull(curCon);
        contractService.deleteById(1);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getByPassword() throws Exception {
        try {
            contractService.getByPassword("ddd");
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("No contracts with this password!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void getByPassword_1() throws Exception {
        Assert.assertEquals(Integer.valueOf(5),contractService.getByPassword("mama12345").getId());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void addClientContract() throws Exception {
        try {
            contractService.addClientContract(10, new ContractFormDto());
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("No client with this id!", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void addClientContract_1() throws Exception {
        try {
            contractService.addClientContract(3, null);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("Second parameter is null", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void addClientContract_2() throws Exception {
        ContractFormDto contractFormDto = new ContractFormDto();
        contractFormDto.setNumber("+7-925-666-8887");
        contractFormDto.setTariffId(2);
        contractFormDto.setClientId(2);
        contractFormDto.setBalance(BigDecimal.valueOf(100));
        contractService.addClientContract(2, contractFormDto);
    }
}
