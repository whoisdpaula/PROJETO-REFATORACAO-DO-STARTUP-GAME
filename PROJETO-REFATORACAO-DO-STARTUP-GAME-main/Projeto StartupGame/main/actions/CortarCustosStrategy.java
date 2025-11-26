package actions;

import model.Deltas;
import model.Startup;
import model.vo.Dinheiro;
import model.vo.Humor;
import model.vo.Percentual;

public class CortarCustosStrategy implements DecisaoStrategy {

    private static final Dinheiro CAIXA_GANHO = new Dinheiro(300.0);
    private static final int REP_DELTA = -8;

    private static final Humor MORAL_DELTA = Humor.newDelta(-15);

    @Override
    public Deltas aplicar(Startup s) {

        s.registrar("Cortar Custos: Ganho " + CAIXA_GANHO.toString() + ", Perda de " + (-REP_DELTA) + " Reputação, Perda de " + (-MORAL_DELTA.valor()) + " Moral.");

        return new Deltas(
                CAIXA_GANHO,
                REP_DELTA,
                MORAL_DELTA,
                new Percentual(0.0)
        );
    }

    @Override
    public Deltas aplicar(Startup s, Dinheiro valorInput) {
        return aplicar(s);
    }

    @Override
    public String nome() {
        return "CORTAR_CUSTOS";
    }
}