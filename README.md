# eMoflon::IBeX TGG Minimal Working Example

This is a Minimal Working Example (MWE) for the eMoflon::IBeX implementation of Triple Graph Grammars (TGGs).

You need an up-to-date version of the Eclipse Modeling Framework with eMoflon::IBeX and all related plug-ins installed.
A pre-built Eclipse distribution can be found [here](https://github.com/eMoflon/emoflon-ibex-eclipse-build/releases).


## Structure

This repository contains three projects:
- `MetamodelA` is the source metamodel.
- `MetamodelB` is the target metamodel.
- `TGGProject` is the TGG project that can be used to synchronize the two metamodels.

Please notice:
There is no actual TGG implementation in the project, just the basic project structure and files are present.
However, the set of projects can be used to test the IBeX-TGG installation and/or be used as a starting point for your own TGG implementation.


## License

See [LICENSE](./LICENSE).
