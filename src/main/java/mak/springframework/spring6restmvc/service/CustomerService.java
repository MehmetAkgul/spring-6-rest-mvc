package mak.springframework.spring6restmvc.service;


import mak.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerService {
    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO save(CustomerDTO customer);

    Optional<CustomerDTO> updatedById(UUID id, CustomerDTO customer);

    Boolean deleteById(UUID id);

    Optional<CustomerDTO> patchedById(UUID id, CustomerDTO customer);
}
