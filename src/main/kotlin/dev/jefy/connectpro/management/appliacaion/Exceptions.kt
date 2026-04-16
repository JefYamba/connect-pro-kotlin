package dev.jefy.connectpro.management.appliacaion

/**
 * @author  Jôph Yamba
 */

class AwardAlreadyExistsException: RuntimeException("Award already exists")
class BadgeAlreadyExistsException: RuntimeException("Badge already exists")
class CategoryAlreadyExistsException: RuntimeException("Category already exists")
class CategoryAlreadyInUseException: RuntimeException("Category already in use")
class BadgeAlreadyInUseException: RuntimeException("Badge already in use")
class AwardAlreadyInUseException: RuntimeException("Award already in use")