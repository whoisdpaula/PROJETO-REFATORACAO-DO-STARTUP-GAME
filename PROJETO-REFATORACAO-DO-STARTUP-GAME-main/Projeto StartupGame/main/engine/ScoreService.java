package engine;

import model.Startup;
import java.util.Comparator;
import java.util.List;

public class ScoreService {
    public double calcularScore(Startup s) {
        return s.scoreFinal();
    }
    public List<Startup> ordenarPorScore(List<Startup> lista) {
        return lista.stream()
                .sorted(Comparator.comparingDouble(Startup::scoreFinal).reversed())
                .toList();
    }
}
