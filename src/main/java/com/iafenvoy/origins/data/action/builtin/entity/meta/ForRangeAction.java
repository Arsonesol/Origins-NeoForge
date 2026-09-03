package com.iafenvoy.origins.data.action.builtin.entity.meta;

import com.iafenvoy.origins.data.action.EntityAction;
import com.iafenvoy.origins.util.math.ResourceReference;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public record ForRangeAction(ResourceReference range, EntityAction action) implements EntityAction {
    public static final MapCodec<ForRangeAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceReference.CODEC.fieldOf("range").forGetter(ForRangeAction::range),
            EntityAction.CODEC.fieldOf("action").forGetter(ForRangeAction::action)
    ).apply(instance, ForRangeAction::new));

    @Override
    public @NotNull MapCodec<? extends EntityAction> codec() { return CODEC; }

    @Override
    public void execute(@NotNull Entity source) {
        double resolved = this.range.resolve(source);
        int count = Double.isNaN(resolved) || resolved <= 0 ? 0 : resolved >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) resolved;
        for (int i = 0; i < count; i++) this.action.execute(source);
    }
}
