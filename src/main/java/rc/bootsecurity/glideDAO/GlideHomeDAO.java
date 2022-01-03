package rc.bootsecurity.glideDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rc.bootsecurity.glideModel.GlideHomeModel;
import rc.bootsecurity.glideModel.GlideHomeSheets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GlideHomeDAO {

    private static final Logger logger = LoggerFactory.getLogger(GlideHomeDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Page<GlideHomeModel> findAll(Pageable page) {
        /*Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Order.by("ID");
        List<Post> users = jdbcTemplate.query("SELECT * FROM USER ORDER BY " + order.getProperty() + " "
                        + order.getDirection().name() + " LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset(),
                (rs, rowNum) -> mapUserResult(rs));*/
        /*return null;*/
        try {
            logger.info(">>> Start GlideHomeModel DAO <<<");
            Sort.Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Sort.Order.by("ID");
            List<GlideHomeModel> users = jdbcTemplate.query("select xtt.TRACK_ID as TRACK_ID,xwmt.workbook_id as WORKBOOK_ID,xtt.TRACK_NAME as TRACK_NAME,xwmt.WORKBOOK_NAME as WORKBOOK_NAME from xxecs_track_t xtt  join xxecs_workbook_master_t xwmt on xtt.track_id=xwmt.track_id ORDER BY " + order.getProperty() + " "
                            + order.getDirection().name() + " OFFSET " + page.getOffset() + " ROWS FETCH NEXT " + page.getPageSize() + " ROWS ONLY",
                    (rs, rowNum) -> mapUserResult(rs));

            for(int i=0;i<=users.size()-1;i++) {
                Long id =users.get(i).getWorkbook_id();
                List<GlideHomeSheets> ghs=getHomeSheets(id);
                users.get(i).setGlideHomeSheetsList(ghs);
            }
            logger.info(">>> End GlideHomeModel DAO <<<");
            return new PageImpl<GlideHomeModel>(users, page, count());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return null;
    }

    private GlideHomeModel mapUserResult(final ResultSet rs) throws SQLException {
        GlideHomeModel post = new GlideHomeModel();
        post.setTrack_id(rs.getLong("TRACK_ID"));
        post.setTrack_name(rs.getString("TRACK_NAME"));
        post.setWorkbook_name(rs.getString("WORKBOOK_NAME"));
        post.setWorkbook_id(rs.getLong("WORKBOOK_ID"));
        return post;
    }
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from xxecs_track_t xtt  join xxecs_workbook_master_t xwmt on xtt.track_id=xwmt.track_id", Integer.class);
    }
    private List<GlideHomeSheets> getHomeSheets(Long Id)
    {
        List<GlideHomeSheets> UserLists = jdbcTemplate.query("SELECT\n" +
                        "    xtt.track_id       AS TRACK_ID,\n" +
                        "    xtt.track_name     AS TRACK_NAME,\n" +
                        "    xwmt.workbook_name AS WORKBOOK_NAME,\n" +
                        "    xwmt.workbook_id as WORKBOOK_ID,\n"+
                        "    xwst.sheet_name AS SHEET_NAME,\n" +
                        "    xwst.sheet_code AS SHEET_CODE,\n" +
                        "    xwst.sheet_id AS SHEET_ID\n" +
                        "FROM\n" +
                        "    xxecs_track_t xtt\n" +
                        "    INNER JOIN xxecs_workbook_master_t xwmt\n" +
                        "    ON xtt.track_id = xwmt.track_id\n" +
                        "    INNER JOIN XXECS_WORKBOOK_SHEETS_T XWST ON xwst.workbook_id=xwmt.workbook_id  WHERE xwmt.workbook_id ='"+Id+"' ORDER BY xwst.sheet_order asc" ,
                (rs, rowNum) -> mapUserList(rs));
        return UserLists;

    }


    private GlideHomeSheets mapUserList(final ResultSet rs) throws SQLException {
        GlideHomeSheets post = new GlideHomeSheets();
        post.setTrack_id(rs.getLong("TRACK_ID"));
        post.setTrack_name(rs.getString("TRACK_NAME"));
        post.setWorkbook_name(rs.getString("WORKBOOK_NAME"));
        post.setWorkbook_id(rs.getLong("WORKBOOK_ID"));
        post.setSheet_name(rs.getString("SHEET_NAME"));
        post.setSheet_code(rs.getString("SHEET_CODE"));
        post.setSheet_id(rs.getLong("SHEET_ID"));
        return post;
    }

}
