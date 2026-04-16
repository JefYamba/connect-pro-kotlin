package dev.jefy.connectpro.management.appliacaion.query;

import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse;
import dev.jefy.connectpro.management.domain.repositoty.AwardRepository;
import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AwardQueryImpl implements AwardQuery {
    private final AwardRepository awardRepo;
    @Override
    public AwardResponse get(AwardId awardId) {
        return awardRepo
                .findById(awardId)
                .map(AwardResponse::from)
                .orElseThrow(()-> new ResourceNotFound(
                        "Award with id: %s not found".formatted(awardId.value())
                ));
    }

    @Override
    public List<AwardResponse> getAll() {
        return awardRepo
                .findAll()
                .stream()
                .map(AwardResponse::from)
                .toList();
    }
}
