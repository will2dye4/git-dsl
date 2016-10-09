package com.williamdye.gitdsl

import static com.williamdye.gitdsl.Script.git


git("/tmp/test1") {
    init()
    def readme = "README.md"
    touch readme
    add files: [readme], verbose: true
    commit "Adding empty $readme", verbose: true
    status()
}