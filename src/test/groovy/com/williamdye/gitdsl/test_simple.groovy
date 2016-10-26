package com.williamdye.gitdsl

import static com.williamdye.gitdsl.Script.git


git("/tmp/gitdsl_readme_test") {
    // initialize an empty repository
    init()

    // create an empty readme
    def readme = "README.md"
    touch readme

    // git status - expect to see un-tracked readme
    status()

    // add the readme to git
    add files: [readme], verbose: true

    // git status again - expect to see readme staged for commit
    status()

    // commit readme
    commit "Adding empty $readme", verbose: true

    // git status a final time - expect to see clean working directory
    status()
}


git("/tmp/gitdsl_clone_test") {
    // clone this repository from GitHub
    clone "https://github.com/will2dye4/git-dsl.git"

    // run git status to verify it worked
    status()
}
