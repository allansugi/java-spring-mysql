package com.example.demo.response;

/**
 * for HTTP response
 * @param <T> any data that wants to be sent to the client side
 */
public class Response<T> {
    private Boolean success;
    private T response;

    public Response() {

    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
