package dev.jefy.connectpro.portfolio.applicaion.dtos;


import org.springframework.util.Assert;

/**
 * @author Jôph Yamba
 */
public record FAQRequest(
    String question,
    String answer
) {
    public FAQRequest {
        Assert.hasText(question, "question must not be empty");
        Assert.hasText(answer, "answer must not be empty");
    }
}
