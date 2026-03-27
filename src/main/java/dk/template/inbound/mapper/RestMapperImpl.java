package dk.template.inbound.mapper;

import dk.template.service.model.Customer;
import org.openapitools.model.CustomerRequest;
import org.openapitools.model.CustomerResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestMapperImpl implements restMapper {

    @Override
    public Customer toDomain(CustomerRequest request) {
        return new Customer(null, request.getName(), request.getAge(), request.getAddress(), request.getMarried());
    }

    @Override
    public CustomerResponse toResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.id());
        response.setName(customer.name());
        response.setAge(customer.age());
        response.setAddress(customer.address());
        response.setMarried(customer.married());
        return response;
    }

    @Override
    public List<CustomerResponse> toResponseList(List<Customer> customers) {
        return customers.stream().map(this::toResponse).toList();
    }
}
