package com.iafenvoy.origins.data.power.component.builtin;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.data.power.component.PowerComponent;
import com.iafenvoy.origins.data.power.reference.PowerHolder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

/** Holds a temporary numeric value for linked event resources. */
public final class TimedResourceComponent extends PowerComponent {
    public static final MapCodec<TimedResourceComponent> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.DOUBLE.optionalFieldOf("value", 0D).forGetter(TimedResourceComponent::value),
            Codec.INT.optionalFieldOf("remaining", 0).forGetter(TimedResourceComponent::remaining)
    ).apply(instance, TimedResourceComponent::new));

    private double value;
    private int remaining;

    public TimedResourceComponent() { this(0D, 0); }
    public TimedResourceComponent(double value, int remaining) { this.value = value; this.remaining = remaining; }
    public double value() { return this.remaining > 0 ? this.value : 0D; }
    public int remaining() { return this.remaining; }
    public void set(double value, int duration) {
        this.value = value;
        // The source implementation expires when age > (setTick + duration).
        // Components tick after entity events, so retain two extra states: one
        // for the setting tick and one for the boundary tick itself. This also
        // keeps duration=0 values visible until the next tick, matching source.
        this.remaining = duration >= Integer.MAX_VALUE - 1 ? Integer.MAX_VALUE : Math.max(0, duration) + 2;
        this.markDirty();
    }

    @Override
    public void tick(OriginDataHolder holder, PowerHolder parent) {
        if (this.remaining <= 0) return;
        this.remaining--;
        if (this.remaining == 0) this.markDirty();
    }

    @Override public @NotNull MapCodec<? extends PowerComponent> codec() { return CODEC; }
}
