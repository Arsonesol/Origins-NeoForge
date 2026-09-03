package com.iafenvoy.origins.data.action.builtin.entity;

import com.iafenvoy.origins.data.action.EntityAction;
import com.iafenvoy.origins.data._common.helper.ResourceValueHelper;
import com.iafenvoy.origins.util.math.ResourceOperation;
import com.iafenvoy.origins.util.math.VariableSerializer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.mariuszgromada.math.mxparser.Expression;

public record VariableChangeResourceAction(ResourceLocation resource, String expression, VariableSerializer variables,
                                           ResourceOperation operation) implements EntityAction {
    public static final MapCodec<VariableChangeResourceAction> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ResourceLocation.CODEC.fieldOf("resource").forGetter(VariableChangeResourceAction::resource),
            Codec.STRING.fieldOf("expression").forGetter(VariableChangeResourceAction::expression),
            VariableSerializer.CODEC.optionalFieldOf("variables", new VariableSerializer(java.util.Map.of())).forGetter(VariableChangeResourceAction::variables),
            ResourceOperation.CODEC.optionalFieldOf("operation", ResourceOperation.ADD).forGetter(VariableChangeResourceAction::operation)
    ).apply(i, VariableChangeResourceAction::new));
    @Override public @NotNull MapCodec<? extends EntityAction> codec() { return CODEC; }
    @Override public void execute(@NotNull Entity source) {
        if (!(source instanceof LivingEntity)) return;
        double change = new Expression(this.expression, this.variables.variables().entrySet().stream().map(e -> new org.mariuszgromada.math.mxparser.Argument(e.getKey(), this.variables.value(e.getKey(), source))).toArray(org.mariuszgromada.math.mxparser.Argument[]::new)).calculate();
        if (this.operation == ResourceOperation.ADD)
            ResourceValueHelper.addOrThrow(source, resource, change);
        else
            ResourceValueHelper.setOrThrow(source, resource, change);
    }
}
