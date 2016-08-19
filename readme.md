# cljs. putting the *scripting* back in clojurescript.

## what

some tooling around single file clojurescript *scripts* running on node. run a *.cljs file like it was a *.py file.

``` bash
cljs ./rotate_the_logs.cljs
```

## installation

depends on:
- bash
- python2.7
  - pip install edn_format
- java
- lein
- node

a demo [setup](https://github.com/nathants/bootstraps/blob/8ba6a70/scripts/cljs.sh) on ubuntu 16.04 via ec2 and [py-aws](https://github.com/nathants/py-aws/tree/891fa578aefba9c0a8675b07bb138ab44682a0fe). also works on macos but setup is more manual, see above dependencies.

``` bash
ec2 new cljs-testbox --ami xenial --type m4.large --spot 0.1 --login # py-aws
git clone https://github.com/nathants/bootstraps
git checkout 8ba6a70
cd boostraps/scripts
bash cljs.sh
```

## usage

``` bash
cljs ./cljs/examples/shell.cljs
```

if it has already been compiled, it can also be run as:

``` bash
node $(cljs-root ./cljs/examples/shell.cljs)/run.js
```

you declare you clojure and npm dependencies with comments in the namespace block.

``` clojure
(ns main
  #_(:npm [express "4.14.0"])
  #_(:lein [prismatic/schema "1.1.3"])
  (:require [schema.core :as schema :include-macros true]))
```

behind the scenes, `cljs` is converting a single clojurescript file into a leiningen project in /tmp, based on a hash of the file path.

`cljs` will recompile if the source has changed. you can also start the clojurescript compiler in watch mode for faster compiles.

``` bash
cljs-auto-start ./cljs/examples/shell.cljs
# recompiling is now much faster
cljs ./cljs/examples/shell.cljs
cljs ./cljs/examples/shell.cljs
cljs ./cljs/examples/shell.cljs
cljs-auto-stop ./cljs/examples/shell.cljs
```

to force a lein project level rebuild, use an env variable `clean=true cljs ...` or `clean=true cljs-auto-start ...`.

you can use a repl as an alternative/addition to just running the script with `cljs`. start the repl with `cljs-repl ./cljs/examples/shell.cljs`, then jack-in to the nrepl on the port it printed, then call the function `(start-node-repl)`.

note: that the blocking `sh/run` function must be called inside of a fiber, so in a repl session do something like:

```clojure
(sh/run-fiber
  #(do (sh/run "whoami")
       ...))
```
