package mak.springframework.spring6restmvc.service;

import lombok.RequiredArgsConstructor;
import mak.springframework.spring6restmvc.mapper.CustomerMapper;
import mak.springframework.spring6restmvc.model.CustomerDTO;
import mak.springframework.spring6restmvc.repositories.CustomerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Mehmet AKGUL on 9/12/23.
 */
@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDTO(customerRepository.findById(id).orElse(null))
        );
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        return customerMapper.customerToCustomerDTO(customerRepository.save(customerMapper.customerDTOtoCustomer(customerDTO)));
    }

    @Override
    public void updatedById(UUID id, CustomerDTO customer) {

    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void patchedById(UUID id, CustomerDTO customer) {

    }
}
