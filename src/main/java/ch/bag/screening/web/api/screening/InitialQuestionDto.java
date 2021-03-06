package ch.bag.screening.web.api.screening;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitialQuestionDto {
  private String version;
  private String initialNodeId;
  private NodeDto node;
  private Optional<RecommendationDto> recommendation;
}
