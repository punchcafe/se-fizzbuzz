package dev.punchcafe.sefizzbuzz.cli.smoke;

import dev.punchcafe.sefizzbuzz.cli.App;
import org.junit.jupiter.api.*;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpResponse;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimpleSmokeTest {

    private ClientAndServer clientAndServer;

    @BeforeAll
    void beforeAll() {
        this.clientAndServer = startClientAndServer(4000);
    }

    @BeforeEach
    void beforeEach() {
        this.clientAndServer.reset();
    }

    @AfterAll
    void afterAll() {
        clientAndServer.stop();
    }

    @Test
    void requestsCorrectPathForCalculate() {
        clientAndServer.when(request()
                .withMethod("GET")
                .withPath("/fizzbuzz/15"))
                .respond(HttpResponse.response()
                        .withStatusCode(200)
                        .withBody("{ \"id\": 15, \"value\" : \"FizzBuzz\", \"is_favourite\" : false}"));

        App.main(new String[]{"calculate", "15"});

        clientAndServer.verify(request()
                .withMethod("GET")
                .withPath("/fizzbuzz/15"));
    }
}
