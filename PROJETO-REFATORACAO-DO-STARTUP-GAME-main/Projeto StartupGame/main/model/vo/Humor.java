package model.vo;


public record Humor(int valor) {

    public Humor {
    }

    public static Humor newDelta(int delta) {
        return new Humor(delta);
    }

    public static Humor newEstado(int estado) {
        return new Humor(estado);
    }

    @Override
    public int valor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}