package org.wiki.system.validator

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ObjectIdValidator::class])
annotation class IdValid(
    val message: String = "Invalid expected size",
    val size: Int = 24,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)
