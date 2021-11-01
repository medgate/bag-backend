package ch.bag.screening.web.api.profile;

import ch.bag.screening.domain.profile.Gender;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

  private int age;
  private Gender gender;
  private String canton;
  private String language;
  private Optional<List<String>> symptoms;
}
