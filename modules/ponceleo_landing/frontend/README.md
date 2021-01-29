# Module `ponceleo.landing.frontend`

## Summary

This module aims at managing the frontend of Ponceleo's SPA, and only the
frontend.
It also depends on the `ponceleo.common` module that gathers inter-module
logic such as specs shared between both frontend and backend.

## Detailed description

The frontend is a CLJS project and only contains *.clj files for useful
macros, build and dev utilities and *.cljs files that will be compiled to Js +
some *.js file (the least possible).

The `deps.edn` files contains mainly cljs dependencies. These dependencies are
added regardless of the environment in which they are mainly used since when
the CLJS is built to js, the code is tree-shaken depending on the build you
configure.

The frontend calls a RESTful API for backend logic. This API is developped in
the `ponceleo.server` module.

## Stack notes

This module relies on `shadow-cljs` for the development and the builds to
javascript. The `ponceleo.spa` namespace defines an init script to be used in
dev mode (i.e with `shadow-cljs watch :dev`) whereas the `ponceleo.next
` namespace defines utilities to transpile the SPA to the next-js file
-routing format, which then need to be compiled with `next build` to get
production ready.

## Getting started

* To start the project in dev mode : `npx shadow-cljs watch :app` and go to
"localhost:3449/"
* To build the project to next js
    - `npx shadow-cljs compile :next`
    - `npx next build`
    - `npx next start` OR `next export`

## Known issues

Impossible to use `npx next dev` for the moment. There is an incompatibility
with the way `shadow-cljs`'s compiler work and it throws a "Namespace `goog.math
.Long` already declared."
