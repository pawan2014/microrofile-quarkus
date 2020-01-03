package org.pk.health;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class MemoryHealthCheck implements HealthCheck {
	@Override
	public HealthCheckResponse call() {

		HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("@Readiness-Memory health check");

		try {
			int mb = 1024*1024;
			//Getting the runtime reference from system
			Runtime runtime = Runtime.getRuntime();
			System.out.println("Hello world");
			System.out.println("##### Heap utilization statistics [MB] #####");
			//Print used memory
			System.out.println("Used Memory:" 
				+ (runtime.totalMemory() - runtime.freeMemory()) / mb);
			//Print free memory
			System.out.println("Free Memory:" 
				+ runtime.freeMemory() / mb);
			//Print total available memory
			System.out.println("Total Memory:" + runtime.totalMemory() / mb);
			//Print Maximum available memory
			System.out.println("Max Memory:" + runtime.maxMemory() / mb);
			// Response builder
			responseBuilder.up()
			.withData("Used Memory", (runtime.totalMemory() - runtime.freeMemory()) / mb)
			.withData("Total Memory", runtime.totalMemory() / mb)
			.withData("Max Memory", runtime.maxMemory() / mb);
		} catch (Exception e) {
			// cannot access the database
			responseBuilder.down().withData("Exception getting readiness", e.toString());
			
		}

		return responseBuilder.build();
	}

}
