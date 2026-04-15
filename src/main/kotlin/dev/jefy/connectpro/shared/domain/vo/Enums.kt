package dev.jefy.connectpro.shared.domain.vo

/**
 * @author  Jôph Yamba
 */
enum class Gender(val value: String) { MALE("Male"), FEMALE("Female")}

enum class ExtraType { BRONZE, SILVER, GOLD }

enum class JobApplicationStatus { PENDING, ACCEPTED, REJECTED }

enum class JobType { FULL_TIME, PART_TIME, FREELANCE, INTERNSHIP, }

enum class PayPeriod { HOURLY, DAILY, WEEKLY, MONTHLY }

enum class WorkMode { REMOTE, ON_SITE, HYBRID }