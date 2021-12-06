package rc.bootsecurity.controller;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import rc.bootsecurity.Exception.BankTransactionException;
import rc.bootsecurity.db.BankAccountDAO;
import rc.bootsecurity.model.BankAccountInfo;
import rc.bootsecurity.model.SendMoneyForm;
import rc.bootsecurity.model.Test;
import rc.bootsecurity.validator.ObjectValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("bankaccount")
public class BankAccountController {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);
    @Autowired
    private BankAccountDAO bankAccountDAO;

    @Autowired
    private ObjectValidator objectValidator;

    private static String UPLOADED_FOLDER = "D://temp//";

    @RequestMapping(value = "/accountpage", method = RequestMethod.GET)
    public String showBankAccounts(Model model) {
        List<BankAccountInfo> list = bankAccountDAO.getBankAccounts();

        model.addAttribute("accountInfos", list);

        return "bankaccount/accountsPage";
    }

    @RequestMapping(value = "/sendMoney", method = RequestMethod.GET)
    public String viewSendMoneyPage(Model model) {
        SendMoneyForm form = new SendMoneyForm(1L, 2L, 700d);
        model.addAttribute("sendMoneyForm", form);
        return "bankaccount/sendMoneyPage";
    }

    @RequestMapping(value = "/sendMoney", method = RequestMethod.POST)
    public String processSendMoney(Model model, SendMoneyForm sendMoneyForm) {
        /*ValidationErrorResponse va =null;
                va= objectValidator.validate(sendMoneyForm, SendMoneyForm.class);
                if (va!=null)
                {
                        System.out.println(va.getViolations().get(0).getMessage().toString());
                }*/
        System.out.println("Send Money::" + sendMoneyForm.getAmount());
        try {

            bankAccountDAO.sendMoney(sendMoneyForm.getFromAccountId(), //
                    sendMoneyForm.getToAccountId(), //
                    sendMoneyForm.getAmount());
        } catch (BankTransactionException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "bankaccount/sendMoneyPage";
        }
        return "redirect:/bankaccount/accountpage";
    }


    @RequestMapping(value = "/uploadexcel", method = RequestMethod.GET)
    public String viewUploadExcel(Model model) {
        logger.info(">>> Inside Upload Excel View Page <<<");
        return "bankaccount/uploadExcelPage";
    }


    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ModelAndView mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile, Model model) throws IOException {
        List<Test> tempStudentList = new ArrayList<Test>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Test tempStudent = new Test();
            XSSFRow row = worksheet.getRow(i);
            tempStudent.setId((int) row.getCell(0).getNumericCellValue());
            tempStudent.setContent(row.getCell(1).getStringCellValue());
            tempStudentList.add(tempStudent);
        }
        for (Test t1 :  tempStudentList) {
            logger.info(t1.getId() + "");
            logger.info(t1.getContent());
        }

        //File Upload Logic
        try {
            // Get the file and save it somewhere
            byte[] bytes = reapExcelDataFile.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + reapExcelDataFile.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ModelAndView mav =new ModelAndView();
        mav.setViewName("bankaccount/uploadExcelPage");
        mav.addObject("errorMessage","Upload Succesfully");//return new ModelAndView("/index");
        return  mav;
    }

}
