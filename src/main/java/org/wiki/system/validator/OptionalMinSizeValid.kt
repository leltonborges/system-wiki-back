package org.wiki.system.validator

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [OptionalMinSizeValidator::class])
annotation class OptionalMinSizeValid(
    val message: String = "If provided, resume must be at least the minimum length",
    val min: Int,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)
