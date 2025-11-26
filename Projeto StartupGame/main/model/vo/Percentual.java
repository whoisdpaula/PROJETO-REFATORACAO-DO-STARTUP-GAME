package model.vo;

import exception.DomainException;

public record Percentual(double valor) {
    public Percentual {
        if (valor < -1.0 || valor > 1.0) {
            throw new DomainException("Percentual deve estar entre -100% (-1.0) e +100% (1.0)");
        }
    }
    public Dinheiro aplicarSobre(Dinheiro baseDinheiro) {
        double novoValor = baseDinheiro.valor() * (1.0 + valor);
        return new Dinheiro(novoValor);
    }
    public Percentual somar(Percentual outro) {
        return new Percentual(this.valor + outro.valor);
    }
}