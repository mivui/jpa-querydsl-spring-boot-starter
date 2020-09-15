package com.github.uinio.jpa.io;


/**
 * Response result set
 *
 * @author Jingle-Cat
 */

public class Respond {

    private Respond() {
    }

    private int status;

    private Object json;

    private String message;

    public static Respond respond() {
        final Respond respond = new Respond();
        respond.setStatus(200);
        return respond;
    }

    public static Respond respond(int status, String message) {
        final Respond respond = new Respond();
        respond.setStatus(status);
        respond.setMessage(message);
        return respond;
    }

    public static Respond respond(int status, String message, Object data) {
        final Respond respond = new Respond();
        respond.setStatus(status);
        respond.setMessage(message);
        respond.setJson(data);
        return respond;
    }

    //200
    public static Respond ok(String message) {
        Respond respond = new Respond();
        respond.setStatus(200);
        respond.setMessage(message);
        return respond;
    }

    //401
    public static Respond unAuthorized(String message) {
        Respond respond = new Respond();
        respond.setStatus(401);
        respond.setMessage(message);
        return respond;
    }

    //403
    public static Respond forbidden(String message) {
        Respond respond = new Respond();
        respond.setStatus(403);
        respond.setMessage(message);
        return respond;
    }

    //404
    public static Respond notFound(String message) {
        Respond respond = new Respond();
        respond.setStatus(404);
        respond.setMessage(message);
        return respond;
    }

    //500
    public static Respond error(String message) {
        Respond respond = new Respond();
        respond.setStatus(500);
        respond.setMessage(message);
        return respond;
    }

    public static Respond success(Object data) {
        final Respond respond = new Respond();
        respond.setStatus(200);
        respond.setJson(data);
        return respond;
    }

    public static Respond success(String message, Object data) {
        final Respond respond = new Respond();
        respond.setStatus(200);
        respond.setMessage(message);
        respond.setJson(data);
        return respond;
    }

    public static Respond failure(String message) {
        final Respond respond = new Respond();
        respond.setStatus(500);
        respond.setMessage(message);
        return respond;
    }

    public static Respond failure(String message, Object data) {
        final Respond respond = new Respond();
        respond.setStatus(500);
        respond.setMessage(message);
        respond.setJson(data);
        return respond;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getJson() {
        return json;
    }

    public void setJson(Object json) {
        this.json = json;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
