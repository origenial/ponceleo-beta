# Module : ponceleo_proxy

## Summary

This module aims at being an entrypoint for the system, and to forward incoming requests/intents to the concerned modules.
It must be seen as an applicative proxy for scalable production use.

## Difference with ponceleo_container

In contrary to `ponceleo_container`, `ponceleo_proxy` do not import modules and run as a monolith !
Instead, every module should be run as a standalone and share a queryable contract/interface that `ponceleo_proxy` will use !
This aims at uncoupling deployment cycles between the many parts of the project.

There is much more technical complexity with `ponceleo_proxy` than with `ponceleo_container`, but the tradeoff will be to have the 
lowest possible coupling between products, teams, release-cycles, devs, etc. 