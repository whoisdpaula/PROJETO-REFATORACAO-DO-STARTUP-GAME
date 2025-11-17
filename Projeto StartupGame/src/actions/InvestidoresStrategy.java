package actions;

import model.Deltas;
import model.Startup;
import model.vo.Humor;
import model.vo.Dinheiro;

public class InvestidoresStrategy implements DecisaoStrategy {

    @Override
    public model.Deltas aplicar(model.Startup s){
        double aporte = 2000;
        int repDelta = +8;
        int moralDelta = -5;

        s.setCaixa(s.getCaixa().somar(new Dinheiro(aporte)));

        s.setReputacao(s.getReputacao().aumentar(repDelta));

        s.setMoral(s.getMoral().aumentar(moralDelta * -1));

        s.registrar("Investidores:+"+aporte+"caixa,+"+repDelta+"reputacao,+"+(-moralDelta)+"moral");

        return new Deltas(aporte,repDelta,moralDelta,0);
    }
}