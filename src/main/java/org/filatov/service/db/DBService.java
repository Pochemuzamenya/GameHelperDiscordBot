package org.filatov.service.db;

import reactor.core.publisher.Mono;

public interface DBService<T> {

    Mono<T> create(T t);

    Mono<T> getByDiscordName(String name);

    Mono<T> update(T t);

    Mono<Void> delete(Integer id);

}
