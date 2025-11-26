
package model.vo;

import model.vo.Dinheiro;
import model.vo.Humor;
import model.Startup;


public record RodadaResumo(
        long startupId,
        int numeroRodada,
        Dinheiro caixaAntes,
        Dinheiro caixaDepois,
        Dinheiro receitaGerada,
        Humor moral,
        int reputacao
) {

    public RodadaResumo(Startup s, Dinheiro receitaGerada, Dinheiro caixaAntes) {
        this(
                s.getId(),
                s.getRodadaAtual(),
                caixaAntes,
                s.getCaixa(),
                receitaGerada,
                s.getMoral(),
                s.getReputacao()
        );
    }
}