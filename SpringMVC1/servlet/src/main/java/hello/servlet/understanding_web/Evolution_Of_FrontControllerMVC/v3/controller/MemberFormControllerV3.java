package hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3.controller;

import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.ModelView;
import hello.servlet.understanding_web.Evolution_Of_FrontControllerMVC.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
    }
}
