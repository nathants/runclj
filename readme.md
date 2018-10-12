# cljs. putting the *scripting* back in clojurescript.

## what

some tooling around single file clojurescript *scripts* running on node or the browser.

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
- `git clone https://github.com/nathants/runclj`
- `mv runclj/bin/* /usr/local/bin`

## examples

- [hello.cljs](./examples/hello.cljs)
- [shell.cljs](./examples/shell.cljs)
- [server.cljs](./examples/server.cljs)
- [client.cljs](./examples/client.cljs) deployed: [live](https://nathants.com/client.cljs/)
- [macros.cljs](./examples/macros.cljs)

## usage

``` bash
runclj shell.cljs
```

if it has already been compiled, it can also be run as:

``` bash
node $(runclj-root shell.cljs)/dev.js # this resolves to: .lein/shell.cljs/dev.js
```

you declare clojure and npm dependencies with meta-data at the start of the file.

``` clojure
#!/usr/bin/env runclj
^{:runclj {:npm [[express "4.16.3"]]
           :lein [[prismatic/schema "1.1.3"]]}}
(ns main
  (:require [schema.core :as schema :include-macros true]))
```

`runclj` is converting a single clojurescript file into a temporary leiningen project in `.lein/`

`runclj` will recompile if the source has changed. use auto mode for faster iteration.

``` bash
auto=y runclj shell.cljs # auto compiler boots on the first call
runclj shell.cljs # fast compiles!
runclj-auto-stop shell.cljs # stop the background lein process
```

to force a lein project level rebuild:

- use an env variable `clean=y runclj shell.cljs`
- or `rm -rf $(runclj-root shell.cljs)`

you can use a repl as an alternative to just running the script with `runclj`. open `$(runclj-root shell.cljs)/project.clj` in your IDE and start your repl.

then call the function `(start-node-repl)`, or for client code uncomment the repl line in the client's `-main` function and call the function `(start-browser-repl)`.

### repl free workflow for client code

if you don't like using a repl, and can tolerate a slightly slower update that clears all state, the following works well:

`ls client.cljs | entr -r bash -c 'auto=y runclj client.cljs & while true; do curl localhost:8000 --connect-timeout 0.01 &>/dev/null && break; done && chromium http://localhost:8000 --new-window'`

### deployment

do a release

`runclj-release shell.cljs`

you can also ship a script as a tarball which include all node_modules

``` bash
runclj-tar shell.cljs > shell.tgz
scp shell.tgz ubuntu@remote:
ssh ubuntu@remote tar xf shell.tgz
```

run with node

```bash
ssh ubuntu@remote node .lein/shell.cljs/release.js
```

or runclj

```bash
ssh ubuntu@remote runclj shell.cljs
```

for client code, start a web server to serve `index.html` and `release.js`

```bash
cd .lein/client.cljs
python3 -m http.server
```
