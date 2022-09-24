package it.eg.cookbook.model;

public enum ResponseCode {

    OK("Ok"),
    DOCUMENTO_NON_TROVATO("Documento non trovato"),
    DOCUMENTO_GIA_PRESENTE("Id documento è già presente"),
    GENERIC("Errore generico");

    private String message;

    public String getMessage() {
        return message;
    }

    ResponseCode(String message) {
        this.message = message;
    }

}
