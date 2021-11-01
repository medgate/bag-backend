package ch.bag.screening.web.api.answer;

import static ch.bag.screening.web.api.profile.UserProfileMapper.toUserProfile;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import ch.bag.screening.domain.answer.CompletedUserAnswer;
import ch.bag.screening.domain.answer.Exchange;
import ch.bag.screening.domain.node.AnswerId;
import ch.bag.screening.domain.node.QuestionId;
import ch.bag.screening.domain.node.RecommendationId;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class UserAnswerMapper {

  static CompletedUserAnswer toCompletedUserAnswer(final CompletedUserAnswerDto answer) {
    return CompletedUserAnswer.builder()
        .version(trimToEmpty(answer.getVersion()))
        .profile(toUserProfile(answer.getProfile()))
        .exchanges(toExchanges(answer.getExchanges()))
        .recommendationId(RecommendationId.fromString(answer.getRecommendationId()))
        .build();
  }

  private static List<Exchange> toExchanges(final List<ExchangeDto> exchanges) {
    return emptyIfNull(exchanges).stream()
        .filter(Objects::nonNull)
        .map(UserAnswerMapper::toExchange)
        .collect(toList());
  }

  private static Exchange toExchange(final ExchangeDto exchange) {
    return Exchange.builder()
        .questionId(QuestionId.fromString(exchange.getQuestionId()))
        .answerId(AnswerId.fromString(exchange.getAnswerId()))
        .build();
  }
}
