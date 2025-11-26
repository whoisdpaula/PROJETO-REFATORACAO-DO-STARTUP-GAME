package model;

import model.vo.Dinheiro;
import model.vo.Humor;
import model.vo.Percentual;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Startup {

    private long id;
    private String nome;
    private Dinheiro caixa;
    private Dinheiro receitaBase;
    private int reputacao;
    private Humor moral;
    private int rodadaAtual = 1;
    private Percentual bonusPercentReceitaProx = new Percentual(0.0);
    private final List<String> historico = new ArrayList<>();

    public Startup(String nome, Dinheiro caixa, Dinheiro receitaBase, int reputacao, Humor moral) {
        this.nome = nome;
        this.caixa = caixa;
        this.receitaBase = receitaBase;
        this.reputacao = reputacao;
        this.moral = moral;
        this.historico.add("Startup criada.");
    }

    public Startup(String nome) {
        this(
                nome,
                new Dinheiro(10000.00),
                new Dinheiro(1000.00),
                50,
                new Humor(70)
        );
    }


    public void aplicarDeltas(Deltas d) {
        if (d == null) return;

        this.caixa = this.caixa.somar(d.caixaDelta());

        int novoMoralValor = this.moral.valor() + d.moralDelta().valor();


        novoMoralValor = Math.min(100, Math.max(0, novoMoralValor));

        this.moral = new Humor(novoMoralValor);

        this.reputacao += d.reputacaoDelta();
        this.reputacao = Math.min(100, Math.max(0, this.reputacao));

        this.bonusPercentReceitaProx = this.bonusPercentReceitaProx.somar(d.bonusDelta());
    }


    public Dinheiro receitaRodada() {
        Dinheiro receitaComBonus = this.bonusPercentReceitaProx.aplicarSobre(this.receitaBase);
        this.bonusPercentReceitaProx = new Percentual(0.0);
        return receitaComBonus;
    }

    public void registrar(String linha) {
        historico.add("R" + rodadaAtual + " - " + linha);
    }

    public double scoreFinal() {
        return reputacao * 0.35
                + moral.valor() * 0.25
                + (caixa.valor() / 1000.0) * 0.15
                + (receitaBase.valor() / 1000.0) * 0.25;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Dinheiro getCaixa() { return caixa; }
    public void setCaixa(Dinheiro caixa) { this.caixa = caixa; }

    public Dinheiro getReceitaBase() { return receitaBase; }
    public void setReceitaBase(Dinheiro receitaBase) { this.receitaBase = receitaBase; }

    public int getReputacao() { return reputacao; }
    public void setReputacao(int reputacao) { this.reputacao = reputacao; }

    public Humor getMoral() { return moral; }
    public void setMoral(Humor moral) { this.moral = moral; }

    public int getRodadaAtual() { return rodadaAtual; }
    public void setRodadaAtual(int rodadaAtual) { this.rodadaAtual = rodadaAtual; }

    public Percentual getBonusPercentReceitaProx() { return bonusPercentReceitaProx; }
    public void setBonusPercentReceitaProx(Percentual bonusPercentReceitaProx) { this.bonusPercentReceitaProx = bonusPercentReceitaProx; }

    public List<String> getHistorico() { return historico; }


    @Override
    public String toString() {
        return String.format(Locale.US,
                "%s | Caixa: %s | ReceitaBase: %s | Rep: %d | Moral: %d",
                nome, caixa.toString(), receitaBase.toString(), reputacao, moral.valor());
    }
}