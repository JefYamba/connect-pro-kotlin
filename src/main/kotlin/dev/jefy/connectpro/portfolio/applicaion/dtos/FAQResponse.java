package dev.jefy.connectpro.portfolio.applicaion.dtos;


import java.util.UUID;

import dev.jefy.connectpro.portfolio.domain.model.FAQ;

/**
 * @author Jôph Yamba
 */
public record FAQResponse (
    UUID id,
    String question,
    String answer
) {
    public static FAQResponse from(FAQ faq) {
        return new FAQResponse(
                faq.getId().value(),
                faq.getQuestion(),
                faq.getAnswer()
        );
    }
}

