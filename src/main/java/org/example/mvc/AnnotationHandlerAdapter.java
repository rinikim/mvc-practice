package org.example.mvc;

import org.example.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotationHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof AnnotationHandler;    // AnnotationHandler.class 이여야만 해당 adapter 를 사용할 수 있다.
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String vieWName = ((AnnotationHandler) handler).handle(request, response);
        return new ModelAndView(vieWName);
    }

}
