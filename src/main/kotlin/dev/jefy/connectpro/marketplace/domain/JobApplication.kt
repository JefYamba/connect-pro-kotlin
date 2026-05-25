package dev.jefy.connectpro.marketplace.domain

import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId
import dev.jefy.connectpro.shared.domain.vo.JobApplicationStatus
import jakarta.persistence.*
import java.time.Instant

/**
 * @author Jôph Yamba
 */
@Entity
@Table(name = "job_applications")
open class JobApplication(
    @EmbeddedId
    var id: JobApplicationId,
    @Column(columnDefinition = "TEXT")
    var motivation: String,
    var appliedAt: Instant = Instant.now(),
    @Enumerated(EnumType.STRING)
    var status: JobApplicationStatus = JobApplicationStatus.PENDING
) {
    
    fun accept() { status = JobApplicationStatus.ACCEPTED }
    fun reject() { status = JobApplicationStatus.REJECTED }
}