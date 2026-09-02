package com.iafenvoy.origins.data.action.builtin.block.meta;

import com.iafenvoy.origins.data.action.BlockAction;
import com.iafenvoy.origins.data.condition.BlockCondition;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record WhileAction(BlockCondition condition, BlockAction action) implements BlockAction {
    public static final int MAX_REPETITIONS = 100_000;
    public static final MapCodec<WhileAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockCondition.CODEC.fieldOf("condition").forGetter(WhileAction::condition),
            BlockAction.CODEC.fieldOf("action").forGetter(WhileAction::action)
    ).apply(instance, WhileAction::new));

    @Override
    public @NotNull MapCodec<? extends BlockAction> codec() {
        return CODEC;
    }

    @Override
    public void execute(@NotNull Level level, @NotNull BlockPos pos, @NotNull Optional<Direction> direction) {
        int repetitions = 0;
        while (this.condition.test(level, pos)) {
            if (repetitions++ < MAX_REPETITIONS)
                this.action.execute(level, pos, direction);
            else
                throw new IllegalStateException("An \"origins:while\" loop was not terminated within " + MAX_REPETITIONS + " loops!");
        }
    }
}
