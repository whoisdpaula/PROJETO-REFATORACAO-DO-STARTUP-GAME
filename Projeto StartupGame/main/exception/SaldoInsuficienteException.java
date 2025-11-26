package exception;

public class SaldoInsuficienteException extends DomainException {
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}