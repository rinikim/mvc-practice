package org.example;

import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.example.model.User;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Controller 이 설정돼있는 모든 클래스를 찾아서 출력한다.
 */
public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void controllerScan() {
        // org.example 패키지를 기준으로 아래의 @Controller 를 모두 찾을 것이다.
        Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class, Service.class));
    }

    private Set<Class<?>> getTypesAnnotatedWith(List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("org.example");
        Set<Class<?>> beans = new HashSet<>();
        annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));

        logger.debug("beans: [{}]", beans);
        return beans;
    }

    @Test
    void showClass() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        logger.debug("User all declared fields: [{}]", Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        logger.debug("User all declared constructors: [{}]", Arrays.stream(clazz.getDeclaredConstructors()).collect(Collectors.toList()));
        logger.debug("User all declared methods: [{}]", Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList()));
    }

    @Test
    void load() throws ClassNotFoundException {
        // 클레스타입 객체 가져오는 방법 3가지
        // 1. 힙 영역에 로드되어있는 클래스타입의 객체를 가져온다.
        Class<User> clazz = User.class;

        // 2. 객체를 우선 생성하여 instance.getClass()를 사용하여 객체를 가져온다.
        User user = new User("tytntu", "김혜린");
        Class<? extends User> clazz2 = user.getClass();

        // 3. Class.forName("클래스 경로")
        Class<?> clazz3 = Class.forName("org.example.model.User");

        logger.debug("clazz : [{}]", clazz);
        logger.debug("clazz2 : [{}]", clazz2);
        logger.debug("clazz3 : [{}]", clazz3);

        assertThat(clazz == clazz2).isTrue();
        assertThat(clazz3 == clazz2).isTrue();
        assertThat(clazz3 == clazz).isTrue();

    }
}
