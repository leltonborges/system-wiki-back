package org.wiki.system.exception

class StandardFiled(
    message: String,
    statusCode: Int,
    path: String,
    val fields: List<FieldMessage>,
    timestamp: Long = System.currentTimeMillis()
) : Standard(
    message, statusCode, path, timestamp
)