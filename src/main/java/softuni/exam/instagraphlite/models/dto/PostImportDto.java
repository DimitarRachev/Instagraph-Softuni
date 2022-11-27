package softuni.exam.instagraphlite.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "post")
public class PostImportDto {
    @XmlElement
    @Size(min = 21)
    private String caption;
    @XmlElement
    private UserImportInXmlDto user;

    @XmlElement
    private PictureImportInXmlDto picture;

}
