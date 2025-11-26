package model.vo;

import exception.SaldoInsuficienteException;
public record Dinheiro(double valor) {

    public Dinheiro {
        if (valor < 0) {

        }
    }
    public Dinheiro somar(Dinheiro outro){
        return new Dinheiro(this.valor + outro.valor);
    }

    public Dinheiro subtrair(Dinheiro outro){
        double novo = this.valor - outro.valor;
        if (novo < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para subtrair " + outro.toString() + " de " + this.toString());
        }
        return new Dinheiro(novo);
    }
    public boolean isMenorQue(Dinheiro outro) {
        return this.valor < outro.valor;
    }
    @Override
    public String toString(){ return String.format("R$%.2f", valor); }
}