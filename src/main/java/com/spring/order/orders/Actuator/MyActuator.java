package com.spring.order.orders.Actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component // Use @Component instead of @Controller
public class MyActuator implements HealthIndicator {

    @Override
    public Health health() {
        if (isHealthGood()) {
            return Health.up()
                         .withDetail("Application Health", getClass().getSimpleName())
                         .build();
        } else {
            return Health.down()
                         .withDetail("Application Health", getClass().getSimpleName())
                         .build();
        }
    }

    private boolean isHealthGood() {
        return true;
    }
}
