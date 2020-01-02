package org.pk.health;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class PorcessHealthCheck implements HealthCheck {
	@Override
	public HealthCheckResponse call() {

		HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("@Readiness-Process number check");
		int numberproc = Runtime.getRuntime().availableProcessors();
		if (numberproc > 6) {
			responseBuilder.up().withData("Processor", numberproc);
		} else

		{
			responseBuilder.down().withData("Processor", numberproc);
		}

		return responseBuilder.build();
	}

}
