package com.example.customerdemo.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.example.customerdemo.DTO.CustomerDTO;
import com.example.customerdemo.Mapper.CustomerMapper;
import com.example.customerdemo.Repository.CustomerRepository;
import com.example.customerdemo.entity.CustomerEntity;
import com.example.customerdemo.service.CustomerService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testGetCustomerById_found() {
        CustomerEntity entity = new CustomerEntity();
        entity.setCustomerID(1L);
        entity.setName("John");
        entity.setEmail("john@example.com");

        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerID(1L);
        dto.setName("John");
        dto.setEmail("john@example.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(customerMapper.toDTO(entity)).thenReturn(dto);

        CustomerDTO result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals(1L, result.getCustomerID());
    }

    @Test
    void testGetCustomerById_notFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.getCustomerById(1L));
    }

    @Test  
    	void testGetAllCustomers() {
    	    CustomerEntity entity1 = new CustomerEntity();
    	    entity1.setCustomerID(1L);
    	    entity1.setName("John");
    	    entity1.setEmail("john@example.com");

    	    CustomerEntity entity2 = new CustomerEntity();
    	    entity2.setCustomerID(2L);
    	    entity2.setName("Jane");
    	    entity2.setEmail("jane@example.com");

    	    // Stub repository
    	    when(customerRepository.findAll()).thenReturn(List.of(entity1, entity2));

    	    // Stub mapper
    	    CustomerDTO dto1 = new CustomerDTO();
    	    dto1.setCustomerID(1L);
    	    dto1.setName("John");
    	    dto1.setEmail("john@example.com");

    	    CustomerDTO dto2 = new CustomerDTO();
    	    dto2.setCustomerID(2L);
    	    dto2.setName("Jane");
    	    dto2.setEmail("jane@example.com");

    	    when(customerMapper.toDTOList(List.of(entity1, entity2)))
    	            .thenReturn(List.of(dto1, dto2));

    	    // Call service
    	    List<CustomerDTO> customers = customerService.getAllCustomers();

    	    assertNotNull(customers);
    	    assertEquals(2, customers.size());
    	    assertEquals("John", customers.get(0).getName());
    	    assertEquals("Jane", customers.get(1).getName());
    	}


    @Test
    void testCreateCustomer() {
        CustomerDTO dto = new CustomerDTO();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");

        CustomerEntity entity = new CustomerEntity();
        entity.setName("Alice");
        entity.setEmail("alice@example.com");

        when(customerMapper.toEntity(dto)).thenReturn(entity);
        when(customerRepository.save(entity)).thenReturn(entity);
        when(customerMapper.toDTO(entity)).thenReturn(dto);

        CustomerDTO result = customerService.createCustomer(dto);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals("alice@example.com", result.getEmail());
    }
}
