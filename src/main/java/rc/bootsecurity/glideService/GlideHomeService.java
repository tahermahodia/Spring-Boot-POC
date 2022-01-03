package rc.bootsecurity.glideService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rc.bootsecurity.db.PostDAO;
import rc.bootsecurity.glideController.GlideHomeController;
import rc.bootsecurity.glideDAO.GlideHomeDAO;
import rc.bootsecurity.glideModel.GlideHomeModel;
import rc.bootsecurity.model.Post;
import rc.bootsecurity.paging.Paged;
import rc.bootsecurity.paging.Paging;

@Service
public class GlideHomeService {

    private static final Logger logger = LoggerFactory.getLogger(GlideHomeService.class);

    @Autowired
    private GlideHomeDAO glideHomeDAO;

    public Paged<GlideHomeModel> getPage(int pageNumber, int size) {
        logger.info(">>> Inside Glide Home Service <<<");
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.Direction.ASC, "TRACK_ID");
        Page<GlideHomeModel> postPage = glideHomeDAO.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }
}
