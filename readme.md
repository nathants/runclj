# runclj

## what

tooling around single file clojurescript programs running on node or the browser.

``` bash
runclj ./rotate_the_logs.cljs
```

## how

transparently converts a single clojurescript file into a temporary leiningen project in `.lein/`.

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
sudo mv runclj/bin/* runclj/bin/.lein /usr/local/bin
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
           :lein [[prismatic/schema "1.1.3"]]}}
(ns main
  (:require [schema.core :as schema :include-macros true]))
```

run the program.

`runclj program.cljs`

## repl workflow

- open `.lein/program.cljs/project.clj` in an IDE and start a clojure repl.

- call the function `(start-node-repl)` or `(start-browser-repl)` to begin the repl session.

- when using [cider](https://docs.cider.mx/) use `M-x sesman-link-with-buffer` to associate the source file with the new repl.

- for other IDEs like [vim](https://github.com/tpope/vim-fireplace), [vscode](https://marketplace.visualstudio.com/items?itemName=betterthantomorrow.calva) or [intellij](https://cursive-ide.com/), associate the source file with the repl session in the recommended way.

## auto-reloading workflow

an alternative workflow using [entr](http://www.entrproject.org/) instead of a repl:

- in terminal 1: `runclj-auto-start program.cljs`

- in terminal 2: `ls program.cljs | entr -r runclj program.cljs`

## auto-testing workflow

`ls program.cljs | callback="bash ./test.sh" entr -r runclj program.cljs`

## client deployment

ship a release with advanced compilation.

```bash
runclj-release program.cljs
scp index.html     user@remote:/var/www/html
scp release.js     user@remote:/var/www/html
scp release.js.map user@remote:/var/www/html
```

## server deployment

ship a release as a tarball including node_modules.

```bash
runclj-tar program.cljs > release.tgz
scp release.tgz user@remote:
ssh user@remote tar xf release.tgz
```

run with node

```bash
ssh user@remote node .lein/program.cljs/release.js
```

or runclj

```bash
ssh user@remote runclj program.cljs
```

## outgrowing runclj

if your project grows too large for a single file, copy the generated leiningen project and continue working on that directly.

- `runclj program.cljs`

- `cp -r .lein/program.cljs program`

- `tree program`

  ```
  program
  ├── project.clj
  └── src
      ├── program.clj
      ├── program.cljs
      ├── runner.cljs
      └── repl.clj

  ```
