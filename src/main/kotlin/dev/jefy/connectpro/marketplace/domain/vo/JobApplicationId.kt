package dev.jefy.connectpro.marketplace.domain.vo

import jakarta.persistence.Embeddable
import java.util.*

/**
 * @author Jôph Yamba
 */
@Embeddable
data class JobApplicationId(var applicantId: UUID, var jobPostId: UUID)