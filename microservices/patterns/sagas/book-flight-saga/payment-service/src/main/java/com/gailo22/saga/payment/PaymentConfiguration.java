package com.gailo22.saga.payment;

import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OptimisticLockingDecoratorConfiguration.class, SagaParticipantConfiguration.class, EventuateTramKafkaMessageConsumerConfiguration.class})
//@Import(OptimisticLockingDecoratorConfiguration.class)
@EnableAutoConfiguration
public class PaymentConfiguration {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService();
    }

    @Bean
    public PaymentCommandHandler paymentCommandHandler(PaymentService paymentService) {
        return new PaymentCommandHandler(paymentService);
    }

    @Bean
    public CommandDispatcher consumerCommandDispatcher(PaymentCommandHandler target,
                                                       SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
        return sagaCommandDispatcherFactory
                .make("paymentCommandDispatcher", target.commandHandlerDefinitions());
    }
}
