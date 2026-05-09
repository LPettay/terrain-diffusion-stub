package com.github.lpettay.tdstub;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

@Mod(TerrainDiffusionStub.MOD_ID)
public class TerrainDiffusionStub {
    public static final String MOD_ID = "terrain-diffusion-mc";

    public static final DeferredRegister<MapCodec<? extends BiomeSource>> BIOME_SOURCE_TYPES =
            DeferredRegister.create(BuiltInRegistries.BIOME_SOURCE, MOD_ID);
    public static final DeferredRegister<MapCodec<? extends DensityFunction>> DENSITY_FUNCTION_TYPES =
            DeferredRegister.create(BuiltInRegistries.DENSITY_FUNCTION_TYPE, MOD_ID);

    public static final Supplier<MapCodec<StubBiomeSource>> TERRAIN_DIFFUSION_BIOME_SOURCE =
            BIOME_SOURCE_TYPES.register("terrain_diffusion", () -> StubBiomeSource.CODEC);
    public static final Supplier<MapCodec<StubDensityFunction>> TERRAIN_DIFFUSION_DENSITY_FUNCTION =
            DENSITY_FUNCTION_TYPES.register("terrain_diffusion", () -> StubDensityFunction.CODEC);

    public TerrainDiffusionStub(IEventBus modBus) {
        BIOME_SOURCE_TYPES.register(modBus);
        DENSITY_FUNCTION_TYPES.register(modBus);
    }
}
