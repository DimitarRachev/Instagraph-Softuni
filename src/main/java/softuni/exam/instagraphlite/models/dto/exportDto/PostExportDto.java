package softuni.exam.instagraphlite.models.dto.exportDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostExportDto {
  private String caption;
  private double pictureSize;


  @Override public String toString() {
    return "==Post Details: \n" +
      "----Caption: " + caption + "\n" +
      "----Picture Size: " + pictureSize;
  }
}
