package dk.template.outbound;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private boolean married;

    public CustomerEntity() {}

    public CustomerEntity(Long id, String name, Integer age, String address, boolean married) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.married = married;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getAge() { return age; }
    public String getAddress() { return address; }
    public boolean isMarried() { return married; }
}
