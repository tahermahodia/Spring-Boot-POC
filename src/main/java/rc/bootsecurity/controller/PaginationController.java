package rc.bootsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import rc.bootsecurity.service.PostService;

@Controller
@RequestMapping("pagination")
public class PaginationController {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);

    @Autowired
    private PostService postService;


    @RequestMapping(value = "/pagelist", method = RequestMethod.GET)
    public String pageList(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                           @RequestParam(value = "size", required = false, defaultValue = "5") int size, Model model) {
        model.addAttribute("posts", postService.getPage(pageNumber, size));
        //return "posts";
        return "pagination/pagelist";
    }
}
