package ru.perm.v.reactivesimple;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class CombineTest {

	@Test
	public void mergeFluxes() {
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa")
				.delayElements(Duration.ofMillis(500));
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples")
				.delaySubscription(Duration.ofMillis(250))
				.delayElements(Duration.ofMillis(500));
//		Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);
		Flux<String> mergedFlux = foodFlux.mergeWith(characterFlux);
		mergedFlux.subscribe(System.out::println);
		StepVerifier.create(mergedFlux)
				.expectNext("Garfield")
				.expectNext("Lasagna")
				.expectNext("Kojak")
				.expectNext("Lollipops")
				.expectNext("Barbossa")
				.expectNext("Apples")
				.verifyComplete();
	}

	/**
	 * Комбинирует парами
	 */
	@Test
	public void zipFluxes() {
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");
		// Tuple2 - контейнер для 2-х объектов. Оба String
		Flux<Tuple2<String, String>> zippedFlux =
				Flux.zip(characterFlux, foodFlux);
		StepVerifier.create(zippedFlux)
				.expectNextMatches(p ->
						p.getT1().equals("Garfield") &&
								p.getT2().equals("Lasagna"))
				.expectNextMatches(p ->
						p.getT1().equals("Kojak") &&
								p.getT2().equals("Lollipops"))
				.expectNextMatches(p ->
						p.getT1().equals("Barbossa") &&
								p.getT2().equals("Apples"))
				.verifyComplete();
	}

	/**
	 * Комбинирование из 2-х потоков в один преобразованный
	 */
	@Test
	public void zipFluxesToObject() {
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");
		Flux<String> zippedFlux =
				Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);
		StepVerifier.create(zippedFlux)
				.expectNext("Garfield eats Lasagna")
				.expectNext("Kojak eats Lollipops")
				.expectNext("Barbossa eats Apples")
				.verifyComplete();
	}}
