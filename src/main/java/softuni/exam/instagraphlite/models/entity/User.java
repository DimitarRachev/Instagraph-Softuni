package softuni.exam.instagraphlite.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
public class User extends BaseEntity{

    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 18)
    private String username;
    @Column(nullable = false)
    @Size(min = 4)
    private String password;
    @ManyToOne(optional = false)
    private Picture picture;
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User() {
        posts = new ArrayList<>();
    }
}
