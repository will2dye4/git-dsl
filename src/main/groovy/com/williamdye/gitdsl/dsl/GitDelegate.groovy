package com.williamdye.gitdsl.dsl

import java.util.regex.Matcher

/**
 * Closure delegate for the git method.
 */
class GitDelegate {

    protected static final String dry_run = "dry_run"
    protected static final String files = "files"
    protected static final String force = "force"
    protected static final String verbose = "verbose"

    protected static final Map<String, String> options = [
            dry_run: "--dry-run",
            force: "--force",
            verbose: "--verbose"
    ]

    protected String directory

    GitDelegate(String directory) {
        this.directory = directory
    }

    void init() {
        executeGit "init"
    }

    void clone(String repository) {
        Matcher matcher = repository =~ /^.+\/([\w-]+)\.git\/?$/
        if (!matcher.find()) {
            throw new IllegalArgumentException("repository to clone must end with '<name>.git'")
        }
        executeGit "clone $repository"
        directory = "$directory/${matcher.group(1)}"
    }

    void add(String file) {
        add files: [file]
    }

    void add(Map<String, Object> params = [:]) {
        if (!params[files]) {
            throw new IllegalArgumentException("add must specify one or more files")
        }
        executeGit "${buildCommand('add', [dry_run, force, verbose], params)} -- ${(params[files] as List).join(' ')}"
    }

    void commit(Map<String, Object> params = [:], String message) {
        if (!message?.trim()) {
            throw new IllegalArgumentException("commit message must not be empty")
        }
        executeGit buildCommand("commit -F -", [dry_run, verbose], params), message
    }

    void status() {
        executeGit "status"
    }

    void touch(String file) {
        execute "touch $file"
    }

    protected static String buildCommand(String command, List<String> allowedOptions, Map<String, Object> params) {
        StringBuilder builder = new StringBuilder(command)
        allowedOptions.findAll { params[it] }.each { option ->
            builder.append(" ").append(options[option])
        }
        builder.toString()
    }

    protected Process execute(String command, String stdin = null) {
        show "${command}${stdin ? ' (stdin = "' + stdin + '")' : ''}"
        Process process = command.execute([], new File(directory))
        if (stdin) {
            new BufferedWriter(new OutputStreamWriter(process.out)).with {
                write(stdin)
                flush()
                close()
            }
        }
        process.waitFor()
        if (process.exitValue() != 0) {
            println "[ERROR] subprocess returned ${process.exitValue()}"
            BufferedReader reader = process.err.newReader()
            println reader.readLines().join("\n")
            reader.close()
        }
        String output = process.text?.trim()
        if (output) {
            println output
        }
        process
    }

    protected Process executeGit(String command, String stdin = null) {
        execute "git $command", stdin
    }

    protected static void show(String message) {
        println "\n\n\u001B[1m-git-dsl-> ${message}\u001B[0m"
    }

}
