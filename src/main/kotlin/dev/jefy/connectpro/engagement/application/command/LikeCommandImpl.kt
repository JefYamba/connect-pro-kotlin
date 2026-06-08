package dev.jefy.connectpro.engagement.application.command

import dev.jefy.connectpro.engagement.application.exception.ServiceNotExistOrValidException
import dev.jefy.connectpro.engagement.domain.Like
import dev.jefy.connectpro.engagement.domain.repository.LikeRepository
import dev.jefy.connectpro.engagement.domain.vo.LikeId
import dev.jefy.connectpro.portfolio.PortfolioClient
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.recommandation.RecommandationClient
import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.domain.vo.UserId
import org.jspecify.annotations.NullMarked
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional
class LikeCommandImpl(
    private val portfolioClient: PortfolioClient,
    private val userClient: UserClient,
    private val likeRepo: LikeRepository,
    private val recommandationClient: RecommandationClient
) : LikeCommand {

    override fun like(serviceId: ServiceId): Boolean {
        if (portfolioClient.notExistsAndValidService(serviceId)) {
            throw ServiceNotExistOrValidException()
        }

        val user = userClient.getCurrentUser()
        val id = LikeId.of(UserId(user.id), serviceId)
        
        if (likeRepo.existsById(id)) {
            likeRepo.deleteById(id)
            recommandationClient.untrackEvent(EventType.LIKE, serviceId.value, TargetType.SERVICE)
            return false
        } else {
            likeRepo.save(Like(UserId(user.id), serviceId))
            recommandationClient.trackEvent(EventType.LIKE, serviceId.value, TargetType.SERVICE)
            return true
        }
    }
}