package it.eg.cookbook.model;

public class ResponseMessage {

    private Boolean success;
    private String code;
    private String message;
    private String description;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResponseMessage(Boolean success, ResponseCode responseCode, String description) {
        this.success = success;
        this.code = responseCode.name();
        this.message = responseCode.getMessage();
        this.description = description;
    }

}
