package com.gailo22.saga.seat;

import io.eventuate.common.spring.id.IdGeneratorConfiguration;
import io.eventuate.messaging.kafka.spring.consumer.MessageConsumerKafkaConfiguration;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@Import({OptimisticLockingDecoratorConfiguration.class, SagaOrchestratorConfiguration.class, IdGeneratorConfiguration.class, EventuateTramKafkaMessageConsumerConfiguration.class})
public class SeatConfiguration {

    @Bean
    public SeatSagaService seatSagaService(SeatRepository seatRepository, SagaInstanceFactory sagaInstanceFactory, BookSeatSaga bookSeatSaga) {
        return new SeatSagaService(sagaInstanceFactory, bookSeatSaga, seatRepository);
    }

    @Bean
    public SeatService orderService(SeatRepository seatRepository) {
        return new SeatService(seatRepository);
    }

    @Bean
    public BookSeatSaga bookSeatSaga(SeatService seatService) {
        return new BookSeatSaga(seatService);
    }

}
