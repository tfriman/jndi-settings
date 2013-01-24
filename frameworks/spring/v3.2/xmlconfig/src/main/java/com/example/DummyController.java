package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("messageFromJndi")
    private String result;

    @RequestMapping("/")
    public
    @ResponseBody
    String handle() {
        return result;
    }
}
