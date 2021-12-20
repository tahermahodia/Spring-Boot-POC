package rc.bootsecurity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import rc.bootsecurity.Exception.BankTransactionException;
import rc.bootsecurity.db.BankAccountDAO;
import rc.bootsecurity.model.BankAccountInfo;
import rc.bootsecurity.model.SendMoneyForm;
import rc.bootsecurity.model.Test;
import rc.bootsecurity.validator.ObjectValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
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

    static Sheet sheet = null;

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

        //Pojo Validation logic to validate the pojo and return the list.
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
    public ModelAndView viewUploadExcel(Model model) {
        logger.info(">>> Inside Upload Excel View Page <<<");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("bankaccount/uploadExcelPage");
        return mav;
    }

    @RequestMapping(value="/downloadTemplate",method = RequestMethod.GET)
    public ModelAndView downloadTemplateGet(Model model)
    {
        ModelAndView mav = new ModelAndView();
        List<String> ModuleList = new ArrayList<String>();
        ModuleList=bankAccountDAO.getModuleName();
        model.addAttribute("moduleName",ModuleList);
        mav.setViewName("bankaccount/downloadTemplate");
        return  mav;
    }



    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ModelAndView mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile, Model model) throws IOException {
        //Created First Branch to understand the git.
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

        Workbook workbook1 = new XSSFWorkbook();
        sheet = workbook1.createSheet("Data Validation");
        sheet.createRow(0).createCell(3).setCellValue("Col 3");
        sheet.createRow(1).createCell(0).setCellValue("Row 1");
        sheet.createRow(10).createCell(0).setCellValue("Row 10");
        sheet.createRow(15).createCell(0).setCellValue("Row 15");
        sheet.createRow(25).createCell(0).setCellValue("Row 25");

        addMyValidation(1, 10, 3, 3, new String[]{"One", "Two", "Three"});
        addMyValidation(15, 25, 3, 3, new String[]{"A", "B", "C"});

        FileOutputStream out = new FileOutputStream("d:\\CreateExcelDataValidation.xlsx");
        workbook1.write(out);
        workbook1.close();
        out.close();






        ModelAndView mav =new ModelAndView();
        mav.setViewName("bankaccount/uploadExcelPage");
        mav.addObject("errorMessage","Upload Succesfully");//return new ModelAndView("/index");
        return  mav;
    }


    static boolean addMyValidation(int firstRow, int lastRow, int firstCol, int lastCol, String[] listOfValue) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createExplicitListConstraint(listOfValue);
        CellRangeAddressList range = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        DataValidation validation = helper.createValidation(constraint, range);

        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.setSuppressDropDownArrow(true);
        validation.setEmptyCellAllowed(false);
        validation.setShowPromptBox(true);
        validation.setShowErrorBox(true);

        sheet.addValidationData(validation);

        return true;
    }

    @RequestMapping(value = "/testAjax", method = RequestMethod.POST)
    @ResponseBody
    public String TestAjax(@RequestBody  String model) {
        return "TestAjax";
    }




    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource>
    download(@RequestParam String param) throws IOException {

        File file = new File(UPLOADED_FOLDER + param);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource =
                new ByteArrayResource(Files.readAllBytes(path));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename="+param);
        header.add("Cache-Control",
                "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok().headers(header).
                contentLength(file.length())
                .contentType(MediaType.
                        parseMediaType("application/octet-stream")).
                body(resource);
    }



}
