package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.portfolio.domain.model.Tag
import dev.jefy.connectpro.portfolio.domain.repository.TagRepository
import org.springframework.stereotype.Service

/**
 * @author  Jôph Yamba
 */
@Service
class TagService(val tagRepo: TagRepository) {
    fun resolveTags(stringTags: Set<String>): Set<Tag> {

        val existingTags = tagRepo.findByNameIn(stringTags.toMutableSet())
        val tagsByName = existingTags.associateBy { it.name }.toMutableMap()
        val newTags = mutableSetOf<Tag>()
        for (name in stringTags) {
            if (!tagsByName.containsKey(name)) {
                val tag = Tag(name = name)
                newTags.add(tag)
                tagsByName[name] = tag
            }
        }

        if (newTags.isNotEmpty()) {
            tagRepo.saveAll(newTags)
        }
        return tagsByName.values.toSet()
    }
}