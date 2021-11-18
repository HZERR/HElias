package ru.hzerr.exception.network;

public class WebSiteNotWorkingException extends NetworkException {

    public WebSiteNotWorkingException() {
        super();
    }
    public WebSiteNotWorkingException(String s) {
        super(s);
    }
    public WebSiteNotWorkingException(int code) {
        super("Web site's not responding. The server response code is " + code);
    }
    public WebSiteNotWorkingException(String request, int code) {
        super("Web site's not responding. The server response code is " + code + ". Request - " + request);
    }
}
