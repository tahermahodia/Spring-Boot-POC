package rc.bootsecurity.mapper;

import org.springframework.jdbc.core.RowMapper;
import rc.bootsecurity.model.WorkBook;
import rc.bootsecurity.model.WorkBookSheet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkBookMapper implements RowMapper<WorkBook> {

    public static final String BASE_SQL //
            = "select tw.id as workbookid,tw.workbook_name,twt.id as task_id,twt.task_name from temp_workbook tw inner join temp_workbook_task twt on tw.id=twt.workbook_id";
    @Override
    public WorkBook mapRow(ResultSet rs, int rowNum) throws SQLException {
        WorkBook wb =new WorkBook();
        WorkBookSheet wbs =new WorkBookSheet();
            wb.setWorkbookName(rs.getString("WORKBOOK_NAME"));
            wb.setWorkbookId(rs.getInt("WORKBOOKID"));
            wbs.setWorkBookSheetId(rs.getInt("TASK_ID"));
            wbs.setWorkBookSheetName(rs.getString("TASK_NAME"));
            wb.setWorkBookSheet(wbs);
        return wb;
    }
    }
