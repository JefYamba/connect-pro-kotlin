package dev.jefy.connectpro.marketplace.domain

import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.shared.domain.vo.JobApplicationStatus
import dev.jefy.connectpro.user.domain.vo.UserId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.Instant

/**
 * @author Jôph Yamba
 */
@Entity
@Table(name = "job_applications")
open class JobApplication(applicantId: UserId, jobPostId: JobPostId, motivation: String) {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: JobApplicationId = JobApplicationId(applicantId.value, jobPostId.value)
        protected set

    var motivation: String = motivation
        protected set

    var appliedAt: Instant = Instant.now()
        protected set

    @Enumerated(EnumType.STRING)
    var status: JobApplicationStatus = JobApplicationStatus.PENDING
        protected set

    fun accept() { status = JobApplicationStatus.ACCEPTED }

    fun reject() { status = JobApplicationStatus.REJECTED }
}