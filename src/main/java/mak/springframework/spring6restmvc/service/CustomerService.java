package mak.springframework.spring6restmvc.service;


import mak.springframework.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerService {
    List<Customer> listCustomers();

    Optional<Customer> getCustomerById(UUID id);

    Customer save(Customer customer);

    void updatedById(UUID id, Customer customer);

    void deleteById(UUID id);

    void patchedById(UUID id, Customer customer);
}
