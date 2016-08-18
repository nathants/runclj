# cljs. putting the *scripting* back in clojurescript.

## what

some tooling around single file clojurescript *scripts* running on node. run a *.cljs file like it was a *.py file.

``` bash
cljs ./rotate_the_logs.cljs
```

## install

[setup](https://github.com/nathants/bootstraps/blob/master/scripts/cljs.sh) on ubuntu 16.04.

``` bash
git clone https://github.com/nathants/bootstraps
cd boostraps/scripts
bash cljs.sh
```

## use

``` bash
cljs ./cljs/examples/shell.cljs
```

you declare you clojure and npm dependencies with comments in the namespace block.

``` clojure
(ns main
  #_(:npm [express "4.14.0"])
  #_(:lein [prismatic/schema "1.1.3"])
  (:require [schema.core :as schema :include-macros true]))
```

behind the scenes, `cljs` is converting a single clojurescript file into a leiningen project in /tmp, based on a hash of the file path.

`cljs` will recompile if the source has changed. you can also start the clojurescript compiler in with mode.

``` bash
cljs-auto-start ./cljs/examples/shell.cljs # start the compiler daemon
cljs ./cljs/examples/shell.cljs # recompiling is now much faster
cljs ./cljs/examples/shell.cljs # recompiling is now much faster
cljs ./cljs/examples/shell.cljs # recompiling is now much faster
cljs-auto-stop ./cljs/examples/shell.cljs # start the compiler daemon
```

if you can use a repl as an alternative/addition to just running the script with `cljs`. start the repl with `cljs-repl ./cljs/examples/shell.cljs`, then jack-in to the nrepl on the port it printed, then call the function `(start-node-repl)`.

note: that the blocking `sh/run` function must be called inside of a fiber, so in a repl session do something like `(sh/run-fiber #(do ...))`.
