package com.masterplan.behaviour.response;

public class APIResponse {
    private int status;
    private String message;
    private Object data;
    // private String stack;

    public APIResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        // this.stack=null;
    }

    // Getters and setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // public void setStack(String stack){
    //     this.stack = stack;
    // }

}
