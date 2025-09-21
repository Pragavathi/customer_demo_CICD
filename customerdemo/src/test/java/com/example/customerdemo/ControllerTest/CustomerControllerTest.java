package com.example.customerdemo.ControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.customerdemo.Controller.CustomerController;
import com.example.customerdemo.DTO.CustomerDTO;
import com.example.customerdemo.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testCreateCustomer() throws Exception {
        // Request DTO
        CustomerDTO requestDto = new CustomerDTO();
        requestDto.setName("Mike");
        requestDto.setEmail("mike@example.com");

        // Mocked service response DTO
        CustomerDTO responseDto = new CustomerDTO();
        responseDto.setCustomerID(3L);
        responseDto.setName("Mike");
        responseDto.setEmail("mike@example.com");

        // Mock service
        when(customerService.createCustomer(any(CustomerDTO.class)))
                .thenReturn(responseDto);

        // Perform POST
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerID").value(3))
                .andExpect(jsonPath("$.name").value("Mike"))
                .andExpect(jsonPath("$.email").value("mike@example.com"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        // Request DTO
        CustomerDTO requestDto = new CustomerDTO();
        requestDto.setName("Mike Updated");
        requestDto.setEmail("mike@example.com");

        // Mocked service response DTO
        CustomerDTO responseDto = new CustomerDTO();
        responseDto.setCustomerID(3L);
        responseDto.setName("Mike Updated");
        responseDto.setEmail("mike@example.com");

        // Mock service update
        when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class)))
                .thenReturn(responseDto);

        // Perform PUT
        mockMvc.perform(put("/api/customers/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerID").value(3))
                .andExpect(jsonPath("$.name").value("Mike Updated"))
                .andExpect(jsonPath("$.email").value("mike@example.com"));
    }

    @Test
    void testGetCustomerById() throws Exception {
        CustomerDTO responseDto = new CustomerDTO();
        responseDto.setCustomerID(1L);
        responseDto.setName("John");
        responseDto.setEmail("john@example.com");

        when(customerService.getCustomerById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerID").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setCustomerID(1L);
        customer1.setName("John");
        customer1.setEmail("john@example.com");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setCustomerID(2L);
        customer2.setName("Jane");
        customer2.setEmail("jane@example.com");

        when(customerService.getAllCustomers()).thenReturn(java.util.List.of(customer1, customer2));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Jane"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isOk());
    }
}
