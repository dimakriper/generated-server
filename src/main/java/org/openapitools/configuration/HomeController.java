package org.openapitools.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home controller
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "index.html"; // This will look for index.html in resources/templates or resources/static
    }

    // You can keep Swagger UI accessible at a different path
    @RequestMapping("/api-docs")
    public String apiDocs() {
        return "redirect:swagger-ui.html";
    }
}