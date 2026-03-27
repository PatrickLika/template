package dk.template.service.ports;

import dk.template.service.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerAdapter {
    Customer save(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
}
