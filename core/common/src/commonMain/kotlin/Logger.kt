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
    fun v(data: D, error: Throwable? = null) = verbose(data, error)
    fun debug(data: D, error: Throwable? = null)
    fun d(data: D, error: Throwable? = null) = debug(data, error)
    fun info(data: D, error: Throwable? = null)
    fun i(data: D, error: Throwable? = null) = info(data, error)
    fun warn(data: D, error: Throwable? = null)
    fun w(data: D, error: Throwable? = null) = warn(data, error)
    fun error(data: D, error: Throwable? = null)
    fun e(data: D, error: Throwable? = null) = error(data, error)

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
