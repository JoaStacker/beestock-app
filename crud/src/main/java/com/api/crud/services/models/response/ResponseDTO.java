package com.api.crud.services.models.response;

public class ResponseDTO {
    //tambien se puede trabajar con hashmap pero ni idea :V

    //para que sirve esta clase?
    private int numOfErrors;
    private String message;

    public int getNumOfErrors() {
        return numOfErrors;
    }

    public void setNumOfErrors(int numOfErrors) {
        this.numOfErrors = numOfErrors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
