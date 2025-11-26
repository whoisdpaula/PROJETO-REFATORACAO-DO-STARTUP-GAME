package actions;

import model.Deltas;
import model.Startup;
import model.vo.Dinheiro;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BotStrategy implements DecisaoStrategy {

    private final List<DecisaoStrategy> acoesBot = Arrays.asList(
            new MarketingStrategy(),
            new EquipeStrategy(),
            new CortarCustosStrategy()
    );
    private final Random random = new Random();

    @Override
    public Deltas aplicar(Startup s) {
        DecisaoStrategy acaoEscolhida = acoesBot.get(random.nextInt(acoesBot.size()));
        try {
            Deltas d = acaoEscolhida.aplicar(s);
            s.registrar("BOT: Decisão automática aplicada (" + acaoEscolhida.nome() + ").");
            return d;
        } catch (Exception e) {
            s.registrar("BOT: Falha na decisão automática (" + e.getMessage() + "). Cortando custos para recuperação.");
            return new CortarCustosStrategy().aplicar(s);
        }
    }

    @Override
    public Deltas aplicar(Startup s, Dinheiro valorInput) {
        return aplicar(s);
    }

    @Override
    public String nome() {
        return "BOT_DECISAO_AUTOMATICA";
    }
}