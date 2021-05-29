package com.properties.estated.controller;

import com.properties.estated.model.Investor;
import com.properties.estated.model.Property;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringJUnitWebConfig
@WebMvcTest(controllers = PropertyController.class)
@TestPropertySource(locations = "classpath:application-integrated-tests.properties")
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyController propertyController;

    @Test
    public void givenProperties_whenGetPropertiesByInvestorId_theReturnPropertiesArrayAndReturnStatus200() throws Exception {
        Property property1 = new Property(Double.parseDouble("1.5"), Double.parseDouble("1"), Double.parseDouble("40"));
        Property property2 = new Property(Double.parseDouble("2.5"), Double.parseDouble("2"), Double.parseDouble("130"));
        Property property3 = new Property(Double.parseDouble("1"), Double.parseDouble("1"), Double.parseDouble("35"));

        List<Property> propertyList = Arrays.asList(property1, property2, property3);
        given(propertyController.getPropertiesByInvestorId(Mockito.any())).willReturn(propertyList);

        mockMvc.perform(get("/investor/1/properties").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].roomCount", is(property1.getRoomCount())))
            .andExpect(jsonPath("$[1].roomCount", is(property2.getRoomCount())))
            .andExpect(jsonPath("$[2].roomCount", is(property3.getRoomCount())));

        verify(propertyController, times(1)).getPropertiesByInvestorId(Mockito.any());
    }

    @Test
    public void givenProperty_whenPostProperty_thenCreatePropertyAndReturnStatus200() throws Exception {
        Property property = new Property(Double.parseDouble("1.5"), Double.parseDouble("1"), Double.parseDouble("40"));
        given(propertyController.addProperty(Mockito.any(), Mockito.any())).willReturn(property);

        mockMvc.perform(post("/investor/1/properties").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(property)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.roomCount", is(property.getRoomCount())));

        verify(propertyController, times(1)).addProperty(Mockito.any(), Mockito.any());
    }

    @Test
    public void whenDeleteProperty_thenDeletePropertyAndReturnStatus200() throws Exception {
        mockMvc.perform(delete("/investor/1/properties/1"))
            .andExpect(status().isOk());

        verify(propertyController, times(1)).deleteProperty(Mockito.any(), Mockito.any());
    }

}
