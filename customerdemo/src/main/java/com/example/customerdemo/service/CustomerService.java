package com.example.customerdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customerdemo.DTO.CustomerDTO;
import com.example.customerdemo.Mapper.CustomerMapper;
import com.example.customerdemo.Repository.CustomerRepository;
import com.example.customerdemo.entity.CustomerEntity;
import com.example.customerdemo.exception.CustomerNotFoundException;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    // Get all customers
    public List<CustomerDTO> getAllCustomers() {
        return customerMapper.toDTOList(customerRepository.findAll());
    }

    // Get customer by ID
    public CustomerDTO getCustomerById(Long id) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return customerMapper.toDTO(entity);
    }

    // Create a new customer (accepts DTO)
    public CustomerDTO createCustomer(CustomerDTO newCustomerDto) {
        CustomerEntity entity = customerMapper.toEntity(newCustomerDto);  // DTO → Entity
        CustomerEntity saved = customerRepository.save(entity);
        return customerMapper.toDTO(saved);  // Entity → DTO
    }

    // Update an existing customer (accepts DTO )
    public CustomerDTO updateCustomer(Long id, CustomerDTO updatedCustomerDto) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        CustomerEntity entity = customerMapper.toEntity(updatedCustomerDto);
        entity.setCustomerID(id);  // ensure ID is correct
        CustomerEntity saved = customerRepository.save(entity);
        return customerMapper.toDTO(saved);
    }

    // Delete a customer
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerRepository.deleteById(id);
    }
}
