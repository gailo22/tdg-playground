package com.gailo22.webflux;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebClientAPI {

    private WebClient webClient;

    public static void main(String[] args) throws InterruptedException {
        WebClientAPI api = new WebClientAPI();

        api.postNewProduct()
                .thenMany(api.getAllProducts())
                .take(1)
                .flatMap(p -> api.updateProduct(p.getId(), "New Choco", 2.99))
                .flatMap(p -> api.deleteProduct(p.getId()))
                .thenMany(api.getAllEvents())
                .subscribe(System.out::println);

        Thread.sleep(3000);
    }

    public WebClientAPI() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/products")
                .build();
    }

    private Mono<ResponseEntity<Product>> postNewProduct() {
        return webClient
                .post()
                .body(Mono.just(new Product(null, "Choco", 1.55)), Product.class)
                .exchangeToMono(response -> response.toEntity(Product.class))
                .doOnSuccess(o -> System.out.println("*** POST: " + o));
    }

    private Flux<Product> getAllProducts() {
        return webClient
                .get()
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(o -> System.out.println("*** GET: " + o));
    }

    private Mono<Product> updateProduct(String id, String name, double price) {
        return webClient
                .put()
                .uri("/{id}", id)
                .body(Mono.just(new Product(null, name, price)), Product.class)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnSuccess(o -> System.out.println("*** UPDATE: " + o));
    }

    private Mono<Void> deleteProduct(String id) {
        return webClient
                .delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(o -> System.out.println("*** DELETE: " + o));
    }

    private Flux<ProductEvent> getAllEvents() {
        return webClient
                .get()
                .uri("/events")
                .retrieve()
                .bodyToFlux(ProductEvent.class);
    }

}
