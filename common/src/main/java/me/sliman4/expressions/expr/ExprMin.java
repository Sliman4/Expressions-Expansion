package me.sliman4.expressions.expr;

import me.dreamerzero.miniplaceholders.api.Expansion;
import me.sliman4.expressions.Expression;
import me.sliman4.expressions.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;

public class ExprMin implements Expression {
    @Override
    public void register(Expansion.Builder builder) {
        builder.globalPlaceholder("min", (queue, ctx) -> {
            double min;
            boolean isFloat = false;
            String s1 = Utils.parseToPlainText(ctx, queue.popOr("<expr_min> requires at least 1 argument").value());
            try {
                if (s1.contains(".")) {
                    isFloat = true;
                }
                min = Double.parseDouble(s1);
            } catch (NumberFormatException exception) {
                throw ctx.newException("Not a number: `" + s1 + "`");
            }
            while (queue.hasNext()) {
                String s = Utils.parseToPlainText(ctx, queue.pop().value());
                try {
                    if (s.contains(".")) {
                        isFloat = true;
                    }
                    min = Math.min(min, Double.parseDouble(s));
                } catch (NumberFormatException exception) {
                    throw ctx.newException("Not a number: `" + s + "`");
                }
            }
            return Tag.inserting(isFloat ? Component.text(min) : Component.text((int) Math.round(min)));
        });
    }
}
