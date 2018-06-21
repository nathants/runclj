# cljs. putting the *scripting* back in clojurescript.

## what

some tooling around single file clojurescript *scripts* running on node or the browser.

run a *.cljs file like it was a *.py file.

``` bash
cljs ./rotate_the_logs.cljs
```

## installation

get the dependencies:
- bash
- python3.6
- java
- leiningen
- node

then:
- `git clone https://github.com/nathants/runclj`
- `mv runclj/bin/* /usr/local/bin`

## examples

- [hello.cljs](./examples/hello.cljs)
- [shell.cljs](./examples/shell.cljs)
- [server.cljs](./examples/server.cljs)
- [client.cljs](./examples/client.cljs) [live](https://nathants.com/client.cljs/)
- [macros.cljs](./examples/macros.cljs)

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

for client code, use `(start-browser-repl)` and uncomment the repl line in the clients `run` function.

alternately, open `$(cljs-root shell.cljs)/project.clj` in your IDE and start your repl some other way.

### deployment

do a release

`cljs-release shell.cljs`

you can also ship a script as a tarball which include all node_modules

``` bash
cljs-tar shell.cljs > shell.tgz
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
