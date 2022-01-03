package rc.bootsecurity.glideController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import rc.bootsecurity.controller.BankAccountController;
import rc.bootsecurity.glideService.GlideHomeService;
import rc.bootsecurity.service.PostService;

import javax.jws.WebParam;

@Controller
@RequestMapping("glidepages")
public class GlideHomeController {

    private static final Logger logger = LoggerFactory.getLogger(GlideHomeController.class);

    @Autowired
    private GlideHomeService glideHomeService;

    @RequestMapping(value = "/pagelist", method = RequestMethod.GET)
    public String pageList(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                           @RequestParam(value = "size", required = false, defaultValue = "5") int size, Model model) {

        logger.info(">>> Inside Glide Home Page Controller <<<");
        model.addAttribute("posts", glideHomeService.getPage(pageNumber, size));
        //return "posts";
        return "glidepages/pagelist";
    }


    @RequestMapping(value = "/downloadtemplate",method = RequestMethod.GET)
    public String downloadWorkBookTemplate(@RequestParam(value = "workBookName", required = true) String workBookName,Model model)
    {
        logger.info(">>>>> "+workBookName.toString()+"");
        return null;
    }



}
