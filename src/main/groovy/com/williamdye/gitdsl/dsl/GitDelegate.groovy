package com.williamdye.gitdsl.dsl

/**
 * Closure delegate for the git method.
 */
class GitDelegate {

    protected String directory

    GitDelegate(String directory) {
        this.directory = directory
    }

    void init() {
        executeGit "init"
    }

    void touch(String file) {
        execute "touch $file"
    }

    protected Process execute(String command) {
        show command
        Process process = command.execute([], new File(directory))
        process.waitFor()
        String output = process.text?.trim()
        if (output) {
            println output
        }
        process
    }

    protected Process executeGit(String command) {
        execute "git $command"
    }

    protected static void show(String message) {
        println "-git-dsl-> $message"
    }

}
