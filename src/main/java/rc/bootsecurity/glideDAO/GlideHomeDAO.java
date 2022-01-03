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

import java.sql.ResultSet;
import java.sql.SQLException;
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
        Sort.Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Sort.Order.by("ID");
        List<GlideHomeModel> users = jdbcTemplate.query("select xtt.TRACK_ID as TRACK_ID,xtt.TRACK_NAME as TRACK_NAME,xwmt.WORKBOOK_NAME as WORKBOOK_NAME from xxecs_track_t xtt  join xxecs_workbook_master_t xwmt on xtt.track_id=xwmt.track_id ORDER BY " + order.getProperty() + " "
                        + order.getDirection().name() + " OFFSET " + page.getOffset() + " ROWS FETCH NEXT " + page.getPageSize() + " ROWS ONLY",
                (rs, rowNum) -> mapUserResult(rs));

        return new PageImpl<GlideHomeModel>(users, page, count());
    }

    private GlideHomeModel mapUserResult(final ResultSet rs) throws SQLException {
        GlideHomeModel post = new GlideHomeModel();
        post.setTrack_id(rs.getLong("TRACK_ID"));
        post.setTrack_name(rs.getString("TRACK_NAME"));
        post.setWorkbook_name(rs.getString("WORKBOOK_NAME"));
        return post;
    }

    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from xxecs_track_t xtt  join xxecs_workbook_master_t xwmt on xtt.track_id=xwmt.track_id", Integer.class);
    }
}
