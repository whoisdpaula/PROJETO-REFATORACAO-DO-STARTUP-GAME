package actions;

import model.Deltas;
import model.Startup;
import model.vo.Dinheiro;
import model.vo.Humor;
import model.vo.Percentual;
import exception.DomainException;

public class InvestidoresStrategy implements DecisaoStrategy {

    private static final int REP_DELTA = 5;
    @Override
    public Deltas aplicar(Startup s) {

        return aplicar(s, new Dinheiro(1500.0));
    }
    @Override
    public Deltas aplicar(Startup s, Dinheiro valorInput) {

        if (valorInput.valor() <= 0) {

            throw new DomainException("O valor do investimento deve ser positivo.");
        }
        s.registrar("Investidores: Recebida injeção de " + valorInput.toString() + ", Ganho +" + REP_DELTA + " Reputação.");
        return new Deltas(
                valorInput,
                REP_DELTA,
                new Humor(0),
                new Percentual(0.0)
        );
    }
    @Override
    public boolean requerInput() {
        return true;
    }
    @Override
    public String nome() {
        return "BUSCAR_INVESTIDOR";
    }
}