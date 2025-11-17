package actions;

import model.Deltas;
import model.Startup;
import model.vo.Humor;
import model.vo.Dinheiro;
import model.vo.Percentual;


public class EquipeStrategy implements DecisaoStrategy {
    @Override
    public model.Deltas aplicar(model.Startup s) {
        double custo = 700;
        int moralDelta = +15;
        double bonusReceitaBase = 50;

        s.setCaixa(s.getCaixa().subtrair(new Dinheiro(custo)));

        s.setMoral(s.setMoral().aumentar(moralDelta));

        s.setReceitaBase(new Dinheiro(s.getReceitaBase().valor()+bonusReceitaBase));

        s.registrar("Equipe: investiu-"+custo+"caixa,+"+moralDelta+"moral,+"+bonusReceitaBase+"na receita base.");


        return new Deltas(-custo,moralDelta,0,bonusReceitaBase);
    }
}