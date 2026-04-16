package dev.jefy.connectpro.management.appliacaion.query;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import dev.jefy.connectpro.management.appliacaion.dtos.BadgeResponse;
import dev.jefy.connectpro.management.domain.repositoty.BadgeRepository;
import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BadgeQueryImpl implements BadgeQuery {
    private final BadgeRepository badgeRepo;
    @Override
    public BadgeResponse get(BadgeId badgeId) {
        return badgeRepo
                .findById(badgeId)
                .map(BadgeResponse::from)
                .orElseThrow(()-> new ResourceNotFound(
                        "badge with id: %s not found".formatted(badgeId.value())
                ));
    }

    @Override
    public List<BadgeResponse> getAll() {
        return badgeRepo
                .findAll()
                .stream()
                .map(BadgeResponse::from)
                .toList();
    }
}
