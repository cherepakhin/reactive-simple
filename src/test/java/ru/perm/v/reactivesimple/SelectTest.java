package ru.perm.v.reactivesimple;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class SelectTest {

	/**
	 * Выполняется до тех пор когда не кончится один из потоков
	 */
	@Test
	public void firstFlux() {
		Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
				.delaySubscription(Duration.ofMillis(100));
		Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");
		Flux<String> firstFlux = Flux.first(slowFlux, fastFlux);
		StepVerifier.create(firstFlux)
				.expectNext("hare")
				.expectNext("cheetah")
				.expectNext("squirrel")
				.verifyComplete();
	}
}
