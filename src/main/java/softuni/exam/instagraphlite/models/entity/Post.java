package softuni.exam.instagraphlite.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity{
    @Column(nullable = false)
    @Size(min = 21)
    private String caption;
    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;
    @ManyToOne(targetEntity = Picture.class, optional = false)
    private Picture picture;
}
