package com.iafenvoy.origins.data.power.builtin.modify;

import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.util.math.Modifier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public final class ModifyResourceMinimumPower extends ResourceModifyingPower {
    public static final MapCodec<ModifyResourceMinimumPower> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BaseSettings.CODEC.forGetter(Power::getSettings), ResourceModifyingPower.SETTINGS_CODEC.forGetter(x -> new Settings(x.resource, java.util.Optional.empty(), x.modifiers))
    ).apply(i, (settings, value) -> new ModifyResourceMinimumPower(settings, value.resource(), value.modifiers())));
    public ModifyResourceMinimumPower(BaseSettings settings, ResourceLocation resource, List<Modifier> modifiers) { super(settings, resource, modifiers); }
    @Override protected MapCodec<? extends ResourceModifyingPower> codecImpl() { return CODEC; }
}
