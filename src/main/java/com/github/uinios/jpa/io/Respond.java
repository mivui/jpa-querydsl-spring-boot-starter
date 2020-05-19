package com.github.uinios.jpa.io;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Objects;


@Getter
@Setter
public class Respond {

    private int status;

    private Object json;

    private Object message;

    //200
    public static Respond ok(Object message) {
        Respond authResponse = new Respond();
        authResponse.setStatus(HttpStatus.OK.value());
        authResponse.setMessage(message);
        return authResponse;
    }

    //401
    public static Respond unAuthorized(Object message) {
        Respond authResponse = new Respond();
        authResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        authResponse.setMessage(message);
        return authResponse;
    }

    //403
    public static Respond forbidden(Object message) {
        Respond authResponse = new Respond();
        authResponse.setStatus(HttpStatus.FORBIDDEN.value());
        authResponse.setMessage(message);
        return authResponse;
    }


    //404
    public static Respond notFound(Object message) {
        Respond authResponse = new Respond();
        authResponse.setStatus(HttpStatus.NOT_FOUND.value());
        authResponse.setMessage(message);
        return authResponse;
    }

    //500
    public static Respond error(Object message) {
        Respond authResponse = new Respond();
        authResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        authResponse.setMessage(message);
        return authResponse;
    }


    public static Respond success(Object data) {
        final Respond respond = new Respond();
        respond.setStatus(HttpStatus.OK.value());
        respond.setJson(data);
        return respond;
    }

    public static Respond success(Object message, Object data) {
        final Respond respond = new Respond();
        respond.setStatus(HttpStatus.OK.value());
        respond.setMessage(message);
        respond.setJson(data);
        return respond;
    }

    public static Respond successContent(String message, String... contents) {
        final Respond respond = new Respond();
        respond.setStatus(HttpStatus.OK.value());
        if (Objects.nonNull(message)) {
            for (String content : contents) {
                message = message.replaceFirst("\\{}", content);
            }
        }
        respond.setMessage(message);
        return respond;
    }

    public static Respond failure(Object message) {
        final Respond respond = new Respond();
        respond.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        respond.setMessage(message);
        return respond;
    }

    public static Respond failure(Object message, Object data) {
        final Respond respond = new Respond();
        respond.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        respond.setMessage(message);
        respond.setJson(data);
        return respond;
    }

    public static Respond failureContent(String message, String... contents) {
        final Respond respond = new Respond();
        respond.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        if (Objects.nonNull(message)) {
            for (String content : contents) {
                message = message.replaceFirst("\\{}", content);
            }
        }
        respond.setMessage(message);
        return respond;
    }
}
