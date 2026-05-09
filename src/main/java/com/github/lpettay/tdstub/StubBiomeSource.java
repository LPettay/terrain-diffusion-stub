package com.github.lpettay.tdstub;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.resources.RegistryOps;
import java.util.List;
import java.util.stream.Stream;

/**
 * No-op stand-in for terrain-diffusion-mc:terrain_diffusion BiomeSource on NeoForge.
 *
 * Worldborder is expected to confine players to pre-genned chunks; getNoiseBiome
 * should never be invoked for new chunk generation. This class exists so level.dat
 * codec deserialization succeeds and the dimension loads. If invoked, returns plains.
 */
public class StubBiomeSource extends BiomeSource {
    public static final MapCodec<StubBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RegistryOps.retrieveGetter(Registries.BIOME)
            ).apply(instance, StubBiomeSource::new));

    private static final ResourceKey<Biome> FOREST_SPARSE =
            ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("terrain-diffusion-mc", "forest_sparse"));
    private static final ResourceKey<Biome> TAIGA_SPARSE =
            ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("terrain-diffusion-mc", "taiga_sparse"));
    private static final ResourceKey<Biome> SNOWY_TAIGA_SPARSE =
            ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("terrain-diffusion-mc", "snowy_taiga_sparse"));

    private static final List<ResourceKey<Biome>> POSSIBLE_BIOMES = List.of(
            Biomes.PLAINS, Biomes.SNOWY_PLAINS, Biomes.DESERT, Biomes.SWAMP, Biomes.FOREST,
            Biomes.TAIGA, Biomes.SNOWY_TAIGA, Biomes.SAVANNA, Biomes.WINDSWEPT_HILLS, Biomes.JUNGLE,
            Biomes.BADLANDS, Biomes.MEADOW, Biomes.GROVE, Biomes.SNOWY_SLOPES, Biomes.FROZEN_PEAKS,
            Biomes.STONY_PEAKS, Biomes.WARM_OCEAN, Biomes.OCEAN, Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN,
            FOREST_SPARSE, TAIGA_SPARSE, SNOWY_TAIGA_SPARSE
    );

    private final HolderGetter<Biome> biomeLookup;
    private final Holder<Biome> defaultBiome;

    public StubBiomeSource(HolderGetter<Biome> biomeLookup) {
        this.biomeLookup = biomeLookup;
        this.defaultBiome = biomeLookup.getOrThrow(Biomes.PLAINS);
    }

    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return POSSIBLE_BIOMES.stream().map(biomeLookup::getOrThrow);
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        return defaultBiome;
    }
}
