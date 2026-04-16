package dev.jefy.connectpro.messaging.domain.repositoty;


import org.springframework.stereotype.Repository;

import dev.jefy.connectpro.messaging.domain.Conversation;
import dev.jefy.connectpro.messaging.domain.vo.ConversationId;
import dev.jefy.connectpro.shared.infrastructure.ddd.AggregateRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface ConversationRepository extends AggregateRepository<Conversation, ConversationId> {
}

