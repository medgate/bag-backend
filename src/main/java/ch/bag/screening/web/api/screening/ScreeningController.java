package ch.bag.screening.web.api.screening;

import static ch.bag.screening.web.api.profile.UserProfileMapper.toUserLocale;
import static ch.bag.screening.web.api.profile.UserProfileMapper.toUserProfile;
import static ch.bag.screening.web.api.screening.ScreeningMapper.toInitialQuestionDto;
import static ch.bag.screening.web.api.screening.ScreeningMapper.toNodeDto;
import static ch.bag.screening.web.api.screening.ScreeningMapper.toPartialUserAnswer;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import ch.bag.screening.domain.profile.Gender;
import ch.bag.screening.domain.profile.SwissCanton;
import ch.bag.screening.domain.profile.UserProfile;
import ch.bag.screening.domain.screening.PartialUserAnswer;
import ch.bag.screening.domain.screening.ScreeningService;
import ch.bag.screening.web.api.profile.UserProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/screening/answers", produces = APPLICATION_JSON_VALUE)
@Slf4j
public class ScreeningController {

  private final ScreeningService screeningService;

  @Deprecated
  @GetMapping("/first")
  public InitialQuestionDto getInitialQuestion(@RequestParam("lang") final String language) {
    final UserProfile profile = buildDefaultProfileForInitialQuestion(language);
    return toInitialQuestionDto(screeningService.findInitialQuestion(profile));
  }

  // To keep API retro-compatible, provide default profile, which does not matter for adult tree
  @Deprecated
  private static UserProfile buildDefaultProfileForInitialQuestion(final String language) {
    return UserProfile.builder()
        .age(22)
        .gender(Gender.OTHER)
        .locale(toUserLocale(language))
        .canton(SwissCanton.BERNE)
        .build();
  }

  @PostMapping("/first")
  public InitialQuestionDto postUserProfileForInitialQuestion(
      @RequestBody final UserProfileDto userProfileDto) {
    final UserProfile userProfile = toUserProfile(userProfileDto);
    return toInitialQuestionDto(screeningService.findInitialQuestion(userProfile));
  }

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public NodeDto postUserAnswer(@RequestBody final PartialUserAnswerDto answerDto) {
    final PartialUserAnswer userAnswer = toPartialUserAnswer(answerDto);
    return toNodeDto(screeningService.answerScreeningQuestion(userAnswer));
  }
}
