package softuni.exam.instagraphlite.models.dto.importDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PictureImportDto {
    @NotNull
    private String path;

    @Min(500)
    @Max(60000)
    @NotNull
    private double size;
}
