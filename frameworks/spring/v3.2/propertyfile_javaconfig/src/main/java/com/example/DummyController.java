package com.example;

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

    @Value("${example.foo:this is default value}")
    private String aProperty;

    @RequestMapping("/")
    public
    @ResponseBody
    String handle() {
        return aProperty;
    }
}
