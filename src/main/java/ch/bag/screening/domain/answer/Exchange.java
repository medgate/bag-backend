package ch.bag.screening.domain.answer;

import ch.bag.screening.domain.node.AnswerId;
import ch.bag.screening.domain.node.QuestionId;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Exchange {
  private final QuestionId questionId;
  private final AnswerId answerId;
}
