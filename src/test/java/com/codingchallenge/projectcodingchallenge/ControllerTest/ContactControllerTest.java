package com.codingchallenge.projectcodingchallenge.ControllerTest;

import com.codingchallenge.projectcodingchallenge.Service.ContactService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ContactService contactService;

    @Test
    public void testGetContactById_ContactFound() throws Exception {
        String contactId = "65d9be08de9b1afa4ced2923";

        when(contactService.getContactById(contactId));

        mockMvc.perform(get("/api/contacts/{id}", contactId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("65d9be08de9b1afa4ced2923")))
                .andExpect(jsonPath("$.firstName", is("UpdatedFirstName")))
                .andExpect(jsonPath("$.lastName", is("UpdatedLastName")))
                .andExpect(jsonPath("$.houseNumber", is("789")))
                .andExpect(jsonPath("$.zipCode", is("54321")))
                .andExpect(jsonPath("$.city", is("UpdatedCity")))
                .andExpect(jsonPath("$.phoneNumber", is("9876543210")));
    }

}
