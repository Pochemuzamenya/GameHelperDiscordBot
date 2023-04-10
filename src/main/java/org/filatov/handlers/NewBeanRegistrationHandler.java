package org.filatov.handlers;

import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.filatov.model.springbean.BeanMD;
import org.filatov.service.BeanRegistrationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NewBeanRegistrationHandler implements CommandHandler {

    private final UserInputHandler inputHandler;

    private final BeanRegistrationService beanRegistrationService;


    @Override
    public Mono<String> handleCommand(Message event) {

        return BuildBeanMD(event)
                .map(beanRegistrationService::regBean);

    }

    private Mono<BeanMD> BuildBeanMD(Message event) {
        return inputHandler
                .getParams(event.getContent())
                .collectList()
                .map(
                        list -> BeanMD.builder()
                                .beanName(list.get(0))
                                .beanClassName(list.get(1))
                                .build()
                );
    }

    @Override
    public String getMyCommandName() {
        return "reg_bean";
    }
}
