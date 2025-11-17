package actions;

import model.Deltas;
import model.Startup;
import model.vo.Dinheiro;
import model.vo.Humor;

public class CortarCustosStrategy implements DecisaoStrategy {

    @Override
    public model.Deltas aplicar(model.Startup s){
       double economia = 400;
       int moralDelta = -12;
       int reputacaoDelta = -5;

       s.setCaixa(s.getCaixa().somar(new Dinheiro(economia)));

       s.setMoral(s.getMoral().reduzir(-moralDelta));

       s.setReputacao(s.getReputacao().reduzir(-reputacaoDelta));

       s.registrar("Cortar custos:+"+economia+"caixa,"+moralDelta+"moral,"+reputacaoDelta+"reputacao");

        return new Deltas(economia,moralDelta,reputacaoDelta,0);
    }
}
