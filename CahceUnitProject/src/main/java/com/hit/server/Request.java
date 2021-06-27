package main.java.com.hit.server;

import java.io.Serializable;
import java.util.Map;

public class Request<T> implements Serializable {
    private Map<String, String> headers;
    private T body;

    public Request(Map<String, String> headers, T body) {
        this.headers = headers;
        this.body = body;

    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "headers:\n\t" + headers + "\nbody:\n\t" + body;
    }
}
