package org.example.mvc;

import org.example.annotation.RequestMethod;
import org.example.controller.HandlerKey;
import org.example.view.JspViewResolver;
import org.example.view.ModelAndView;
import org.example.view.View;
import org.example.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private List<HandlerMapping> handlerMappings;    // List 로 하려는 이유 : handlerMapping 이 기존에는 한 개였지만, 애노테이션 용으로 하나가 더 만들어졌다.
    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;

    // tomcat 이 HttpServlet 을 싱글턴으로 만드는데 그 때 servlet 이 만들어지면서 init method 를 호출한다.
    // map 을 초기화 하도록 처리
    @Override
    public void init() throws ServletException {
        RequestMappingHandlerMapping rmhm = new RequestMappingHandlerMapping();
        rmhm.init();

        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("org.example");
        handlerMappings = List.of(rmhm, ahm);

        handlerAdapters = List.of(new SimpleControllerHandlerAdapter());
        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    // 요청이 들어오게 되면 실행
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("[DispatcherServlet] service started.");
        String requestURI = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        // handler 에 작업을 위임한다.
        try {
            // handlerMapping 을 통해서 mapping 을 찾는다.
            // 요청 URI 에 대한 핸들러를 달라.
            // 해당 객체는 URI 에 맞는 객체를 반환할 것이다.
            Object handler = handlerMappings.stream()
                    .filter(hm -> hm.findHandler(new HandlerKey(requestMethod, requestURI)) != null)
                    .map(hm -> hm.findHandler(new HandlerKey(requestMethod, requestURI)))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No handler for [" + requestMethod + ", " + requestURI + "]"));

            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                    .filter(ha -> ha.supports(handler)) // handler 를 지원하는 adapter 를 찾는다.
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No adapter for handler [" + handler + "]"));

            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler); //  handler 를 실행한다.

            for (ViewResolver viewResolver : viewResolvers) {

                View view = viewResolver.resolveView(modelAndView.getViewName());
                view.render(modelAndView.getModel(), request, response);

            }
        } catch (Exception e) {
            log.error("exception occurred : [{}]", e.getMessage(), e);
        }
    }
}
