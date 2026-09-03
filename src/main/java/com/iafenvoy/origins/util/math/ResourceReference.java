package com.iafenvoy.origins.util.math;

import com.iafenvoy.origins.data._common.helper.ResourceValueHelper;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

/** A numeric value that can either be a literal or read from another resource. */
public record ResourceReference(Either<Double, ResourceLocation> value) {
    public static final Codec<ResourceReference> CODEC = Codec.either(Codec.DOUBLE, ResourceLocation.CODEC).xmap(ResourceReference::new, ResourceReference::value);
    public static final Codec<ResourceReference> INT_CODEC = Codec.either(Codec.INT, ResourceLocation.CODEC)
            .xmap(value -> value.map(number -> ResourceReference.number(number.doubleValue()), ResourceReference::resource), ResourceReference::toIntValue);
    public static final Codec<ResourceReference> FLOAT_CODEC = Codec.either(Codec.FLOAT, ResourceLocation.CODEC)
            .xmap(value -> value.map(number -> ResourceReference.number(number.doubleValue()), ResourceReference::resource), ResourceReference::toFloatValue);

    public static ResourceReference number(double value) { return new ResourceReference(Either.left(value)); }
    public static ResourceReference resource(ResourceLocation value) { return new ResourceReference(Either.right(value)); }
    public double resolve(Entity entity) {
        return this.value.map(v -> v, id -> entity == null ? 0D : ResourceValueHelper.value(entity, id));
    }
    public int resolveInt(Entity entity) { return (int) this.resolve(entity); }
    public float resolveFloat(Entity entity) { return (float) this.resolve(entity); }

    private static Either<Integer, ResourceLocation> toIntValue(ResourceReference reference) {
        return reference.value.map(value -> Either.left(value.intValue()), value -> Either.right(value));
    }

    private static Either<Float, ResourceLocation> toFloatValue(ResourceReference reference) {
        return reference.value.map(value -> Either.left(value.floatValue()), value -> Either.right(value));
    }
}
