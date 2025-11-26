package model;

import model.vo.Dinheiro;
import model.vo.Humor;
import model.vo.Percentual;

public record Deltas(
        Dinheiro caixaDelta,
        int reputacaoDelta,
        Humor moralDelta,
        Percentual bonusDelta
) {
    public static Deltas empty() {
        return new Deltas(new Dinheiro(0.0), 0, new Humor(0), new Percentual(0.0));
    }

    public Deltas somar(Deltas outro) {
        return new Deltas(
                this.caixaDelta.somar(outro.caixaDelta()),
                this.reputacaoDelta + outro.reputacaoDelta(),
                new Humor(this.moralDelta.valor() + outro.moralDelta().valor()),
                this.bonusDelta.somar(outro.bonusDelta())
        );
    }

    public Deltas inverter() {
        return new Deltas(
                new Dinheiro(-this.caixaDelta.valor()),
                -this.reputacaoDelta,
                new Humor(-this.moralDelta.valor()),
                new Percentual(-this.bonusDelta.valor())
        );
    }
    @Override
    public String toString() {
        return String.format("Deltas{caixa=%s, rep=%+d, moral=%+d, bonus=%.4f}",
                caixaDelta.toString(), reputacaoDelta, moralDelta.valor(), bonusDelta.valor());
    }
}