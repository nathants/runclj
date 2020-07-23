## what

tooling around single file clojurescript *scripts* running on node or the browser.

run a *.cljs file like it was a *.py file.

``` bash
runclj ./rotate_the_logs.cljs
```

## installation

get the dependencies:
- bash
- python3
- java
- leiningen
- node

then:
```bash
git clone https://github.com/nathants/runclj
mv runclj/bin/* runclj/bin/.lein /usr/local/bin
```

## examples

- [hello.cljs](https://github.com/nathants/runclj/blob/master/examples/hello.cljs)
- [shell.cljs](https://github.com/nathants/runclj/blob/master/examples/shell.cljs)
- [server.cljs](https://github.com/nathants/runclj/blob/master/examples/server.cljs)
- [client.cljs](https://github.com/nathants/runclj/blob/master/examples/client.cljs) deployed: [live](https://nathants.com/client.cljs/)
- [macros.cljs](https://github.com/nathants/runclj/blob/master/examples/macros.cljs)

## usage

``` bash
runclj shell.cljs
```

if it has already been compiled, it can also be run as:

``` bash
node $(runclj-root shell.cljs)/dev.js # this resolves to: .lein/shell.cljs/dev.js
```

declare clojure and npm dependencies with meta-data at the start of the file.

``` clojure
#!/usr/bin/env runclj
^{:runclj {:npm [[express "4.16.3"]]
           :lein [[prismatic/schema "1.1.3"]]}}
(ns main
  (:require [schema.core :as schema :include-macros true]))
```

`runclj` is converting a single clojurescript file into a temporary leiningen project in `.lein/`

`runclj` will recompile if the source has changed. start the auto compiler for faster iteration.

in terminal 1: `runclj-auto-start shell.cljs`

in terminal 2: `runclj shell.cljs`

to force a lein project level rebuild:

- use an env variable `clean=y runclj shell.cljs`
- or `rm -rf $(runclj-root shell.cljs)`

to use a repl as an alternative to just running the script with `runclj`.

open `$(runclj-root shell.cljs)/project.clj` in an IDE and start the clojure repl.

note: when using `cider` use `sesman-link-with-buffer` to associate the source file with the new repl.

then call the function `(start-node-repl)`, or for client code `(start-browser-repl)` which launches a new browser window in the default browser.

## repl free workflow for client code

an alternative workflow using [entr](http://www.entrproject.org/) instead of a repl:

in terminal 1: `runclj-auto-start $path`

in terminal 2: `ls shell.cljs | entr -r runclj $path`

to run a test suite after every compile:

`ls shell.cljs | callback="bash ./test.sh" entr -r runclj $path`

`entr` watches for changes in the source file and recompiles.

## deployment

do a release with advanced compilation

```bash
runclj-release shell.cljs
```

or ship a release as a tarball which include all node_modules

```bash
runclj-tar shell.cljs > shell.tgz
scp shell.tgz user@remote:
ssh user@remote tar xf shell.tgz
```

run with node

```bash
ssh user@remote node .lein/shell.cljs/release.js
```

or runclj

```bash
ssh user@remote runclj shell.cljs
```

for client code, start a web server to serve `index.html` and `release.js`

```bash
cd .lein/client.cljs
python3 -m http.server
```
