# TenSura More Features

`TenSura More Features` (`tensura_mf`) is a Minecraft 1.21.1 mod that expands [Tensura: Reincarnated] with additional magical blocks, recipes, items, and entities. The project is built with Architectury and contains shared code together with separate Fabric and NeoForge implementations.

## Features

- a magical energy network with generators, pipes, and storage;
- a magical incubator and the custom `magic_incubation` recipe type;
- magical materials, blocks, and other registered items;
- generator blocks based on different building materials;
- additional menus, client screens, and magical energy displays;
- new monsters, attributes, spawning rules, and spawn eggs;
- data generation for recipes, loot tables, tags, models, blockstates, and translations;
- item component support, including preserving special equipment data while processing items in the incubator.

## Requirements

- JDK 21;
- Minecraft 1.21.1;
- Gradle 8.8;
- Architectury API 13.0.8;
- Tensura: Reincarnated;
- ManasCore;
- GeckoLib, SmartBrainLib, and TerraBlender.

Development dependency versions are listed in [`gradle.properties`](gradle.properties). Running the game also requires the appropriate loader: Fabric or NeoForge.

## Project Structure

```text
common/      Shared code and resources for all platforms
fabric/      Fabric implementation and entrypoints
neoforge/    NeoForge implementation, registrations, and data generation
libs/        Local libraries used during the build
run/         Development environment working directories
```

Generated resources for the common module are stored in `common/src/generated/resources`.

## Setup

1. Install JDK 21 and verify that `java -version` reports version 21.
2. Clone the repository and open it as a Gradle project.
3. Make sure the local libraries in `libs/` are present and dependencies can be downloaded.
4. Use Gradle 8.8. This checkout contains the wrapper configuration in `gradle/wrapper`, but the `gradlew` and `gradlew.bat` scripts are not included in the repository.

## Main Commands

### Compilation and Build

```bash
gradle common:compileJava neoforge:compileJava
gradle build
```

For Fabric, use:

```bash
gradle fabric:compileJava
```

### Running the Client

```bash
gradle neoforge:runClient
gradle fabric:runClient
```

On Windows, use the same tasks with the installed Gradle command.

### Data Generation

NeoForge data generation creates recipes and other resources in the common generated resources directory:

```bash
gradle neoforge:runData
```

After changing recipe providers, check the generated JSON files in `common/src/generated/resources` and do not commit them if they are only local run output.

## Development

- Put platform-independent logic in `common`.
- Put code that uses only Fabric or NeoForge APIs in the corresponding platform module.
- Add new registrations through the project's existing registry classes.
- Use NeoForge data providers for recipes and verify that recipe identifiers are unique.
- After changing Java code, compile the required platform; after changing data providers, run `neoforge:runData`.

The mod's main namespace is `tensura_mf`, and the common module's main class is `com.github.skillfi.tensura_mf.TensuraMf`.

## License

The terms of use are defined in [`LICENSE.txt`](LICENSE.txt).

[Tensura: Reincarnated]: https://www.curseforge.com/minecraft/mc-mods/tensura-reincarnated
