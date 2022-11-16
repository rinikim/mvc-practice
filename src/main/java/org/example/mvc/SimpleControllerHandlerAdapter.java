package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleControllerHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        // controller 를 구현한 class 이라면 handle 한다.
        return (handler instanceof Controller);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // viewName 이 return 된다.
        String viewName = ((Controller) handler).handlerRequest(request, response);
        return new ModelAndView(viewName);
    }
}
