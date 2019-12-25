package ru.perm.v.reactivesimple;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FilterTest {
	@Test
	public void skipAFew() {
		Flux<String> skipFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred")
				.skip(3);
		StepVerifier.create(skipFlux)
				.expectNext("ninety nine", "one hundred")
				.verifyComplete();
	}

	@Test
	public void skipAFewSeconds() {
		Flux<String> skipFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred")
				.delayElements(Duration.ofSeconds(1))
				.skip(Duration.ofSeconds(4));
		StepVerifier.create(skipFlux)
				.expectNext("ninety nine", "one hundred")
				.verifyComplete();
	}

	@Test
	public void take() {
		Flux<String> nationalParkFlux = Flux.just(
				"Yellowstone", "Yosemite", "Grand Canyon",
				"Zion", "Grand Teton")
				.take(3);
		StepVerifier.create(nationalParkFlux)
				.expectNext("Yellowstone", "Yosemite", "Grand Canyon")
				.verifyComplete();
	}

	@Test
	public void takeByTime() {
		Flux<String> nationalParkFlux = Flux.just(
				"Yellowstone", "Yosemite", "Grand Canyon",
				"Zion", "Grand Teton")
				.delayElements(Duration.ofSeconds(1))
				.take(Duration.ofMillis(3500));
		StepVerifier.create(nationalParkFlux)
				.expectNext("Yellowstone", "Yosemite", "Grand Canyon")
				.verifyComplete();
	}

	@Test
	public void filter() {
		Flux<String> nationalParkFlux = Flux.just(
				"Yellowstone", "Yosemite", "Grand Canyon",
				"Zion", "Grand Teton")
				.filter(np -> !np.contains(" "));
		StepVerifier.create(nationalParkFlux)
				.expectNext("Yellowstone", "Yosemite", "Zion")
				.verifyComplete();
	}

	@Test
	public void distinct() {
		Flux<String> animalFlux = Flux.just(
				"dog", "cat", "bird", "dog", "bird", "anteater")
				.distinct();
		StepVerifier.create(animalFlux)
				.expectNext("dog", "cat", "bird", "anteater")
				.verifyComplete();
	}
}
