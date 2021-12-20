package rc.bootsecurity.model;

public class WorkBookTask {
    private int workbookId;
    private String workbookName;
    private String trackName;
    private String whoCols;
    private String isActive;
    private String end_Date;


    public int getWorkbookId() {
        return workbookId;
    }

    public void setWorkbookId(int workbookId) {
        this.workbookId = workbookId;
    }

    public String getWorkbookName() {
        return workbookName;
    }

    public void setWorkbookName(String workbookName) {
        this.workbookName = workbookName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getWhoCols() {
        return whoCols;
    }

    public void setWhoCols(String whoCols) {
        this.whoCols = whoCols;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(String end_Date) {
        this.end_Date = end_Date;
    }

    public WorkBookTask(int workbookId, String workbookName, String trackName, String whoCols, String isActive, String end_Date) {
        this.workbookId = workbookId;
        this.workbookName = workbookName;
        this.trackName = trackName;
        this.whoCols = whoCols;
        this.isActive = isActive;
        this.end_Date = end_Date;
    }
}
