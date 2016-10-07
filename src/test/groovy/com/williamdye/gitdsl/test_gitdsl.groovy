package com.williamdye.gitdsl

import static com.williamdye.gitdsl.Script.git


git("/tmp/test1") {
    init()
    touch "README.md"
}