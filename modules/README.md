# 'Modules' folder description

## Summary

This folder aims at keeping the code modular. The idea is (for simplicity's
sake) is to keep every shippable and unstable artifacts in the same directory.

## Benefits 

By using a modular architecture, and a mono-repository, We will
enable code reuse, atomic commits, and it won't prevent us from shipping modules into 'git subtrees' or 'git submodules'.

## Organisation

For simplifiing the 'mono-repository' management, please do not create a folder
hierarchy for the modules. Every module should be kept in the same directory level.

Instead of creating a lib under the folder 
'/modules/prefix1/prefix2/awesome_lib'.
 Please prefer : '/modules/prefix1_prefix2_awesome-lib'.

Please, make everything possible to **avoid tight coupling** The idea is that
each subtree should sustain itself and be shippable.

## Caveats

Note that the file `deps.edn` is not exportable as is  because of `:local/root`coordinates it may contain. Those local references should be transformed to public references (maven or git) when the subtree is exported.
 
For the moment, it is harder to deal with multi-level path. Refactoring ideas  are welcome when `tools.deps` will provide  new features. 

