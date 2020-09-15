package com.github.uinio.jpa.io;


/**
 * Response result set
 *
 * @author Jingle-Cat
 */

public class Respond {

    private final int status;

    private final Object json;

    private final String message;

    private Respond(int status, String message, Object json) {
        this.status = status;
        this.json = json;
        this.message = message;
    }

    public static Respond respond() {
        return new Respond(200, null, null);
    }

    public static Respond respond(int status, Object data) {
        return new Respond(status, null, data);
    }

    public static Respond respond(int status, String message, Object data) {
        return new Respond(status, message, data);
    }

    //200
    public static Respond ok(String message) {
        return new Respond(200, message, null);
    }

    //401
    public static Respond unAuthorized(String message) {
        return new Respond(401, message, null);
    }

    //403
    public static Respond forbidden(String message) {
        return new Respond(403, message, null);
    }

    public static Respond success(Object data) {
        return new Respond(200, null, data);
    }

    public static Respond success(String message, Object data) {
        return new Respond(200, message, data);
    }

    public static Respond failure(String message) {
        return new Respond(500, message, null);
    }

    public static Respond failure(String message, Object data) {
        return new Respond(500, message, data);
    }

}
