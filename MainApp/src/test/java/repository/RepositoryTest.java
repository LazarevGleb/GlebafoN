package repository;

import model.entities.Addition;
import model.entities.Client;
import model.entities.Contract;
import model.entities.Tariff;
import model.repositories.*;
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

import static model.entities.Addition.Parameter.MINUTES;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dispatcher-servlet.xml")
@WebAppConfiguration
public class RepositoryTest {
    private AdditionRepository additionRepository;
    private ClientRepository clientRepository;
    private ContractRepository contractRepository;
    private TariffRepository tariffRepository;

    @Autowired
    public void setAdditionRepository(AdditionRepository additionRepository) {
        this.additionRepository = additionRepository;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Autowired
    public void setTariffRepository(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public RepositoryTest() {
    }

    @Test
    @Transactional(readOnly = true)
    public void getAll() throws Exception {
        List<Addition> allAdditions = additionRepository.getAll();
        Assert.assertTrue(allAdditions.size() >= 6);
        Addition addition = allAdditions.get(3);
        Assert.assertEquals(4, addition.getId());
        Assert.assertEquals("400 extra minutes", addition.getName());
        Assert.assertEquals(MINUTES, addition.getParameter());
        Assert.assertEquals(400, addition.getValue());
        Assert.assertEquals(600.00, addition.getPrice().doubleValue(), 1e-3);
        Assert.assertEquals(BigDecimal.valueOf(0, 2), addition.getAdditionActivationCost());
        Assert.assertEquals(true, addition.getValid());

    }

    @Test
    @Transactional(readOnly = true)
    public void getAll_1() throws Exception{
        List<Tariff> allTariffs = tariffRepository.getAll();
        Assert.assertTrue(allTariffs.size() >= 6);
        Tariff tariff = allTariffs.get(0);
        Assert.assertEquals(1, tariff.getId());
        Assert.assertEquals("Schooler", tariff.getName());
        Assert.assertEquals("100 sms, 200 minutes, 3 Gb", tariff.getDescription());
        Assert.assertEquals(100, tariff.getSms());
        Assert.assertEquals(200, tariff.getMinutes());
        Assert.assertEquals(3, tariff.getInternet());
        Assert.assertEquals(100.00, tariff.getPrice().doubleValue(), 1e-3);
        Assert.assertEquals(true, tariff.getValid());

    }

    @Test
    @Transactional(readOnly = true)
    public void getAll_2() throws Exception{
        List<Client> allClients = clientRepository.getAll();
        Assert.assertTrue(allClients.size() >= 5);
        Client client = allClients.get(2);
        Assert.assertEquals(3, client.getId());
        Assert.assertEquals("Gleb", client.getName());
        Assert.assertEquals("Lazarev", client.getSurname());
        Assert.assertEquals("06.12.1994", client.getBirthday());
        Assert.assertEquals("Saint_petersburg, Lunacharskiy avenue, house 110", client.getAddress());
        Assert.assertEquals("lazarevgb@gmail.com", client.getEmail());
        Assert.assertEquals("0514", client.getPassportSeries());
        Assert.assertEquals("225948", client.getPassportNumber());
        Assert.assertEquals("UFMS Russia Primorskiy kray", client.getPassportIssuedBy());
        Assert.assertEquals("29.01.2015", client.getPassportIssueDate());
        Assert.assertEquals("250-060", client.getPassportDivisionCode());
        Assert.assertEquals(true, client.getValid());
    }

    @Test
    @Transactional(readOnly = true)
    public void getAll_3() throws Exception{
        List<Contract> allContracts = contractRepository.getAll();
        Assert.assertTrue(allContracts.size() >= 5);
        Contract contract = allContracts.get(1);
        Assert.assertEquals(2, contract.getId());
        Assert.assertEquals("+7-925-252-6866", contract.getNumber());
        Assert.assertEquals(3, contract.getTariff().getId());
        Assert.assertEquals(4, contract.getClient().getId());
        Assert.assertEquals("UNBLOCKED", contract.getBlock().toString());
        Assert.assertEquals("polina12345", contract.getPassword());
        Assert.assertEquals(0.00, contract.getBalance().doubleValue(), 1e-3);
        Assert.assertEquals(true, contract.getValid());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void update() throws Exception{
        Contract contract = contractRepository.getByProperty("id", 1);
        Assert.assertEquals("+7-925-732-6169", contract.getNumber());
        contract.setBalance(BigDecimal.valueOf(100));
        contractRepository.update(contract);
        Contract contract1 = contractRepository.getByProperty("id", 1);
        Assert.assertEquals(100.00, contract1.getBalance().doubleValue(), 1e-3);
        Assert.assertEquals(contract1.getNumber(), contract.getNumber());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void create() throws Exception{
        Tariff tariff = new Tariff();
        tariff.setId(10);
        tariff.setName("AA");
        tariff.setSms(10);
        tariff.setMinutes(20);
        tariff.setInternet(9);
        tariff.setPrice(BigDecimal.valueOf(135.00));
        tariff.setDescription("10 sms, 20 minutes, 9 Gb");
        tariff.setValid(true);
        tariffRepository.create(tariff);
        Tariff tariff1 = tariffRepository.getByProperty("sms", 10);
        Assert.assertEquals("AA", tariff1.getName());
    }
}
