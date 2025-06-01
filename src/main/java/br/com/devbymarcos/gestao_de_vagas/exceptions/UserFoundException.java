package br.com.devbymarcos.gestao_de_vagas.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException() {
        super("usuario ja existe");
    }
    
}
