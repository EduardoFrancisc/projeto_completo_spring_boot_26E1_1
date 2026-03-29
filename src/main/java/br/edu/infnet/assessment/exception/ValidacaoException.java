package br.edu.infnet.assessment.exception;

import java.util.List;

public class ValidacaoException extends RuntimeException {

    private final List<String> detalhes;

    public ValidacaoException(List<String> detalhes) {
        super("Solicitação inválida");
        this.detalhes = detalhes;
    }

    public List<String> getDetalhes() {
        return detalhes;
    }
}