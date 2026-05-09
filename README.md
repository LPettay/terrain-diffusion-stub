# Terrain Diffusion Loader Stub

A minimal NeoForge 1.21.1 mod that registers the `terrain-diffusion-mc:terrain_diffusion`
BiomeSource and DensityFunction codec types as no-ops, plus the custom biomes and
dimension types from upstream Terrain Diffusion. This lets a Minecraft world
pre-generated on **Fabric** Terrain Diffusion load on a **NeoForge** server.

## Why this exists

[Terrain Diffusion (xandergos/terrain-diffusion-mc)](https://github.com/xandergos/terrain-diffusion-mc)
is Fabric-only. Pillowcreate (LPettay/pillowcreate) runs Create: Aeronautics on NeoForge.
The two cannot coexist natively today.

The full NeoForge port is tracked at
[LPettay/terrain-diffusion-mc#1](https://github.com/LPettay/terrain-diffusion-mc/issues/1).

This stub is the quick path: pre-generate the world on Fabric, copy the world to
NeoForge, install this stub so codec deserialization succeeds, and clamp worldborder
to the pre-genned area so nothing ever calls into the worldgen code.

## Caveats

- **The world cannot expand.** Set worldborder to the pre-genned area before letting
  players in. If a chunk outside the pre-gen is loaded for any reason, the stub
  generators run and you'll get plains-on-flat-water everywhere new — visually broken.
- **Some structure / spawn placement APIs may behave oddly** because the stub's
  `getNoiseBiome()` always returns plains. In practice, with a worldborder, this only
  matters at first spawn and is harmless.
- This is **not** a port of Terrain Diffusion. It does not generate diffusion-based
  terrain; it only loads worlds that were already generated.

## Build

Requires JDK 21.

```bash
./gradlew build
```

Output: `build/libs/terrain-diffusion-stub-0.1.0.jar`

Drop into your NeoForge server's `mods/` folder.
