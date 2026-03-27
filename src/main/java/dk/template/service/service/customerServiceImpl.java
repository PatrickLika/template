package dk.template.service.service;

import dk.template.service.model.Customer;
import dk.template.service.ports.CustomerAdapter;
import dk.template.service.ports.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class customerServiceImpl implements CustomerService {

    private final CustomerAdapter customerAdapter;

    public customerServiceImpl(CustomerAdapter customerAdapter) {
        this.customerAdapter = customerAdapter;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerAdapter.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerAdapter.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerAdapter.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        if (!customerAdapter.existsById(id)) {
            throw new NoSuchElementException();
        }
        return customerAdapter.save(new Customer(id, customer.name(), customer.age(), customer.address(), customer.married()));
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerAdapter.existsById(id)) {
            throw new NoSuchElementException();
        }
        customerAdapter.deleteById(id);
    }
}
