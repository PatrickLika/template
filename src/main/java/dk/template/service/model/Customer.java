package dk.template.service.model;

public record Customer(
        String id,
        String name,
        Integer age,
        String address,
        boolean married
) {
}
