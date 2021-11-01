package firstcatering.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import firstcatering.api.model.EmployeeCard;
import firstcatering.api.service.interfaces.EmployeeCardService;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeCardController.class)
@ActiveProfiles("test")
public class EmployeeCardControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext webAppContext;
    @MockBean
    private DataSource dataSource;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper mapper; // Object mapper for object -> JSON conversion

    @MockBean
    @Autowired
    private EmployeeCardService cardService;

    EmployeeCard card;
    Double amount = 25.00;

    EmployeeCard mockEmployeeCard = new EmployeeCard("111a",
            "John Smith",
            "john@enterprise.com",
            "075678765678",
            "1234",
            "a87676jhgG8Hp111");

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    /*    EmployeeCard employeeCard = new EmployeeCard();
        employeeCard.setId(201);
        //employeeCard.setActive(true);
        employeeCard.setName("Mary Smith");
        employeeCard.setEmployeeID("abc1");
        employeeCard.setCardID("a8767hg34DFq43AB");
        employeeCard.setEmail("mary@enterprise.com");
        employeeCard.setMobile("07654876590");
        employeeCard.setBalance(22.00);
        employeeCard.setPin("1243");
        cardService.registerEmployeeCard(employeeCard);*/
    }

    @Test
    public void whenViewingAllEmployeeCardsThenReturnsAllObjectsWith200() throws Exception {
        mockMvc.perform(get("/cards")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    // @Disabled
    public void givenValidInputWhenViewingCardThenReturnsObjectWith200() throws Exception {
        mockMvc.perform(get("/cards/{card-id}", mockEmployeeCard.getCardID())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    //TODO: fix this test
    @Test
    // @Disabled
    public void givenValidInputWhenRegisteringNewEmployeeCardThenReturnsObjectWith201() throws Exception {
        Mockito
                .when(cardService.registerEmployeeCard(Mockito.any(EmployeeCard.class)))
                .thenReturn(mockEmployeeCard);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/cards/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(mockEmployeeCard));

        mockMvc.perform(builder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.card-id", is("a87676jhgG8Hp111")));
    }

    @Test
    public void givenValidTopUpAmountWhenToppingUpThenReturnsObjectWithOk() throws Exception {
        Mockito
            .when(cardService.topUpEmployeeCard(cardService.getEmployeeCardByCardID("a8767hg34DFq43AB"), amount))
            .thenReturn(mockEmployeeCard);

        mockMvc.perform(put("/cards/{card-id}/top-up", mockEmployeeCard.getCardID())
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}




