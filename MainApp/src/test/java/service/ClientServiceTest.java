package service;

import exceptions.BusinessLogicException;
import model.dto.ClientDto;
import model.services.ClientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dispatcher-servlet.xml")
@WebAppConfiguration
public class ClientServiceTest {
    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Test
    @Transactional(readOnly = true)
    public void getById() throws Exception {
        try {
            clientService.getById(10);
            fail("Exception expected");
        } catch (BusinessLogicException e) {
            Assert.assertEquals("No client with this id!", e.getMessage());
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void create() throws Exception {
        try {
            ClientDto clientDto = null;
            clientService.create(clientDto);
            fail("Exception expected");
        } catch (Exception e) {
            Assert.assertEquals("Client is null", e.getMessage());
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void deleteById() throws Exception {
        Integer id = 1;
        ClientDto curCl = clientService.getById(id);
        Assert.assertNotNull(curCl);
        clientService.deleteById(1);
    }

    @Test
    @Transactional(readOnly = true)
    public void update() throws Exception{
        try {
            clientService.update(null);
            fail("Exception expected");
        } catch (BusinessLogicException e){
            Assert.assertEquals("Parameter is null!", e.getMessage());
        }
    }
}
