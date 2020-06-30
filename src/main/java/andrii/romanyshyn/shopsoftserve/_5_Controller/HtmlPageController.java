package andrii.romanyshyn.shopsoftserve._5_Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlPageController {
    @RequestMapping("/categories")
    public String categories() {
        return "categories.html";
    }

    @RequestMapping("/productCatalog")
    public String productCatalog() {
        return "productCatalog.html";
    }

    @RequestMapping("/persons")
    public String persons() {
        return "persons.html";
    }

    @RequestMapping("/personDetails")
    public String personDetails() {
        return "personDetails.html";
    }

    @RequestMapping("/productMarket")
    public String productMarket() {
        return "productMarket.html";
    }
}