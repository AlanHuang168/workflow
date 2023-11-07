package org.apache.alan.wrokflow.utils;

import org.apache.alan.wrokflow.dto.Authentication;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {

    private static final ThreadLocal<Authentication> authenticationInfoHolder = new ThreadLocal();
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal();

    public static void add(Authentication authenticationInfo) {
        authenticationInfoHolder.set(authenticationInfo);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }


    public static Authentication getAuthenticationInfo() {
        return authenticationInfoHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove() {
        requestHolder.remove();
        authenticationInfoHolder.remove();
    }

}
