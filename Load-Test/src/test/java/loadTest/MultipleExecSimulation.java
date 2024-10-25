package loadTest;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class MultipleExecSimulation extends Simulation {

    // Define the base URL
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json");

    // Define the scenario
    ScenarioBuilder scenario = scenario("MightFailEndpointScenario")
            .exec(
                    http("GET /mightFail")
                            .get("/mightFail")
                            .check(status().is(200)),
                    http("GET /hello").get("/hello")
                            .check(status().is(200))
            ).pause(2) //pause 2 seconds
            .exec(
                    http("GET /hello")
                            .get("/hello")
                            .check(status().is(200))
            );

    {
        // Set up the simulation
        setUp(
                scenario.injectOpen(atOnceUsers(10))
        ).protocols(httpProtocol);
    }
}
