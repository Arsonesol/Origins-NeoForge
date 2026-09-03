package com.iafenvoy.origins.data.power.component.builtin;

import com.iafenvoy.origins.data.power.component.PowerComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

/** Persistent-in-memory component for double precision resources. */
public class DoubleResourceComponent extends PowerComponent {
    public static final MapCodec<DoubleResourceComponent> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.DOUBLE.fieldOf("value").forGetter(x -> x.value), Codec.DOUBLE.fieldOf("min").forGetter(x -> x.min), Codec.DOUBLE.fieldOf("max").forGetter(x -> x.max)
    ).apply(i, DoubleResourceComponent::new));
    private double value, min, max;
    public DoubleResourceComponent(double value, double min, double max) { this.min = min; this.max = max; this.value = Math.clamp(value, min, max); }
    public double getValue() { return value; }
    public double getMin() { return min; }
    public double getMax() { return max; }
    public void setValue(double value) { this.value = Math.clamp(value, min, max); markDirty(); }
    public void setBounds(double min, double max) { this.min = min; this.max = max; this.value = Math.clamp(value, min, max); markDirty(); }
    @Override public @NotNull MapCodec<? extends PowerComponent> codec() { return CODEC; }
}
