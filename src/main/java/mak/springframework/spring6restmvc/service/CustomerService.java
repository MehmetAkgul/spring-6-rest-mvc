package mak.springframework.spring6restmvc.service;


import mak.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerService {
    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO save(CustomerDTO customer);

    void updatedById(UUID id, CustomerDTO customer);

    void deleteById(UUID id);

    void patchedById(UUID id, CustomerDTO customer);
}
