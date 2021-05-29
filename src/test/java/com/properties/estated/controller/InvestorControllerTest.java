package com.properties.estated.controller;

import com.properties.estated.model.Investor;
import com.properties.estated.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
@WebMvcTest(controllers = InvestorController.class)
@TestPropertySource(locations = "classpath:application-integrated-tests.properties")
public class InvestorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvestorController investorController;

    @Test
    public void givenInvestors_whenGetInvestors_theReturnInvestorsArrayAndReturnStatus200() throws Exception {
        Investor gabriel = new Investor("Gabriel");
        Investor evan = new Investor("Evan");
        Investor estated = new Investor("Estated");

        List<Investor> investorList = Arrays.asList(gabriel, evan, estated);
        given(investorController.getInvestors()).willReturn(investorList);

        mockMvc.perform(get("/investor").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].name", is(gabriel.getName())))
            .andExpect(jsonPath("$[1].name", is(evan.getName())))
            .andExpect(jsonPath("$[2].name", is(estated.getName())));

        verify(investorController, times(1)).getInvestors();
    }

    @Test
    public void givenInvestor_whenGetInvestor_thenReturnInvestorAndReturnStatus200() throws Exception {
        Investor gabriel = new Investor("Gabriel");
        given(investorController.getInvestor(Mockito.any())).willReturn(java.util.Optional.of(gabriel));

        mockMvc.perform(get("/investor/1").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(gabriel)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(gabriel.getName())));

        verify(investorController, times(1)).getInvestor(Mockito.any());
    }

    @Test
    public void givenInvestor_whenPostInvestor_thenCreateInvestorAndReturnStatus200() throws Exception {
        Investor gabriel = new Investor("Gabriel");
        given(investorController.createInvestor(Mockito.any())).willReturn(gabriel);

        mockMvc.perform(post("/investor").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(gabriel)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(gabriel.getName())));

        verify(investorController, times(1)).createInvestor(Mockito.any());
    }

    @Test
    public void whenDeleteInvestor_thenDeleteInvestorAndReturnStatus200() throws Exception {
        mockMvc.perform(delete("/investor/1"))
            .andExpect(status().isOk());

        verify(investorController, times(1)).deleteInvestor(Mockito.any());
    }

}
