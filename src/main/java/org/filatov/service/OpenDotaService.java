package org.filatov.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public interface OpenDotaService {

    Logger log = LoggerFactory.getLogger(OpenDotaService.class);

    Mono<String> winLose();

    Mono<String> wardMap();

    Mono<String> wordCloud();

    Mono<String> refresh();
}
