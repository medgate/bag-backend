package ch.bag.screening.web.api.screening;

import static ch.bag.screening.web.api.profile.UserProfileMapper.toUserProfile;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import ch.bag.screening.domain.node.Answer;
import ch.bag.screening.domain.node.Node;
import ch.bag.screening.domain.node.NodeId;
import ch.bag.screening.domain.node.Recommendation;
import ch.bag.screening.domain.screening.InitialQuestion;
import ch.bag.screening.domain.screening.PartialUserAnswer;
import java.util.List;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public final class ScreeningMapper {

  static InitialQuestionDto toInitialQuestionDto(final InitialQuestion response) {
    if (response.getRecommendation() != null && response.getRecommendation().isPresent()) {
      Optional<RecommendationDto> recommendation =
          Optional.of(toRecommendationDto(response.getRecommendation().get()));
      return InitialQuestionDto.builder()
          .version(response.getVersion())
          .initialNodeId(null)
          .node(null)
          .recommendation(recommendation)
          .build();
    } else {
      Optional<RecommendationDto> recommendation = Optional.empty();
      return InitialQuestionDto.builder()
          .version(response.getVersion())
          .initialNodeId(response.getInitialNodeId().toString())
          .node(toNodeDto(response.getNode()))
          .recommendation(recommendation)
          .build();
    }
  }

  static NodeDto toNodeDto(final Node node) {
    return NodeDto.builder()
        .questionId(node.getQuestionId().toString())
        .question(node.getQuestion())
        .description(node.getDescription())
        .answers(toAnswerDtos(node.getAnswers()))
        .build();
  }

  private static List<AnswerDto> toAnswerDtos(final List<Answer> answers) {
    return emptyIfNull(answers).stream().map(ScreeningMapper::toAnswerDto).collect(toList());
  }

  private static AnswerDto toAnswerDto(final Answer answer) {
    return AnswerDto.builder()
        .id(answer.getId().toString())
        .type(answer.getType())
        .text(answer.getText())
        .nextNodeId(answer.getNextNodeId().toString())
        .recommendation(toRecommendationDto(answer.getRecommendation()))
        .build();
  }

  private static RecommendationDto toRecommendationDto(final Recommendation recommendation) {
    if (recommendation == null) {
      return null;
    }
    return RecommendationDto.builder()
        .id(recommendation.getId().toString())
        .title(recommendation.getTitle())
        .description(recommendation.getDescription())
        .medgateTitle(recommendation.getMedgateTitle())
        .medgateDescription(recommendation.getMedgateDescription())
        .severity(recommendation.getSeverity())
        .build();
  }

  static PartialUserAnswer toPartialUserAnswer(final PartialUserAnswerDto answer) {
    return PartialUserAnswer.builder()
        .version(trimToEmpty(answer.getVersion()))
        .profile(toUserProfile(answer.getProfile()))
        .nodeIds(toNodesIds(answer.getNodeIds()))
        .build();
  }

  private static List<NodeId> toNodesIds(final List<String> nodeIds) {
    return emptyIfNull(nodeIds).stream()
        .filter(StringUtils::isNotBlank)
        .map(NodeId::fromString)
        .collect(toList());
  }
}
