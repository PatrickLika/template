package dk.template.inbound.mapper;

import dk.template.service.model.Customer;
import org.openapitools.model.CustomerRequest;
import org.openapitools.model.CustomerResponse;

import java.util.List;

public interface restMapper {
    Customer toDomain(CustomerRequest request);
    CustomerResponse toResponse(Customer customer);
    List<CustomerResponse> toResponseList(List<Customer> customers);
}
