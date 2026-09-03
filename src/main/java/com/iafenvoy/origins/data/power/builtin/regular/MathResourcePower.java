package com.iafenvoy.origins.data.power.builtin.regular;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.util.math.VariableSerializer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

/** A read-only resource calculated from an mXparser expression. */
public final class MathResourcePower extends LinkedResourcePower {
    public static final MapCodec<MathResourcePower> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BaseSettings.CODEC.forGetter(Power::getSettings),
            Codec.STRING.fieldOf("expression").forGetter(MathResourcePower::expression),
            VariableSerializer.CODEC.optionalFieldOf("variables", new VariableSerializer(java.util.Map.of())).forGetter(MathResourcePower::variables)
    ).apply(instance, MathResourcePower::new));

    private final String expression;
    private final VariableSerializer variables;

    public MathResourcePower(BaseSettings settings, String expression, VariableSerializer variables) {
        super(settings);
        this.expression = expression;
        this.variables = variables;
    }

    public String expression() {
        return this.expression;
    }

    public VariableSerializer variables() {
        return this.variables;
    }

    @Override
    protected double supply(OriginDataHolder holder) {
        Argument[] arguments = this.variables.variables().keySet().stream()
                .map(name -> new Argument(name, this.variables.value(name, holder.getEntity())))
                .toArray(Argument[]::new);
        return new Expression(this.expression, arguments).calculate();
    }

    @Override
    protected MapCodec<? extends LinkedResourcePower> codecImpl() {
        return CODEC;
    }
}
