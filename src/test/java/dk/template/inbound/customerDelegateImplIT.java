package dk.template.inbound;

import org.junit.jupiter.api.Test;
import org.openapitools.model.CustomerRequest;
import org.openapitools.model.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class customerDelegateImplIT {

    @Autowired
    private WebTestClient webTestClient;

    private CustomerRequest aCustomerRequest() {
        return new CustomerRequest("John Doe", 34, "123 Main Street, Copenhagen", false);
    }

    @Test
    void createCustomer_returns201() {
        CustomerRequest request = aCustomerRequest();

        CustomerResponse response = webTestClient
                .post()
                .uri("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getAge()).isEqualTo(request.getAge());
        assertThat(response.getAddress()).isEqualTo(request.getAddress());
        assertThat(response.getMarried()).isEqualTo(request.getMarried());
    }

    @Test
    void getCustomers_returns200() {
        List<CustomerResponse> response = webTestClient
                .get()
                .uri("/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<CustomerResponse>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response).isNotEmpty();
    }

    @Test
    void getCustomerById_whenExists_returns200() {
        CustomerResponse response = webTestClient
                .get()
                .uri("/customers/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    void getCustomerById_whenNotFound_returns404() {
        webTestClient
                .get()
                .uri("/customers/{id}", 999L)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateCustomer_whenExists_returns200() {
        CustomerRequest request = aCustomerRequest();

        CustomerResponse response = webTestClient
                .put()
                .uri("/customers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getAge()).isEqualTo(request.getAge());
        assertThat(response.getAddress()).isEqualTo(request.getAddress());
        assertThat(response.getMarried()).isEqualTo(request.getMarried());
    }

    @Test
    void deleteCustomer_whenExists_returns204() {
        webTestClient
                .delete()
                .uri("/customers/{id}", 1L)
                .exchange()
                .expectStatus().isNoContent();
    }
}
