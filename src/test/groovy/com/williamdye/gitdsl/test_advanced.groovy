package com.williamdye.gitdsl

import static com.williamdye.gitdsl.Script.git


git("/tmp/gitdsl_advanced_test") {
    // initialize an empty repository
    init()

    // create some empty files
    ["foo.txt", "foo.groovy", "bar.json", "baz.groovy", "groovy.py"].each { filename ->
        touch filename
    }

    // find all the .groovy files reported by `git status`
    def groovyFiles = status().split("\n").findAll { it.startsWith("\t") && it.endsWith(".groovy") }

    // add the groovy files to git
    add files: groovyFiles, verbose: true

    // run `git status` again to see if the files were added
    status()
}
