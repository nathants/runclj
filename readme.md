# runclj

## what

tooling around single file clojurescript programs running on node or the browser.

``` bash
runclj ./rotate_the_logs.cljs
```

## how

transparently converts a single clojurescript file into a temporary shadow-cljs project in `.shadow-cljs/`.

## installation

get the dependencies:
- bash
- python3
- java
- node
- npm

then:
```bash
git clone https://github.com/nathants/runclj
sudo mv runclj/bin/* runclj/bin/.shadow-cljs /usr/local/bin
```

## examples

- [hello.cljs](https://github.com/nathants/runclj/blob/master/examples/hello.cljs) - node hello world
- [shell.cljs](https://github.com/nathants/runclj/blob/master/examples/shell.cljs) - node with subprocess, user prompts, and core.async
- [server.cljs](https://github.com/nathants/runclj/blob/master/examples/server.cljs) - node express server
- [client.cljs](https://github.com/nathants/runclj/blob/master/examples/client.cljs) - client app with material ui ([demo](https://nathants.com/client.cljs/))
- [macros.cljs](https://github.com/nathants/runclj/blob/master/examples/macros.cljs) - node hello world with macros

## usage

``` bash
runclj program.cljs
```

declare clojure and npm dependencies with meta-data at the start of the file.

``` clojure
#!/usr/bin/env runclj
^{:runclj {:npm [[express "4.16.3"]]
           :deps [[prismatic/schema "1.1.3"]]}}
(ns main
  (:require [schema.core :as schema :include-macros true]))
```

run the program.

`runclj program.cljs`

## browser dev workflow

- `runclj-watch examples/client.cljs`

- open browser to `localhost:8000`

- edit `client.cljs` and browser auto reloads

- optionally connect to repl on `localhost:3333`

## server dev workflow

`runclj examples/server.cljs`

## deployment

```bash
runclj-tar program.cljs | ssh server tar xf -
```

then either install `runclj` and run normally `runclj program.cljs`

or run manually:

- browser: `cd .shadow-cljs/program.cljs/public && python3 -m http.server`

- server: `cd .shadow-cljs/program.cljs/ && node public/main.js`

## outgrowing runclj

if your project grows too large for a single file, copy the generated shadow-cljs project and continue working on that directly.

- `runclj program.cljs`

- `cp -r .shadow-cljs/program.cljs program`

alternatively lift and shift to a project template like [aws-gocljs](https://github.com/nathants/aws-gocljs).
