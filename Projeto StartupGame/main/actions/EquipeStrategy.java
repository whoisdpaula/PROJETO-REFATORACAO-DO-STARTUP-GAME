package actions;

import model.Deltas;
import model.Startup;
import model.vo.Dinheiro;
import model.vo.Humor;
import model.vo.Percentual;
import exception.SaldoInsuficienteException;

public class EquipeStrategy implements DecisaoStrategy {

    private static final Dinheiro CUSTO = new Dinheiro(500.0);
    private static final int REP_DELTA = 3;
    private static final Humor MORAL_DELTA = new Humor(5);
    private static final Percentual BONUS = new Percentual(0.02);

    @Override
    public Deltas aplicar(Startup s) {

        if (s.getCaixa().isMenorQue(CUSTO)) {
            throw new SaldoInsuficienteException("Caixa insuficiente para contratar Equipe. Necessário " + CUSTO.toString());
        }
        s.registrar("Equipe: Gasto " + CUSTO.toString() + " (Caixa), Ganho +" + REP_DELTA + " Reputação, +" + MORAL_DELTA.valor() + " Moral, +2% de Bônus de Receita.");

        return new Deltas(
                new Dinheiro(-CUSTO.valor()),
                REP_DELTA,
                MORAL_DELTA,
                BONUS
        );
    }
    @Override
    public Deltas aplicar(Startup s, Dinheiro valorInput) {
        return aplicar(s);
    }
    @Override
    public String nome() {
        return "CONTRATAR_EQUIPE";
    }
}