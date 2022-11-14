package org.example.mvc;

import org.example.annotation.RequestMethod;
import org.example.controller.HandlerKey;
import org.example.mvc.controller.Controller;
import org.example.view.JspViewResolver;
import org.example.view.View;
import org.example.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private RequestMappingHandlerMapping rmhm;
    private List<ViewResolver> viewResolvers;

    // tomcat 이 HttpServlet 을 싱글턴으로 만드는데 그 때 servlet 이 만들어지면서 init method 를 호출한다.
    // map 을 초기화 하도록 처리
    @Override
    public void init() throws ServletException {
        rmhm = new RequestMappingHandlerMapping();
        rmhm.init();

        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    // 요청이 들어오게 되면 실행
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("[DispatcherServlet] service started.");

        // handlerMapping 을 통해서 mapping 을 찾는다.
        // 요청 URI 에 대한 핸들러를 달라.
        // 해당 객체는 URI 에 맞는 객체를 반환할 것이다.
        Controller handler = rmhm.findHandler(new HandlerKey(RequestMethod.valueOf(request.getMethod()), request.getRequestURI()));

        // handler 에 작업을 위임한다.
        try {
            String viewName = handler.handlerRequest(request, response);

            for (ViewResolver viewResolver : viewResolvers) {

                View view = viewResolver.resolveView(viewName);
                view.render(new HashMap<>(), request, response);

            }
        } catch (Exception e) {
            log.error("exception occurred : [{}]", e.getMessage(), e);
        }
    }
}
