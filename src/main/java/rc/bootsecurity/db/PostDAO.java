package rc.bootsecurity.db;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rc.bootsecurity.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Page<Post> findAll(Pageable page) {
        /*Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Order.by("ID");
        List<Post> users = jdbcTemplate.query("SELECT * FROM USER ORDER BY " + order.getProperty() + " "
                        + order.getDirection().name() + " LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset(),
                (rs, rowNum) -> mapUserResult(rs));*/
        /*return null;*/
        Sort.Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Sort.Order.by("ID");
        List<Post> users = jdbcTemplate.query("SELECT * FROM post ORDER BY " + order.getProperty() + " "
                        + order.getDirection().name() + " OFFSET " + page.getOffset() + " ROWS FETCH NEXT " + page.getPageSize() + " ROWS ONLY",
                (rs, rowNum) -> mapUserResult(rs));

        return new PageImpl<Post>(users, page, count());
    }

    private Post mapUserResult(final ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setId(rs.getLong("ID"));
        post.setTitle(rs.getString("TITTLE"));
        post.setBody(rs.getString("BODY"));
        return post;
    }

    public int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM post", Integer.class);
    }

}
