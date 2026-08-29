package com.iafenvoy.origins.data.power.builtin.regular;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.data.power.Power;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Map;
import java.util.WeakHashMap;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DamageOverTimePower extends Power {
    public static final MapCodec<DamageOverTimePower> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BaseSettings.CODEC.forGetter(Power::getSettings),
            Codec.INT.optionalFieldOf("interval", 20).forGetter(DamageOverTimePower::getInterval),
            Codec.INT.optionalFieldOf("onset_delay").forGetter(DamageOverTimePower::getOnsetDelay),
            Codec.FLOAT.fieldOf("damage").forGetter(DamageOverTimePower::getDamage),
            Codec.FLOAT.optionalFieldOf("damage_easy").forGetter(DamageOverTimePower::getDamageEasy),
            DamageType.CODEC.fieldOf("damage_type").forGetter(DamageOverTimePower::getDamageType),
            Enchantment.CODEC.optionalFieldOf("protection_enchantment").forGetter(DamageOverTimePower::getProtectionEnchantment),
            Codec.FLOAT.optionalFieldOf("protection_effectiveness", 1.0F).forGetter(DamageOverTimePower::getProtectionEffectiveness)
    ).apply(i, DamageOverTimePower::new));

    private final int interval;
    private final int onsetDelay;
    private final float damage, damageEasy;
    private final Holder<DamageType> damageType;
    private final Optional<Holder<Enchantment>> protectionEnchantment;
    private final float protectionEffectiveness;
    private final Map<OriginDataHolder, Integer> activeTicks = new WeakHashMap<>();

    public DamageOverTimePower(BaseSettings settings, int interval, Optional<Integer> onsetDelay, float damage, Optional<Float> damageEasy, Holder<DamageType> damageType, Optional<Holder<Enchantment>> protectionEnchantment, float protectionEffectiveness) {
        super(settings);
        this.interval = interval;
        this.onsetDelay = onsetDelay.orElse(this.interval);
        this.damage = damage;
        this.damageEasy = damageEasy.orElse(this.damage);
        this.damageType = damageType;
        this.protectionEnchantment = protectionEnchantment;
        this.protectionEffectiveness = protectionEffectiveness;
    }

    public int getInterval() {
        return this.interval;
    }

    public Optional<Integer> getOnsetDelay() {
        return Optional.of(this.onsetDelay);
    }

    public float getDamage() {
        return this.damage;
    }

    public Optional<Float> getDamageEasy() {
        return Optional.of(this.damageEasy);
    }

    public Holder<DamageType> getDamageType() {
        return this.damageType;
    }

    public Optional<Holder<Enchantment>> getProtectionEnchantment() {
        return this.protectionEnchantment;
    }

    public float getProtectionEffectiveness() {
        return this.protectionEffectiveness;
    }

    @Override
    public @NotNull MapCodec<? extends Power> codec() {
        return CODEC;
    }

    @Override
    public int tickInterval() {
        return this.interval;
    }

    @Override
    public void inactive(@NotNull OriginDataHolder holder) {
        this.activeTicks.remove(holder);
    }

    @Override
    public void activeTick(OriginDataHolder holder) {
        super.activeTick(holder);
        Entity entity = holder.getEntity();
        int ticks = this.activeTicks.merge(holder, this.interval, Integer::sum);
        int protection = this.getProtection(entity);
        int delay = this.onsetDelay + (int) (Math.pow(protection * 2, 1.3) * this.protectionEffectiveness * 20);
        if (ticks >= delay)
            entity.hurt(new DamageSource(this.damageType), entity.level().getDifficulty() == Difficulty.EASY ? this.damageEasy : this.damage);
    }

    private int getProtection(Entity entity) {
        if (!(entity instanceof net.minecraft.world.entity.LivingEntity living) || this.protectionEnchantment.isEmpty()) return 0;
        int accumulated = 0;
        int items = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;
            ItemStack stack = living.getItemBySlot(slot);
            int level = stack.getEnchantmentLevel(this.protectionEnchantment.get());
            accumulated += level;
            if (level > 0) items++;
        }
        return accumulated + items;
    }
}
