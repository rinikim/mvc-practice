package org.example.controller;

import org.example.model.User;
import org.example.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserCreateController implements org.example.mvc.controller.Controller {
    @Override
    public String handlerRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserRepository.save(new User(request.getParameter("userId"), request.getParameter("name")));
        return "redirect:/users";
    }
}
