package org.example.controller;

import org.example.mvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 해당하는 경로로 이동
public class ForwardController implements Controller {

    private final String forwardUriPath;

    public ForwardController(String forwardUriPath) {
        this.forwardUriPath = forwardUriPath;
    }

    @Override
    public String handlerRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forwardUriPath;
    }
}
