package org.example.mvc;

import org.example.annotation.RequestMethod;
import org.example.controller.ForwardController;
import org.example.controller.HandlerKey;
import org.example.controller.UserCreateController;
import org.example.controller.UserListController;
import org.example.mvc.controller.Controller;
import org.example.mvc.controller.HomeController;

import java.util.HashMap;
import java.util.Map;

// mvc 패턴 중 HandlerMapping 의 역할
public class RequestMappingHandlerMapping implements HandlerMapping {
    // /users, UserController
    private Map<HandlerKey, Controller> mappings = new HashMap<>();

    void init() {
//        mappings.put(new HandlerKey(RequestMethod.GET, "/"), new HomeController());
        mappings.put(new HandlerKey(RequestMethod.GET, "/users"), new UserListController());
        mappings.put(new HandlerKey(RequestMethod.POST, "/users"), new UserCreateController());
        mappings.put(new HandlerKey(RequestMethod.GET, "/user/form"), new ForwardController("/user/form"));
    }

    public Controller findHandler(HandlerKey handlerKey) {
        return mappings.get(handlerKey);
    }
}
