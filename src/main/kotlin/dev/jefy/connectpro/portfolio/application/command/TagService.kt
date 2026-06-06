package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.portfolio.domain.model.Tag
import dev.jefy.connectpro.portfolio.domain.repository.TagRepository
import org.springframework.stereotype.Service

/**
 * @author  Jôph Yamba
 */
@Service
class TagService(val tagRepo: TagRepository) {
    /*fun resolveTags(stringTags: Set<String>): Set<Tag> {

        val existingTags = tagRepo.findByNameIn(stringTags.map { it.lowercase() }.toSet())
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
    }*/
    fun resolveTags(stringTags: Set<String>): Set<Tag> {
        if (stringTags.isEmpty()) return emptySet()

        // Normalise les noms en lowercase avant la requête
        val normalizedNames = stringTags.map { it.lowercase() }.toSet()

        // Recherche case insensitive
        val existingTags = tagRepo.findByNameInIgnoreCase(normalizedNames)

        // Map par nom normalisé
        val tagsByNormalizedName = existingTags.associateBy { it.name.lowercase() }.toMutableMap()

        val newTags = mutableSetOf<Tag>()

        for (originalName in stringTags) {
            val normalized = originalName.lowercase()

            if (!tagsByNormalizedName.containsKey(normalized)) {
                val tag = Tag(name = originalName) 
                newTags.add(tag)
                tagsByNormalizedName[normalized] = tag
            }
        }

        if (newTags.isNotEmpty()) {
            tagRepo.saveAll(newTags)
        }

        return tagsByNormalizedName.values.toSet()
    }
}