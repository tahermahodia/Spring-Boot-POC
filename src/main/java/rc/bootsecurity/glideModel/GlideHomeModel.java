package rc.bootsecurity.glideModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GlideHomeModel {

    private Long track_id;
    private String track_name;
    private String workbook_name;
    private Long workbook_id;
    private List<GlideHomeSheets> glideHomeSheetsList;



}
