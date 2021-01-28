# Ponceleo landing's backend

## Development

`clj -A:env/dev:env/local --repl`

## Build

Run `clj -X:project/uberjar`

## Run

``` bash
java -Dconfig="./env/secret.config.edn" \
-jar ponceleo.standlone.jar \
clojure.main -m ponceleo.landing.backend.core`
