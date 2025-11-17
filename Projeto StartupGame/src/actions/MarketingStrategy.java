package actions;

import model.Deltas;
import model.Startup;
import model.vo.Dinheiro;
import model.vo.Humor;
import model.vo.Percentual;

public class MarketingStrategy implements DecisaoStrategy {

    @Override
    public model.Deltas aplicar(model.Startup s){

        double custo = 500;
        int repDelta = +10;
        double bonusProxRodada = 0.05;

        s.setCaixa(s.getCaixa().subtrair(new Dinheiro(custo)));

        s.setReputacao(s.getReputacao().aumentar(repDelta));

        s.addBonusPercentReceitaProx(bonusProxRodada);

        s.registrar("Marketing-"+custo+"caixa,+"+repDelta+"reputação,+5% receita proxima rodada.");

        return new Deltas(-custo,repDelta,0,bonusProxRodada);
    }
}