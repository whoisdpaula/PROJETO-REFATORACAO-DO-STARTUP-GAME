package model;

public class Rodada {
    private long id;
    private int numero;
    private long startupId;

    public Rodada(int numero, long startupId) {
        this.numero = numero;
        this.startupId = startupId;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public long getStartupId() {
        return startupId;
    }
    public void setStartupId(long startupId) {
        this.startupId = startupId;
    }

    @Override
    public String toString() {
        return "Rodada{" +
                "id=" + id +
                ", numero=" + numero +
                ", startupId=" + startupId +
                '}';
    }
}
