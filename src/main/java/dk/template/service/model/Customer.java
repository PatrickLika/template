package dk.template.service.model;

public record Customer(
        Long id,
        String name,
        Integer age,
        String address,
        boolean married
) {
}
