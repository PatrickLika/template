package dk.template.outbound;

import dk.template.service.model.Customer;
import dk.template.service.ports.CustomerAdapter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository implements CustomerAdapter {

    private final CustomerJpaRepository jpaRepository;

    public CustomerRepository(CustomerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = new CustomerEntity(customer.id(), customer.name(), customer.age(), customer.address(), customer.married());
        CustomerEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Customer> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    private Customer toDomain(CustomerEntity entity) {
        return new Customer(entity.getId(), entity.getName(), entity.getAge(), entity.getAddress(), entity.isMarried());
    }
}
