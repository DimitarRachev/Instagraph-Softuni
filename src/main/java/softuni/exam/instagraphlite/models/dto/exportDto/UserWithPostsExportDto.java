package softuni.exam.instagraphlite.models.dto.exportDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserWithPostsExportDto {
  private String username;
  private List<PostExportDto> posts;

  public UserWithPostsExportDto() {
    posts =new ArrayList<PostExportDto>();
  }

  @Override public String toString() {
    return "User: " + username + "\n" +
      "Post count: " + posts.size() + "\n" +
      posts
        .stream()
        .sorted(Comparator.comparingDouble(PostExportDto::getPictureSize))
        .map(PostExportDto::toString)
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
