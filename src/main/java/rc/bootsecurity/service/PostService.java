package rc.bootsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rc.bootsecurity.db.PostDAO;
import rc.bootsecurity.model.Post;
import rc.bootsecurity.paging.Paged;
import rc.bootsecurity.paging.Paging;

@Service
public class PostService {

    @Autowired
    private PostDAO postDAO;


    public Paged<Post> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.Direction.ASC, "id");
        Page<Post> postPage = postDAO.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }
}
