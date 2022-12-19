package avada.media.myhouse24_admin.model.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SelectResponse {

    private List<Result> results = new ArrayList<>();
    private Long itemsCount;

    @Data
    public static class Result {

        private Long id;
        private String text;

        public void setText(String lastname, String firstname, String middleName) {
            StringBuilder fullName = new StringBuilder();
            if (lastname != null && !lastname.equals("")) fullName.append(lastname);
            if (firstname != null && !firstname.equals("")) {
                if (fullName.length() > 0) fullName.append(" ").append(firstname);
                else fullName.append(firstname);
            }
            if (middleName != null && !middleName.equals("")) {
                if (fullName.length() > 0) fullName.append(" ").append(middleName);
                else fullName.append(middleName);
            }
            this.text = fullName.toString();
        }

        public void setText(String title) {
            StringBuilder text = new StringBuilder();
            if (title != null && !title.equals("")) text.append(title);
            this.text = text.toString();
        }

    }

}
