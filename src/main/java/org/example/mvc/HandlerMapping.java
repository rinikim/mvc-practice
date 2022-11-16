package org.example.mvc;

import org.example.controller.HandlerKey;
import org.example.mvc.controller.Controller;


public interface HandlerMapping {
    Object findHandler(HandlerKey handlerKey);  // Controller 가 아닌 Object 로 받아준다. (애노테이션 형태로 만들 것이기 때문에)
}
