# cljs. putting the *scripting* back in clojurescript.

## what

some tooling around single file clojurescript *scripts* running on node or the browser.

run a *.cljs file like it was a *.py file.

``` bash
cljs ./rotate_the_logs.cljs
```

## installation

depends on:
- bash
- python3.6
- java
- lein
- node

a demo [setup](https://github.com/nathants/bootstraps/blob/aa1c119d0182784199e425bad810ee53fb97ca64/ubuntu/scripts/runclj.sh/) on ubuntu via [ec2](https://github.com/nathants/py-aws/tree/7f436794e3cfaeeb6da3e85d457307e2eef442c2). also works on macos but setup is more manual, see above dependencies.

``` bash
ec2 new cljs-testbox --ami xenial --type m4.large --spot 1.0 --login # py-aws
git clone https://github.com/nathants/bootstraps
git checkout 730cc1d
cd boostraps/scripts
bash runclj.sh
cd runclj/examples
```

## examples

- [client.cljs](./examples/client.cljs)
- [hello.cljs](./examples/hello.cljs)
- [macros.cljs](./examples/macros.cljs)
- [server.cljs](./examples/server.cljs)
- [shell.cljs](./examples/shell.cljs)

## usage

``` bash
cljs shell.cljs
```

if it has already been compiled, it can also be run as:

``` bash
node $(cljs-root shell.cljs)/dev.js # this resolves to: .lein/shell.cljs/dev.js
```

you declare you clojure and npm dependencies with comments in the namespace block.

``` clojure
(ns main
  #_(:npm [express "4.16.3"])
  #_(:lein [prismatic/schema "1.1.3"])
  (:require [schema.core :as schema :include-macros true]))
```

`cljs` is converting a single clojurescript file into a temporary leiningen project in `.lein/`.

`cljs` will recompile if the source has changed. use auto mode for faster iteration.

``` bash
auto=y cljs shell.cljs # auto compiler boots on the first call
cljs shell.cljs # fast compiles!
cljs-auto-stop shell.cljs # stop the background lein process
```

to force a lein project level rebuild:

- use an env variable `clean=y cljs shell.cljs`
- or `rm -rf $(cljs-root shell.cljs)`.

you can use a repl as an alternative to just running the script with `cljs`. start the repl with `cljs-repl shell.cljs`, jack-in to the nrepl on the port it printed, then call the function `(start-node-repl)`.

alternately, open `$(cljs-root shell.cljs)/project.clj` in your IDE and start your repl some other way.

### deployment

you can ship a script as a tarball which include all node_modules

``` bash
cljs-tar examples/shell.cljs > shell.tgz
scp shell.tgz ubuntu@remote:
ssh ubuntu@remote tar xf shell.tgz
```

run with node

```bash
ssh ubuntu@remote node .lein/shell.cljs/release.js
```

or cljs

```bash
ssh ubuntu@remote cljs shell.cljs
```
