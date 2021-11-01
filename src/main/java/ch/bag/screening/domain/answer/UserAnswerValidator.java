package ch.bag.screening.domain.answer;

import static ch.bag.screening.domain.answer.UserAnswerError.EMPTY_SCREENING_EXCHANGES;
import static ch.bag.screening.domain.answer.UserAnswerError.EMPTY_SCREENING_PROFILE;
import static ch.bag.screening.domain.answer.UserAnswerError.EMPTY_SCREENING_PROFILE_CANTON;
import static ch.bag.screening.domain.answer.UserAnswerError.EMPTY_SCREENING_PROFILE_GENDER;
import static ch.bag.screening.domain.answer.UserAnswerError.EMPTY_SCREENING_PROFILE_SYMPTOMS;
import static ch.bag.screening.domain.answer.UserAnswerError.EMPTY_SCREENING_RECOMMENDATION;
import static ch.bag.screening.domain.answer.UserAnswerError.EMPTY_SCREENING_VERSION;
import static ch.bag.screening.domain.answer.UserAnswerError.INVALID_SCREENING_AGE;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;

import ch.bag.screening.domain.error.ValidationException;
import ch.bag.screening.domain.profile.UserProfile;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserAnswerValidator {

  private static final int MAX_PROFILE_AGE = 120; // years
  private static final int MAX_AGE_WITHOUT_SYMPTOMS = 11; // years

  static void validateCompletedUserAnswer(
      final CompletedUserAnswer userAnswer, final boolean allowEmptyExchanges) {
    if (isBlank(userAnswer.getVersion())) {
      throw new ValidationException(EMPTY_SCREENING_VERSION);
    }
    if (!allowEmptyExchanges && isEmpty(userAnswer.getExchanges())) {
      throw new ValidationException(EMPTY_SCREENING_EXCHANGES);
    }
    if (userAnswer.getRecommendationId().isEmpty()) {
      throw new ValidationException(EMPTY_SCREENING_RECOMMENDATION);
    }
    validateProfile(userAnswer.getProfile());
  }

  public static void validateProfile(final UserProfile profile) {
    if (profile == null) {
      throw new ValidationException(EMPTY_SCREENING_PROFILE);
    }
    if (!isValidAge(profile.getAge())) {
      throw new ValidationException(INVALID_SCREENING_AGE, MAX_PROFILE_AGE);
    }
    if (profile.getGender() == null) {
      throw new ValidationException(EMPTY_SCREENING_PROFILE_GENDER);
    }
    if (profile.getCanton() == null) {
      throw new ValidationException(EMPTY_SCREENING_PROFILE_CANTON);
    }
    if (profile.getAge() > MAX_AGE_WITHOUT_SYMPTOMS && isEmpty(profile.getSymptoms())) {
      throw new ValidationException(EMPTY_SCREENING_PROFILE_SYMPTOMS);
    }
  }

  private static boolean isValidAge(final int age) {
    return age >= 0 && age <= MAX_PROFILE_AGE;
  }
}
