package actions;

import model.Deltas;
import model.Startup;
import model.vo.Dinheiro;

public interface DecisaoStrategy {

    Deltas aplicar(Startup s);

    Deltas aplicar(Startup s, Dinheiro valorInput);

    String nome();

    default boolean requerInput() {
        return false;
    }
}