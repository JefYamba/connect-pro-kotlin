package dev.jefy.connectpro.portfolio.application.exceptions

class PortfolioAlreadyExistsException() : RuntimeException("Portfolio already exists")
class ProjectAlreadyExistsException() : RuntimeException("Project already exists")
class SocialLinkAlreadyExistsException() : RuntimeException("Social link already exists")
class JobPostAlreadyExistsException() : RuntimeException("Job post already exists")
class ServiceAlreadyExistsException() : RuntimeException("Service already exists")

class ProjectNotFoundException() : RuntimeException("Project not found")
class AwardNotFoundException() : RuntimeException("Award not found")
class SocialNotFoundException() : RuntimeException("Social link not found")
class PortfolioNotFoundException() : RuntimeException("Portfolio not found")
class FaqNotFoundException() : RuntimeException("Faq not found")
class ServiceNotFoundException() : RuntimeException("Service not found")
class JobPostNotFoundException() : RuntimeException("Job post not found")
class CategoryNotFoundException() : RuntimeException("Category not found")
class BadgeNotFoundException() : RuntimeException("Badge not found")