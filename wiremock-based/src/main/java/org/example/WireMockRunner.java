package org.example;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

//@Getter
@Slf4j
public class WireMockRunner implements Supplier<WireMockServer>, Function<WireMockConfiguration, WireMockServer> {

    private final int WIREMOCK_PORT = 8089;
    private WireMockServer wireMockServerInstance;

    @Override
    public WireMockServer get() {
        WireMockConfiguration wireMockConfiguration = options()
                .extensions(UserIdTransformer.class, BookIdTransformer.class)
                .port(WIREMOCK_PORT)
                .notifier(new Slf4jNotifier(true));

        return apply(wireMockConfiguration);
    }

    @Override
    public WireMockServer apply(WireMockConfiguration wireMockConfiguration) {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfiguration);
        logWiremockServerOptions(wireMockServer);
        return wireMockServer;
    }

    private void logWiremockServerOptions(WireMockServer wireMockServer) {
        System.out.println("URL: http://localhost:" + wireMockServer.getOptions().portNumber());
    }


}
