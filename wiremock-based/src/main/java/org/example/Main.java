package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

public class Main {

    public final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockRunner().get();

        users(wireMockServer);
        usersId(wireMockServer);
        booksId(wireMockServer);

        wireMockServer.start();
    }

    private static void users(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(
                        urlEqualTo("/users")
                ).willReturn(
                        aResponse().withJsonBody(OBJECT_MAPPER.valueToTree(User.generateUsers()))
                )
        );
    }

    private static void usersId(WireMockServer wireMockServer) {
        JsonNode jsonBody = OBJECT_MAPPER.valueToTree(User.generateUser());

        wireMockServer.stubFor(
                get(
                        urlMatching("/users/([0-9]*)")
                ).willReturn(
                        aResponse()
                                .withJsonBody(jsonBody)
                                .withTransformers("user-id-transformer")
                )
        );
    }

    private static void booksId(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(
                        urlMatching("/books/([0-9]*)")
                ).willReturn(
                        aResponse()
                                .withTransformers("book-id-transformer")
                )
        );
    }

}