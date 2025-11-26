package actions;

import model.Deltas;
import model.Startup;
import model.vo.Dinheiro;
import model.vo.Percentual;
import model.vo.Humor;
import exception.SaldoInsuficienteException;

public class MarketingStrategy implements DecisaoStrategy {

    private static final Dinheiro CUSTO = new Dinheiro(500.0);
    private static final int REP_DELTA = 10;
    private static final Percentual BONUS = new Percentual(0.05);

    @Override
    public Deltas aplicar(Startup s) {

        if (s.getCaixa().isMenorQue(CUSTO)) {
            throw new SaldoInsuficienteException("Caixa insuficiente para investir " + CUSTO.toString() + " em Marketing.");
        }

        s.registrar("Marketing: Gasto " + CUSTO.toString() + " (Caixa), Ganho +" + REP_DELTA + " Reputação, +" + (BONUS.valor() * 100) + "% de Bônus de Receita.");

        return new Deltas(
                new Dinheiro(-CUSTO.valor()),
                REP_DELTA,
                new Humor(0),
                BONUS
        );
    }
    @Override
    public Deltas aplicar(Startup s, Dinheiro valorInput) {
        return aplicar(s);
    }

    @Override
    public String nome() {
        return "MARKETING";
    }
}