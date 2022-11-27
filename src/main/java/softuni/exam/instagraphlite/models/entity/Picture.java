package softuni.exam.instagraphlite.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pictures")
@Getter
@Setter
@AllArgsConstructor
public class Picture extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String path;

    @Column(nullable = false)
    @Min(500)
    @Max(60000)
    private double size;
    @OneToMany(targetEntity = User.class, mappedBy = "picture")
    private List<User> users;

    @OneToMany(targetEntity = Post.class, mappedBy = "picture")
    private List<Post> posts;

    public Picture() {
        users = new ArrayList<>();
        posts = new ArrayList<>();
    }
}
