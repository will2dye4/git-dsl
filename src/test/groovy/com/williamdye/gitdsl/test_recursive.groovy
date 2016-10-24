package com.williamdye.gitdsl

import static com.williamdye.gitdsl.Script.git


git(args[0]) {
    println "running, directory is $directory"
    clone "https://github.com/will2dye4/git-dsl.git"
    Thread.sleep 1000
    def classpath = "src/main/groovy"
    def script = "src/test/groovy/com/williamdye/gitdsl/test_recursive.groovy"
    execute "groovy -cp $classpath $script $directory"
}
