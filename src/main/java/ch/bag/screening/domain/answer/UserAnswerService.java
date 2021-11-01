package ch.bag.screening.domain.answer;

import static ch.bag.screening.domain.answer.UserAnswerValidator.validateCompletedUserAnswer;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import ch.bag.screening.domain.profile.Tenant;
import ch.bag.screening.domain.screening.ScreeningProperties;
import java.time.Clock;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAnswerService {

  private final Clock clock;
  private final StatisticsStore statisticsStore;
  private final ScreeningProperties screeningProperties;

  public CompletedUserAnswer saveUserScreeningAnswers(
      final CompletedUserAnswer userAnswer, final boolean allowEmptyExchanges) {
    validateCompletedUserAnswer(userAnswer, allowEmptyExchanges);
    logProvidedRecommendation(userAnswer);
    final Tenant tenant = findCurrentTenant();
    final CompletedUserAnswer answer = userAnswer.withTenant(tenant).withSubmittedAt(now());
    statisticsStore.saveScreeningStatistics(answer);
    return answer;
  }

  private Tenant findCurrentTenant() {
    return defaultIfNull(screeningProperties.getTenant(), Tenant.BAG);
  }

  private static void logProvidedRecommendation(final CompletedUserAnswer userAnswer) {
    LOG.info(
        "Provided user recommendation for {} was {}",
        userAnswer.getVersion(),
        userAnswer.getRecommendationId());
  }

  private Instant now() {
    return clock.instant();
  }
}
