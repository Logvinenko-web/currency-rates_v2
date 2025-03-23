package com.anton.rate;

import com.anton.rate.model.CurrencyResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("FunctionalTest")
public class BaseFunctionalTest extends BaseTest{

    private static final String JDBC_PREFIX = "jdbc";

    private static final String R2DBC_PREFIX = "r2dbc";

    protected static final String FIAT_PATH = "/fiat-currency-rates";

    protected static final String CRYPTO_PATH = "/crypto-currency-rates";

    private static final WireMockServer MOCK_SERVER = new WireMockServer(
        WireMockConfiguration.wireMockConfig().port(8099));

    static WireMockServer wireMockServer;

    @Autowired
    WebTestClient webTestClient;

    @AfterAll
    public static void stopWireMock () {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    static PostgreSQLContainer<?> PSQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName("db_rate")
        .withUsername("user")
        .withPassword("user")
        .withExposedPorts(5432);

    @DynamicPropertySource
    protected static void registerMockServer (DynamicPropertyRegistry registry) {
        MOCK_SERVER.start();
        PSQL_CONTAINER.start();
        registry.add("spring.liquibase.url", PSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.r2dbc.url",
            () -> PSQL_CONTAINER.getJdbcUrl().replace(JDBC_PREFIX, R2DBC_PREFIX));
        registry.add("spring.r2dbc.username", PSQL_CONTAINER::getUsername);
        registry.add("spring.r2dbc.password", PSQL_CONTAINER::getPassword);
        registry.add("spring.config.mock-url", () -> "http://localhost:" + MOCK_SERVER.port());
    }

    protected StubMapping mockGetSuccessResponse (String path, String responseBody) {
       return MOCK_SERVER.stubFor(WireMock.get(WireMock.urlEqualTo(path))
            .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody(responseBody)));
    }

    protected StubMapping mockGetInternalErrorStatus (String path) {
        return MOCK_SERVER.stubFor(WireMock.get(WireMock.urlEqualTo(path))
            .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(500)));
    }

    protected CurrencyResponse getCurrency() {
       return  webTestClient.get().uri("/currency-rates")
           .exchange()
           .expectStatus().isOk()
           .expectBody(CurrencyResponse.class)
           .returnResult()
           .getResponseBody();
    }


}
