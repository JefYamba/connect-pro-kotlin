package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.engagement.EngagementClient
import dev.jefy.connectpro.management.ManagementClient
import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.portfolio.application.dtos.FAQRequest
import dev.jefy.connectpro.portfolio.application.dtos.ServiceRequest
import dev.jefy.connectpro.portfolio.application.exceptions.AwardNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.CategoryNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.ServiceAlreadyExistsException
import dev.jefy.connectpro.portfolio.application.exceptions.ServiceNotFoundException
import dev.jefy.connectpro.portfolio.domain.model.Service
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository
import dev.jefy.connectpro.portfolio.domain.repository.ServiceRepository
import dev.jefy.connectpro.portfolio.domain.vo.FAQId
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.shared.application.dtos.ImageData
import dev.jefy.connectpro.shared.domain.vo.Image
import dev.jefy.connectpro.shared.infrastructure.annotations.CommandService
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageService
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@CommandService
class ServiceCommandImpl(
    private val portfolioRepo: PortfolioRepository,
    private val serviceRepo: ServiceRepository,
    private val imageService: ImageService,
    private val managementClient: ManagementClient,
    private val engagementClient: EngagementClient,
    private val tagService: TagService
) : ServiceCommand {

    override fun create(request: ServiceRequest): ServiceId {
        val portfolioId = PortfolioId.of(request.portfolioId)
        val categoryId = CategoryId.of(request.categoryId)

        if (managementClient.notExistsCategory(categoryId)) throw CategoryNotFoundException()

        require(portfolioRepo.existsById(portfolioId)) { "Portfolio with id $portfolioId not found" }

        if (serviceRepo.existsByTitleConflict(portfolioId = portfolioId, title = request.name)) throw ServiceAlreadyExistsException()

        val tags = tagService.resolveTags(stringTags = request.tags)

        val service = Service(
            portfolioId = portfolioId,
            name = request.name,
            description = request.description,
            categoryId = categoryId,
            tags = tags.toMutableSet()
        )
        serviceRepo.save(service)

        return service.id
    }

    override fun update(
        serviceId: ServiceId, request: ServiceRequest
    ): ServiceId {
        val service = getService(serviceId)
        val categoryId = CategoryId.of(request.categoryId)

        if (managementClient.notExistsCategory(categoryId)) throw CategoryNotFoundException()

        if (serviceRepo.existsByTitleConflictForId(
                portfolioId = service.portfolioId,
                title = request.name,
                serviceId = serviceId
            )) throw ServiceAlreadyExistsException()

        val tags = tagService.resolveTags(stringTags = request.tags)

        service.update(
            title = request.name,
            description = request.description,
            categoryId = categoryId,
            tags = tags
        )
        serviceRepo.save(service)
        
        return service.id
    }

    @Throws(IOException::class)
    override fun setCoverImage(serviceId: ServiceId, image: MultipartFile): ServiceId {
        val service = getService(serviceId)
        val imageUrl = imageService.save(image)
        service.addCoverImage(imageUrl)
        serviceRepo.save(service)
        return service.id
    }
        

    @Throws(IOException::class)
    override fun deleteCoverImage(serviceId: ServiceId): ServiceId {
        val service = getService(serviceId)
        service.coverImage?.let { imageService.delete(Image(it)) }
        service.deleteCoverImage()
        serviceRepo.save(service)
        return service.id
    }

    @Throws(IOException::class)
    override fun addImage(serviceId: ServiceId, image: MultipartFile): ServiceId {
        val service = getService(serviceId)
        val imageUrl = imageService.save(image)
        service.addImage(imageUrl)
        serviceRepo.save(service)
        return service.id
    }


    @Throws(IOException::class)
    override fun addImages(serviceId: ServiceId, images: List<MultipartFile>): ServiceId {
        val service = getService(serviceId)
        images.forEach { file ->
            val imageUrl = imageService.save(file)
            service.addImage(imageUrl)
        }
        serviceRepo.save(service)
        return service.id
    }

    @Throws(IOException::class)
    override fun removeImage(serviceId: ServiceId, image: ImageData): ServiceId {
        val service = getService(serviceId)
        imageService.delete(image.getKey())
        service.removeImage(image.getKey())
        serviceRepo.save(service)
        return service.id
    }
        

    override fun addFaq(serviceId: ServiceId, request: FAQRequest): ServiceId =
        getService(serviceId)
            .apply { addFaq(request) }
            .also { serviceRepo.save(it) }
            .id

    override fun removeFaq(serviceId: ServiceId, faqId: FAQId): ServiceId =
        getService(serviceId)
            .apply { removeFaq(faqId) }
            .also { serviceRepo.save(it) }
            .id

    override fun setAward(serviceId: ServiceId, awardId: AwardId): ServiceId {
        if (managementClient.notExistsAward(awardId)) throw AwardNotFoundException()

        return getService(serviceId)
            .apply { addAwardId(awardId) }
            .also { serviceRepo.save(it) }
            .id
    }

    override fun removeAward(serviceId: ServiceId): ServiceId =
        getService(serviceId)
            .apply { removeAward() }
            .also { serviceRepo.save(it) }
            .id

    override fun activateService(serviceId: ServiceId): ServiceId =
        getService(serviceId)
            .apply { activate() }
            .also { serviceRepo.save(it) }
            .id

    override fun deactivateService(serviceId: ServiceId): ServiceId =
        getService(serviceId)
            .apply { deactivate() }
            .also { serviceRepo.save(it) }
            .id

    override fun delete(serviceId: ServiceId) {
        val service = getService(serviceId)
        engagementClient.deleteLikesAndReviewsForService(serviceId)
        serviceRepo.deleteById(service.id)
    }

    private fun getService(serviceId: ServiceId): Service = serviceRepo
        .findById(serviceId).orElseThrow { ServiceNotFoundException() }
}