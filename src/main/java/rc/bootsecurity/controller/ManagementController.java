package rc.bootsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rc.bootsecurity.db.SalesDAO;
import rc.bootsecurity.model.Sale;

import java.util.List;

@Controller
@RequestMapping("management")
public class ManagementController {

    private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

    @Autowired
    private SalesDAO dao;

    @GetMapping("index")
    public String index(Model model) {
        logger.info(">>>>>>> Inside Management controller page <<<<<<<");
        List<Sale> listSale = dao.list();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.print(auth.getName());
        model.addAttribute("listSale", listSale);
        //return "index";
        return "management/index";
    }


    @RequestMapping("/new")
    public String showNewForm(Model model) {
        Sale sale = new Sale();
        model.addAttribute("sale", sale);
        return "management/new_form";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("sale") Sale sale) {
        dao.save(sale);
        return "redirect:/management/index";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id) {
        try {
            logger.info("Inside Edit Employee>>>>" + id);
            ModelAndView mav = new ModelAndView("management/edit_form");
            Sale sale = dao.get(id);
            mav.addObject("sale", sale);
            return mav;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("sale") Sale sale) {
        dao.update(sale);

        return "redirect:/management/index";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        dao.delete(id);
        return "redirect:/management/index";
    }
}
