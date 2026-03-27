package dk.template.inbound;

import dk.template.inbound.mapper.restMapper;
import dk.template.service.ports.CustomerService;
import org.openapitools.api.CustomersApi;
import org.openapitools.model.CustomerRequest;
import org.openapitools.model.CustomerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class CustomerDelegateImpl implements CustomersApi {

    private final CustomerService customerService;
    private final restMapper mapper;

    public CustomerDelegateImpl(CustomerService customerService, restMapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<CustomerResponse> createCustomer(CustomerRequest customerRequest) {
        CustomerResponse response = mapper.toResponse(customerService.createCustomer(mapper.toDomain(customerRequest)));
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        return ResponseEntity.ok(mapper.toResponseList(customerService.getAllCustomers()));
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerById(Long customerId) {
        try {
            return ResponseEntity.ok(mapper.toResponse(customerService.getCustomerById(customerId)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<CustomerResponse> updateCustomer(Long customerId, CustomerRequest customerRequest) {
        try {
            CustomerResponse response = mapper.toResponse(customerService.updateCustomer(customerId, mapper.toDomain(customerRequest)));
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(Long customerId) {
        try {
            customerService.deleteCustomer(customerId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
