package rc.bootsecurity.model;

import java.util.List;

public class DownloadTemplate {
    private String moduleName;
    private List<String> moduleTasks;

    public DownloadTemplate(String moduleName, List<String> moduleTasks) {
        this.moduleName = moduleName;
        this.moduleTasks = moduleTasks;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<String> getModuleTasks() {
        return moduleTasks;
    }

    public void setModuleTasks(List<String> moduleTasks) {
        this.moduleTasks = moduleTasks;
    }

}
