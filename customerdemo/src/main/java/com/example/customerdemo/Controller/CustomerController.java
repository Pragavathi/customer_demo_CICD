package com.example.customerdemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.customerdemo.DTO.CustomerDTO;
import com.example.customerdemo.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Get all customers from postgresdb
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Get customer by ID
    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    // Create a new customer (accept DTO)
    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO newCustomerDto) {
        return customerService.createCustomer(newCustomerDto);
    }

    // Update an existing customer (accept DTO)
    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO updatedCustomerDto) {
        return customerService.updateCustomer(id, updatedCustomerDto);
    }

    // Delete a customer
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
