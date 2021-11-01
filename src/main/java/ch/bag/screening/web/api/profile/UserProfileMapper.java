package ch.bag.screening.web.api.profile;

import static ch.bag.screening.domain.language.Languages.DEFAULT_LOCALE;
import static ch.bag.screening.domain.language.Languages.isAvailableLocale;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import ch.bag.screening.domain.profile.SwissCanton;
import ch.bag.screening.domain.profile.Symptom;
import ch.bag.screening.domain.profile.UserProfile;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public final class UserProfileMapper {

  public static UserProfile toUserProfile(final UserProfileDto profile) {
    if (profile == null) {
      return null;
    }
    final List<Symptom> symptoms;
    if (profile.getSymptoms() != null && profile.getSymptoms().isPresent()) {
      symptoms = toSymptoms(profile.getSymptoms().get());
    } else {
      symptoms = toSymptoms(Collections.singletonList(Symptom.FEVER.toString()));
    }
    return UserProfile.builder()
        .age(profile.getAge())
        .gender(profile.getGender())
        .canton(SwissCanton.fromCode(profile.getCanton()))
        .locale(toUserLocale(profile.getLanguage()))
        .symptoms(symptoms)
        .build();
  }

  public static Locale toUserLocale(final String language) {
    final Locale locale = Locale.forLanguageTag(trimToEmpty(lowerCase(language)));
    return isAvailableLocale(locale) ? locale : DEFAULT_LOCALE;
  }

  public static List<Symptom> toSymptoms(final List<String> symptoms) {
    return emptyIfNull(symptoms).stream()
        .map(StringUtils::trimToEmpty)
        .map(Symptom::fromString)
        .filter(Objects::nonNull)
        .collect(toList());
  }
}
