package com.github.lpettay.tdstub;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

/**
 * No-op stand-in for terrain-diffusion-mc:terrain_diffusion DensityFunction.
 *
 * Worldborder confines players to pre-genned chunks, so compute() should never be
 * invoked during normal play. Returns 0 (sea-level) as a safe default if it is.
 */
public class StubDensityFunction implements DensityFunction.SimpleFunction {
    public static final StubDensityFunction INSTANCE = new StubDensityFunction();
    public static final MapCodec<StubDensityFunction> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public double compute(FunctionContext ctx) {
        return 0.0;
    }

    @Override
    public double minValue() {
        return -1.0;
    }

    @Override
    public double maxValue() {
        return 1.0;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return new KeyDispatchDataCodec<>(CODEC);
    }
}
