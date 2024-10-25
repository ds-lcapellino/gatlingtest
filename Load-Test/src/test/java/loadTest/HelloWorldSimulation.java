package loadTest;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class HelloWorldSimulation extends Simulation {

    // Define the base URL
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json");

    // Define the scenario
    ScenarioBuilder scenario = scenario("HelloEndpointScenario")
            .exec(
                    http("GET /hello")
                            .get("/hello")
                            .check(status().is(200)) // Check that the response status is 200 OK
            );

    {
        // Set up the simulation
        setUp(
                scenario.injectOpen(atOnceUsers(10)) // 10 users will hit the /hello endpoint simultaneously
        ).protocols(httpProtocol);
    }
}
