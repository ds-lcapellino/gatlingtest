/********************************************************************************
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ********************************************************************************/
package loadTest;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.bodyString;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class PostTemplateSimulation extends Simulation {

    FeederBuilder.FileBased<Object> feeder = CoreDsl.jsonFile("input.json").queue();

    // Define the base URL
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080") // Replace with your base URL
            .acceptHeader("application/json");

    // Define the scenario
    ScenarioBuilder scenario = scenario("PostSomethingScenario")
            .feed(feeder)
            .exec(
                    http("POST /postSomething")
                            .post("/postSomething")
                            .body(StringBody("{\"string\": \"#{string}\"}")) // Send a JSON payload
                            .check(status().is(200),
                                    bodyString().is("Success")) // Check that the response status is 200 OK
            );

    {
        // Set up the simulation
        setUp(
                scenario.injectOpen(atOnceUsers(2)) // 10 users will hit the /hello endpoint simultaneously
        ).protocols(httpProtocol);
    }
}
