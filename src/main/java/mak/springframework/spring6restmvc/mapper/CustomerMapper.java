package mak.springframework.spring6restmvc.mapper;

import mak.springframework.spring6restmvc.entities.Customer;
import mak.springframework.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

/**
 * Created by Mehmet AKGUL on 9/12/23.
 */
@Mapper
public interface CustomerMapper {

    Customer customerDTOtoCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
