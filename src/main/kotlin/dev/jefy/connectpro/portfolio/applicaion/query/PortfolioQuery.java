package dev.jefy.connectpro.portfolio.applicaion.query;

import dev.jefy.connectpro.portfolio.applicaion.dtos.JobPostResponse;
import dev.jefy.connectpro.portfolio.applicaion.dtos.PortfolioResponse;
import dev.jefy.connectpro.portfolio.applicaion.dtos.ServiceResponse;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId;

/**
 * @author Jôph Yamba
 */
public interface PortfolioQuery {
    PortfolioResponse get(PortfolioId portfolioId);
    JobPostResponse getJopPost(JobPostId jobPostId);
    ServiceResponse getService(ServiceId serviceId );
}
