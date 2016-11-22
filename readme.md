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
- java
- lein
- node

a demo [setup](https://github.com/nathants/bootstraps/blob/730cc1d/scripts/cljs.sh) on ubuntu 16.04 via [ec2](https://github.com/nathants/py-aws/tree/891fa578aefba9c0a8675b07bb138ab44682a0fe). also works on macos but setup is more manual, see above dependencies.

``` bash
ec2 new cljs-testbox --ami xenial --type m4.large --spot 1.0 --login # py-aws
git clone https://github.com/nathants/bootstraps
git checkout 730cc1d
cd boostraps/scripts
bash runclj.sh
```

## usage

``` bash
cljs ./cljs/examples/shell.cljs
```

if it has already been compiled, it can also be run as:

``` bash
node $(cljs-root ./cljs/examples/shell.cljs)/dev.js
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
# recompiling is now much faster
auto=y cljs ./cljs/examples/shell.cljs # auto compiler boots on the first call
auto=y cljs ./cljs/examples/shell.cljs # fast compiles!
auto=y cljs ./cljs/examples/shell.cljs # fast compiles!
cljs-auto-stop ./cljs/examples/shell.cljs
```

to force a lein project level rebuild, use an env variable `clean=true cljs ...`.

you can use a repl as an alternative/addition to just running the script with `cljs`. start the repl with `cljs-repl ./cljs/examples/shell.cljs`, then jack-in to the nrepl on the port it printed, then call the function `(start-node-repl)`.

### deployment

you can ship a compiled script with tar. the os and node versions must match in both locations.

``` bash
cljs-tar examples/shell.cljs > shell.tgz
scp shell.tgz ubuntu@remote:
ssh ubuntu@remote
tar xf shell.tgz

node .lein/shell.cljs/release.js # invoke with node only
cljs shell.cljs # invoke with cljs
```
