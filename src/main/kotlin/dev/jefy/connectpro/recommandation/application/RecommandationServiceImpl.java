package dev.jefy.connectpro.recommandation.application;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import dev.jefy.connectpro.management.domain.Category;
import dev.jefy.connectpro.management.domain.repositoty.CategoryRepository;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.portfolio.PortfolioClient;
import dev.jefy.connectpro.recommandation.domain.EventTracking;
import dev.jefy.connectpro.recommandation.domain.repository.EventTrackingRepository;
import dev.jefy.connectpro.recommandation.domain.vo.TargetType;
import dev.jefy.connectpro.user.UserClient;
import dev.jefy.connectpro.user.application.dtos.UserData;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommandationServiceImpl implements RecommandationService {
    private final EventTrackingRepository eventTrackingRepo;
    private final UserClient userClient;
    private final PortfolioClient portfolioClient;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryId> getRecommendedCategories() {
        UserData currentUser = userClient.getCurrentUser();
        List<EventTracking> events = eventTrackingRepo.findAllByUserId(currentUser.id());
        
        if (events.isEmpty()) {
            return getPopularCategories();
        }

        Map<CategoryId, Integer> categoryScores = new HashMap<>();
        
        for (EventTracking event : events) {
            Optional<CategoryId> categoryIdOpt = getCategoryIdFromTarget(event);
            if (categoryIdOpt.isPresent()) {
                CategoryId categoryId = categoryIdOpt.get();
                int weight = getEventWeight(event);
                categoryScores.put(categoryId, categoryScores.getOrDefault(categoryId, 0) + weight);
            }
        }
        
        if (categoryScores.isEmpty()) {
            return getPopularCategories();
        }

        return categoryScores.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .limit(5)
                .collect(Collectors.toList());
    }

    private List<CategoryId> getPopularCategories() {
        return categoryRepository.findAll(PageRequest.of(0, 5))
                .getContent().stream()
                .map(Category::getId)
                .collect(Collectors.toList());
    }
    
    private Optional<CategoryId> getCategoryIdFromTarget(EventTracking event) {
        if (event.getTargetType() == TargetType.SERVICE || event.getTargetType() == TargetType.CONTACT_FOR_SERVICE) {
            return portfolioClient.getServiceCategoryId(event.getTargetId());
        } else if (event.getTargetType() == TargetType.JOB_POST || event.getTargetType() == TargetType.JOB_APPLICATION) {
            return portfolioClient.getJobPostCategoryId(event.getTargetId());
        } else if (event.getTargetType() == TargetType.LIKE) {
             // In current model, Like target might be a ServiceId (to be checked)
             return portfolioClient.getServiceCategoryId(event.getTargetId());
        } else if (event.getTargetType() == TargetType.REVIEW) {
             return portfolioClient.getServiceCategoryId(event.getTargetId());
        }
        return Optional.empty();
    }
    
    private int getEventWeight(EventTracking event) {
        return switch (event.getEventType()) {
            case LIKE -> 5;
            case REVIEW -> 4;
            case CLICK -> 2;
            case VIEW -> 1;
        };
    }
}
