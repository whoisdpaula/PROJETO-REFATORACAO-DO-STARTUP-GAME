package actions;

import model.Deltas;
import model.Startup;
import model.vo.Dinheiro;
import model.vo.Humor;
import model.vo.Percentual;
import exception.SaldoInsuficienteException;

public class ProdutoStrategy implements DecisaoStrategy {

    private static final Dinheiro CUSTO = new Dinheiro(1000.0);
    private static final int REP_DELTA = 5;
    private static final Percentual BONUS = new Percentual(0.12);

    @Override
    public Deltas aplicar(Startup s) {

        if (s.getCaixa().isMenorQue(CUSTO)) {
            throw new SaldoInsuficienteException("Caixa insuficiente para investir " + CUSTO.toString() + " no desenvolvimento de Produto.");
        }

        s.registrar("Produto: Gasto " + CUSTO.toString() + " (Caixa), Ganho +" + REP_DELTA + " Reputação, +12% de Bônus de Receita na próxima rodada.");

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
        return "PRODUTO";
    }
}