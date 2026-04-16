package dev.jefy.connectpro.messaging.domain.repositoty;

import org.springframework.stereotype.Repository;

import dev.jefy.connectpro.messaging.domain.Message;
import dev.jefy.connectpro.messaging.domain.vo.MessageId;
import dev.jefy.connectpro.shared.infrastructure.ddd.AggregateRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface MessageRepository extends AggregateRepository<Message, MessageId> {
}
