package mak.springframework.spring6restmvc.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mak.springframework.spring6restmvc.model.Customer;
import mak.springframework.spring6restmvc.service.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by Mehmet AKGUL on 7/29/23.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{id}";
    private final CustomerService customerService;


    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchedById(@PathVariable UUID id, @RequestBody Customer customer) {
        customerService.patchedById(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable UUID id) {
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updatedById(@PathVariable UUID id, @RequestBody Customer customer) {
        customerService.updatedById(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = CUSTOMER_PATH)
    public ResponseEntity saveCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.save(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/app/v1/customer/" + savedCustomer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = CUSTOMER_PATH)
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable UUID id) {
        log.debug("Get Customer By Id - in controller err3rete ");
        return customerService.getCustomerById(id).orElseThrow(NotFoundException::new);
    }
}
