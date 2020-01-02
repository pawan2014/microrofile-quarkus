package org.pk.health;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class SimpleHealthCheck implements HealthCheck {
	 @Override
	    public HealthCheckResponse call() {
	        return HealthCheckResponse.up("@Liveness-Health check- My BP is Normal, but cannot gurantee");
	    }
}
