package org.wiki.system.validator

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ObjectIdValidator::class])
annotation class ValidId(
    val message: String = "ID invalid",
    val size: Int,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)
