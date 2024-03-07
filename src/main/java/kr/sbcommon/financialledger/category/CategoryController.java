package kr.sbcommon.financialledger.category;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @GetMapping
    public String category() {
        return "category/category";
    }

}
