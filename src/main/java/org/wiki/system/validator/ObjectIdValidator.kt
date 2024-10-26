package org.wiki.system.validator

import jakarta.enterprise.context.ApplicationScoped
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.bson.types.ObjectId

@ApplicationScoped
class ObjectIdValidator : ConstraintValidator<org.wiki.system.validator.IdValid, String> {
    private var size: Int = 0
    override fun initialize(constraint: org.wiki.system.validator.IdValid) {
        this.size = constraint.size
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value != null && value.length >= this.size && ObjectId.isValid(value)
    }
}