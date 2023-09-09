package mak.springframework.spring6restmvc.repositories;

import mak.springframework.spring6restmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by Mehmet AKGUL on 9/9/23.
 */
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
