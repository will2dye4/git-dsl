package com.williamdye.gitdsl

import com.williamdye.gitdsl.dsl.GitDelegate

/** Static methods to import when using the Git DSL */
class Script {

    static void git(String directory, Closure closure) {
        if (new File(directory).mkdirs()) {
            debug("Created directory $directory")
        }
        GitDelegate delegate = new GitDelegate(directory)
        closure.delegate = delegate
        closure.call()
    }

    protected static void debug(String message) {
        println("[DEBUG] $message")
    }

}