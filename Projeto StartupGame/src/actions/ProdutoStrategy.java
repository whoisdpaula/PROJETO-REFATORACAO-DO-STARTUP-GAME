package actions;

import model.Deltas;
import model.Startup;
import model.vo.Humor;
import model.vo.Dinheiro;

public class ProdutoStrategy implements DecisaoStrategy {

    @Override
    public model.Deltas aplicar(model.Startup s){
        double custo = 1000;
        int repDelta = +5;
        double receitaBonus = 120;

        s.setCaixa(s.getCaixa().subtrair(new Dinheiro(custo)));

        s.setReputacao(s.getReputacao().aumentar(repDelta));

        s.setReceitaBase(new Dinheiro(s.getReceitaBase().valor()+receitaBonus));

        s.registrar("Produto: -"+custo+"caixa,+"+repDelta+"reputacao,receita base+"+receitaBonus);

        return new Deltas(-custo,repDelta,0,receitaBonus);

    }
}