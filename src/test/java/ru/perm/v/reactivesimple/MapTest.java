package ru.perm.v.reactivesimple;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapTest {
	// Выполняется синхронно!!!!! Для асинх. через flatMap
	@Test
	public void map() {
		Flux<Player> playerFlux = Flux
				.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
				.map(n -> {
					String[] split = n.split("\\s");
					return new Player(split[0], split[1]);
				});
		StepVerifier.create(playerFlux)
				.expectNext(new Player("Michael", "Jordan"))
				.expectNext(new Player("Scottie", "Pippen"))
				.expectNext(new Player("Steve", "Kerr"))
				.verifyComplete();
	}

	// Выполняется асинхронно !!!!
	@Test
	public void flatMap() {
		Flux<Player> playerFlux = Flux
				.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
				.flatMap(n -> Mono.just(n)
						.map(p -> {
							String[] split = p.split("\\s");
							return new Player(split[0], split[1]);
						})
						.subscribeOn(Schedulers.parallel())
				);
		List<Player> playerList = Arrays.asList(
				new Player("Michael", "Jordan"),
				new Player("Scottie", "Pippen"),
				new Player("Steve", "Kerr"));
		StepVerifier.create(playerFlux)
				.expectNextMatches(p -> playerList.contains(p))
				.expectNextMatches(p -> playerList.contains(p))
				.expectNextMatches(p -> playerList.contains(p))
				.verifyComplete();
	}

	@Test
	public void buffer() {
		Flux<String> fruitFlux = Flux.just(
				"apple", "orange", "banana", "kiwi", "strawberry");
		Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);
		StepVerifier
				.create(bufferedFlux)
				.expectNext(Arrays.asList("apple", "orange", "banana"))
				.expectNext(Arrays.asList("kiwi", "strawberry"))
				.verifyComplete();
	}

	@Test
	public void bufferMap() {
		String[] fruits = new String[]{"apple", "orange", "banana", "kiwi", "strawberry"};
		ArrayList<String> FRUITS = Arrays.stream(fruits).map(s -> s.toUpperCase()).collect(Collectors.toCollection(ArrayList::new));
		Flux<String> fruitsFlux =
				Flux.fromArray(fruits)
						.buffer(3)
						.flatMap(x ->
								Flux.fromIterable(x)
										.map(y -> {
											System.out.println(y);
											return y.toUpperCase();
										})
										.subscribeOn(Schedulers.parallel())
										.log()
						);
		StepVerifier.create(fruitsFlux)
				.expectNextMatches(p -> FRUITS.contains(p))
				.expectNextMatches(p -> FRUITS.contains(p))
				.expectNextMatches(p -> FRUITS.contains(p))
				.expectNextMatches(p -> FRUITS.contains(p))
				.expectNextMatches(p -> FRUITS.contains(p))
				.verifyComplete();
	}

	@Test
	public void bufferMap2() {
		String[] fruits = new String[]{"apple", "orange", "banana", "kiwi", "strawberry"};
		ArrayList<String> FRUITS = Arrays.stream(fruits).map(s -> s.toUpperCase()).collect(Collectors.toCollection(ArrayList::new));
		Flux<String> fruitsFlux =
				Flux.fromArray(fruits)
						.buffer(3)
						.flatMap(x ->
								Flux.fromIterable(x)
										.map(y -> {
//											System.out.println(y);
											return y.toUpperCase();
										})
										.subscribeOn(Schedulers.parallel())
										.log()
						)
//						.log()
						;
		StepVerifier.create(fruitsFlux)
				.expectNextMatches(p -> FRUITS.contains(p))
				.expectNextMatches(p -> FRUITS.contains(p))
				.expectNextMatches(p -> FRUITS.contains(p))
				.expectNextMatches(p -> FRUITS.contains(p))
				.expectNextMatches(p -> FRUITS.contains(p))
				.verifyComplete();
	}

	@Test
	public void collectList() {
		Flux<String> fruitFlux = Flux.just(
				"apple", "orange", "banana", "kiwi", "strawberry");
		Mono<List<String>> fruitListMono = fruitFlux.collectList();
		StepVerifier
				.create(fruitListMono)
				.expectNext(Arrays.asList(
						"apple", "orange", "banana", "kiwi", "strawberry"))
				.verifyComplete();
	}

	@Test
	public void collectMap() {
		Flux<String> animalFlux = Flux.just(
				"aardvark", "elephant", "koala", "eagle", "kangaroo");
		Mono<Map<Character, String>> animalMapMono =
				animalFlux.collectMap(a -> a.charAt(0));
		StepVerifier
				.create(animalMapMono)
				.expectNextMatches(map -> {
					return
							map.size() == 3 &&
									map.get('a').equals("aardvark") &&
									map.get('e').equals("eagle") &&
									map.get('k').equals("kangaroo");
				})
				.verifyComplete();
	}
}
