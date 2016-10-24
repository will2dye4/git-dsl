package com.williamdye.gitdsl

import static com.williamdye.gitdsl.Script.git


git("/tmp/test1") {
    init()
    def readme = "README.md"
    touch readme
    status()
    add files: [readme], verbose: true
    status()
    commit "Adding empty $readme", verbose: true
    status()
}


git("/tmp/test2") {
    clone "https://github.com/will2dye4/git-dsl.git"
    status()
}
