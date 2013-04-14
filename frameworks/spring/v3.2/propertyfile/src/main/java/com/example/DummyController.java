package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles / mapping.
 *
 * @author Timo Friman
 */
@Controller
public class DummyController {

    @Value("${example.bar:this is default value}")
    private String aProperty;

    @Autowired(required = false)
    @Qualifier("exampleProperty")
    private String anotherProperty = "default value";

    @RequestMapping("/")
    public
    @ResponseBody
    String handle() {
        return aProperty + " and " + anotherProperty;
    }
}
