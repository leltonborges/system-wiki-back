package org.wiki.system.exception

open class Standard(
    val message: String,
    val statusCode: Int,
    val path: String,
    val timestamp: Long = System.currentTimeMillis()
)