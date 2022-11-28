package softuni.exam.instagraphlite.models.dto.exportDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureExportDto {
  private double size;
  private String path;
}
