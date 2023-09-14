package mak.springframework.spring6restmvc.controller;

import mak.springframework.spring6restmvc.entities.Customer;
import mak.springframework.spring6restmvc.mapper.CustomerMapper;
import mak.springframework.spring6restmvc.model.CustomerDTO;
import mak.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CustomerControllerIT {


    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer() {
        // once bir dto verisi olustur controllerdaki saveCustomer endpointine
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New Customer ")
                .build();
        // 2. olarak bu olusturdugun dto yu controllerdaki saveCustomer endpointine gonderiyouz
        ResponseEntity responseEntity = customerController.saveCustomer(customerDTO);

        // 3. olarak response olarak donen responseEntity icindeki statusCode 201 oldugunu ispat edelim
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        // 4. olarak responseEntity icindeki Location'a var oldugunu ispatlayalim cunku biz zaten bir location eklemistik buna
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        // 5. olarak locationdaki beer id'yi alalim
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        // simdi aldigimiz id ile iliskili gercekten bir beer oldugunu ispat etmek icin customeri cekelim
        Customer customer = customerRepository.findById(savedUUID).get();
        //6. artik customerin null olmadigini kontrol edelim
        assertThat(customer).isNotNull();

    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());

        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testListCustomer() {
        List<CustomerDTO> dtos = customerController.listCustomers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listCustomers();
        assertThat(dtos.size()).isEqualTo(0);
    }
}