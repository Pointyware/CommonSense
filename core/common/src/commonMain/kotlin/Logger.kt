package org.pointyware.commonsense.core.common

/**
 * A logger that can be used to log messages and errors at different levels.
 */
interface Logger<D> {
    enum class Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }
    fun verbose(data: D, error: Throwable? = null)
    fun debug(data: D, error: Throwable? = null)
    fun info(data: D, error: Throwable? = null)
    fun warn(data: D, error: Throwable? = null)
    fun error(data: D, error: Throwable? = null)

    fun log(level: Level, data: D, error: Throwable? = null) {
        when (level) {
            Level.VERBOSE -> verbose(data, error)
            Level.DEBUG -> debug(data, error)
            Level.INFO -> info(data, error)
            Level.WARN -> warn(data, error)
            Level.ERROR -> error(data, error)
        }
    }
}
