package com.example.jpmorganjavatest.testconfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.Map;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext)
    {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8085));
        wireMockServer.start();

        configurableApplicationContext
            .getBeanFactory()
            .registerSingleton("wireMockServer", wireMockServer);

        configurableApplicationContext.addApplicationListener(applicationEvent -> {
            if (applicationEvent instanceof ContextClosedEvent)
            {
                wireMockServer.stop();
            }
        });

        TestPropertyValues
            .of(Map.of("base_url", "http://localhost:8085"))
            .applyTo(configurableApplicationContext);
    }
}

