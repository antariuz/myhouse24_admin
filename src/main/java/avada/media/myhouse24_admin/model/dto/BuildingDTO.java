package avada.media.myhouse24_admin.model.dto;

import avada.media.myhouse24_admin.model.Floor;
import avada.media.myhouse24_admin.model.Section;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDTO {

    private Long id;
    private String title;
    private String address;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private List<Section> sections;
    private List<Floor> floors;
    private List<StaffDTO> staffList;

}