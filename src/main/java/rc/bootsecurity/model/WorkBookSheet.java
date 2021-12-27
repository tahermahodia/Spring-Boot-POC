package rc.bootsecurity.model;

public class WorkBookSheet {
    private int workBookSheetId;
    private int workBookId;
    private String workBookSheetName;
    private String manualActivity;
    private String independent;
    private String dataValidationPro;
    private String whoCols;
    private String stageTableName;
    private String tableName;

    public WorkBookSheet() {

    }

    public int getWorkBookSheetId() {
        return workBookSheetId;
    }

    public void setWorkBookSheetId(int workBookSheetId) {
        this.workBookSheetId = workBookSheetId;
    }

    public int getWorkBookId() {
        return workBookId;
    }

    public void setWorkBookId(int workBookId) {
        this.workBookId = workBookId;
    }

    public String getWorkBookSheetName() {
        return workBookSheetName;
    }

    public void setWorkBookSheetName(String workBookSheetName) {
        this.workBookSheetName = workBookSheetName;
    }

    public String getManualActivity() {
        return manualActivity;
    }

    public void setManualActivity(String manualActivity) {
        this.manualActivity = manualActivity;
    }

    public String getIndependent() {
        return independent;
    }

    public void setIndependent(String independent) {
        this.independent = independent;
    }

    public String getDataValidationPro() {
        return dataValidationPro;
    }

    public void setDataValidationPro(String dataValidationPro) {
        this.dataValidationPro = dataValidationPro;
    }

    public String getWhoCols() {
        return whoCols;
    }

    public void setWhoCols(String whoCols) {
        this.whoCols = whoCols;
    }

    public String getStageTableName() {
        return stageTableName;
    }

    public void setStageTableName(String stageTableName) {
        this.stageTableName = stageTableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public WorkBookSheet(int workBookSheetId, int workBookId, String workBookSheetName, String manualActivity, String independent, String dataValidationPro, String whoCols, String stageTableName, String tableName) {
        this.workBookSheetId = workBookSheetId;
        this.workBookId = workBookId;
        this.workBookSheetName = workBookSheetName;
        this.manualActivity = manualActivity;
        this.independent = independent;
        this.dataValidationPro = dataValidationPro;
        this.whoCols = whoCols;
        this.stageTableName = stageTableName;
        this.tableName = tableName;
    }
}
