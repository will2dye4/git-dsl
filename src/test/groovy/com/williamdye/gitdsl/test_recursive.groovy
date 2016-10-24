package com.williamdye.gitdsl

import static com.williamdye.gitdsl.Script.git


git(args[0]) {
    clone "https://github.com/will2dye4/git-dsl.git"
    def classpath = "src/main/groovy"
    def script = "src/test/groovy/com/williamdye/gitdsl/test_recursive.groovy"
    execute "groovy -cp $classpath $script $directory"
}
