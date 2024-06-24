package org.pointyware.commonsense.core.common

/**
 * A logger than can be used by all targets to log messages and errors at different levels.
 */
open class CommonLogger: Logger<String> {

    override fun verbose(data: String, error: Throwable?) {
        println("VERBOSE: $data")
        error?.let {
            println("VERBOSE-ERROR: ${it.message}")
        }
    }

    override fun debug(data: String, error: Throwable?) {
        println("DEBUG: $data")
        error?.let {
            println("DEBUG-ERROR: ${it.message}")
        }
    }

    override fun info(data: String, error: Throwable?) {
        println("INFO: $data")
        error?.let {
            println("INFO-ERROR: ${it.message}")
        }
    }

    override fun warn(data: String, error: Throwable?) {
        println("WARN: $data")
        error?.let {
            println("WARN-ERROR: ${it.message}")
        }
    }

    override fun error(data: String, error: Throwable?) {
        println("ERROR: $data")
        error?.let {
            println("ERROR-ERROR: ${it.message}")
        }
    }
}

/**
 * A globally accessible logger that can be set to a specific implementation.
 */
var Log = CommonLogger()
