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
    return
      String.format("==Post Details:%n" +
        "----Caption: %s%n" +
        "----Picture Size: %.2f", caption, pictureSize);
  }
}
