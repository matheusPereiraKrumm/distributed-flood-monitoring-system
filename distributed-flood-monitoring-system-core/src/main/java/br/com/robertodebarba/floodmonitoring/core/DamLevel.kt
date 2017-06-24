package br.com.robertodebarba.floodmonitoring.core

import com.fasterxml.jackson.annotation.JsonInclude
import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id
import org.mongodb.morphia.annotations.Reference

import java.time.ZonedDateTime

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Entity("damLevels")
class DamLevel {

    @Id
    val id: ObjectId? = null
    private val time: ZonedDateTime? = null
    private val level: Float = 0.toFloat()
    @Reference
    private val dam: Dam? = null

}
