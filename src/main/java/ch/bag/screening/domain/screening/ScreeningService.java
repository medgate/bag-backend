package ch.bag.screening.domain.screening;

import static ch.bag.screening.domain.answer.UserAnswerValidator.validateProfile;
import static java.time.Instant.now;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import ch.bag.screening.domain.answer.CompletedUserAnswer;
import ch.bag.screening.domain.answer.UserAnswerService;
import ch.bag.screening.domain.node.Answer;
import ch.bag.screening.domain.node.AnswerId;
import ch.bag.screening.domain.node.Node;
import ch.bag.screening.domain.node.NodeId;
import ch.bag.screening.domain.node.QuestionId;
import ch.bag.screening.domain.node.Recommendation;
import ch.bag.screening.domain.node.RecommendationId;
import ch.bag.screening.domain.profile.SwissCanton;
import ch.bag.screening.domain.profile.Symptom;
import ch.bag.screening.domain.profile.Tenant;
import ch.bag.screening.domain.profile.UserProfile;
import ch.bag.screening.storage.screening.JsonAnswer;
import ch.bag.screening.storage.screening.JsonAnswerNode;
import ch.bag.screening.storage.screening.JsonCantonalRecommendation;
import ch.bag.screening.storage.screening.JsonNode;
import ch.bag.screening.storage.screening.JsonQuestion;
import ch.bag.screening.storage.screening.JsonRecommendation;
import ch.bag.screening.storage.screening.JsonScreeningTree;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ScreeningService {

  private final UserAnswerService userAnswerService;

  // Age settings to decide which screening tree should be used
  private static final int KID_TREE_MAX_AGE = 11;
  private static final int CHILD_TREE_MAX_AGE = 14;
  private static final int JUNIOR_TREE_MAX_AGE = 17;
  private static final int SENIOR_TREE_MIN_AGE = 65;

  // Decision tree versions
  public static final String LATEST_KID_SCREENING_VERSION = "v4_kid";

  private static final String LATEST_CHILD_HIGH_SCREENING_VERSION = "v4_child_high";
  private static final String LATEST_CHILD_MEDIUM_SCREENING_VERSION = "v4_child_medium";
  private static final String LATEST_CHILD_LOW_SCREENING_VERSION = "v4_child_low";

  private static final String LATEST_JUNIOR_HIGH_SCREENING_VERSION = "v4_junior_high";
  private static final String LATEST_JUNIOR_MEDIUM_SCREENING_VERSION = "v4_junior_medium";
  private static final String LATEST_JUNIOR_LOW_SCREENING_VERSION = "v4_junior_low";

  public static final String LATEST_ADULT_HIGH_SCREENING_VERSION = "v4_adult_high";
  public static final String LATEST_ADULT_MEDIUM_SCREENING_VERSION = "v4_adult_medium";
  public static final String LATEST_ADULT_LOW_SCREENING_VERSION = "v4_adult_low";

  private static final String LATEST_SENIOR_HIGH_SCREENING_VERSION = "v4_senior_high";
  private static final String LATEST_SENIOR_MEDIUM_SCREENING_VERSION = "v4_senior_medium";
  private static final String LATEST_SENIOR_LOW_SCREENING_VERSION = "v4_senior_low";

  // Symptom severity settings
  private static final List<Symptom> MAJOR_SYMPTOMS =
      Arrays.asList(
          Symptom.FEVER,
          Symptom.SORE_THROAT,
          Symptom.COUGH,
          Symptom.SHORTNESS_BREATH,
          Symptom.CHEST_PAIN,
          Symptom.LOST_SMELL_OR_TASTE);

  private static final List<Symptom> MINOR_SYMPTOMS =
      Arrays.asList(
          Symptom.WEAKNESS,
          Symptom.HEADACHES,
          Symptom.SNIFFLES,
          Symptom.GASTRO,
          Symptom.MUSCLE_PAIN,
          Symptom.SKIN_RASH);

  private final ScreeningProperties screeningProperties;
  private final ScreeningParser screeningParser;

  public InitialQuestion findInitialQuestion(final UserProfile userProfile) {
    validateProfile(userProfile);
    final Tenant tenant = findCurrentTenant();
    final String version = findScreeningVersion(userProfile.getAge(), userProfile.getSymptoms());
    final JsonScreeningTree screeningTree = findScreeningTree(version);
    if (screeningTree.getNodes().size() == 0) {
      return toInitialQuestion(null, null, screeningTree, userProfile, tenant);
    } else {
      final JsonNode firstNode = screeningTree.getNodes().get(0);
      final JsonQuestion question = findQuestionById(screeningTree, firstNode.getQuestion());
      return toInitialQuestion(firstNode, question, screeningTree, userProfile, tenant);
    }
  }

  private static Symptom.SymptomSeverity getSymptomSeverity(final List<Symptom> symptoms) {
    boolean major = false;
    boolean minor = false;
    for (final Symptom symptom : symptoms) {
      if (MAJOR_SYMPTOMS.contains(symptom)) {
        major = true;
        break;
      } else if (MINOR_SYMPTOMS.contains(symptom)) {
        minor = true;
      }
    }
    if (major) {
      return Symptom.SymptomSeverity.HIGH;
    }
    if (minor) {
      return Symptom.SymptomSeverity.MEDIUM;
    }
    return Symptom.SymptomSeverity.LOW;
  }

  private static String getKidVersion() {
    return LATEST_KID_SCREENING_VERSION;
  }

  private static String getChildVersion(final Symptom.SymptomSeverity symptomSeverity) {
    switch (symptomSeverity) {
      case LOW:
        return LATEST_CHILD_LOW_SCREENING_VERSION;
      case MEDIUM:
        return LATEST_CHILD_MEDIUM_SCREENING_VERSION;
      case HIGH:
      default:
        return LATEST_CHILD_HIGH_SCREENING_VERSION;
    }
  }

  private static String getJuniorVersion(final Symptom.SymptomSeverity symptomSeverity) {
    switch (symptomSeverity) {
      case LOW:
        return LATEST_JUNIOR_LOW_SCREENING_VERSION;
      case MEDIUM:
        return LATEST_JUNIOR_MEDIUM_SCREENING_VERSION;
      case HIGH:
      default:
        return LATEST_JUNIOR_HIGH_SCREENING_VERSION;
    }
  }

  private static String getSeniorVersion(final Symptom.SymptomSeverity symptomSeverity) {
    switch (symptomSeverity) {
      case LOW:
        return LATEST_SENIOR_LOW_SCREENING_VERSION;
      case MEDIUM:
        return LATEST_SENIOR_MEDIUM_SCREENING_VERSION;
      case HIGH:
      default:
        return LATEST_SENIOR_HIGH_SCREENING_VERSION;
    }
  }

  private static String getAdultVersion(final Symptom.SymptomSeverity symptomSeverity) {
    switch (symptomSeverity) {
      case LOW:
        return LATEST_ADULT_LOW_SCREENING_VERSION;
      case MEDIUM:
        return LATEST_ADULT_MEDIUM_SCREENING_VERSION;
      case HIGH:
      default:
        return LATEST_ADULT_HIGH_SCREENING_VERSION;
    }
  }

  private static String findScreeningVersion(final int age, final List<Symptom> symptoms) {
    final Symptom.SymptomSeverity symptomSeverity = getSymptomSeverity(symptoms);

    if (age <= KID_TREE_MAX_AGE) {
      return getKidVersion();
    } else if (age <= CHILD_TREE_MAX_AGE) {
      return getChildVersion(symptomSeverity);
    } else if (age <= JUNIOR_TREE_MAX_AGE) {
      return getJuniorVersion(symptomSeverity);
    } else if (age >= SENIOR_TREE_MIN_AGE) {
      return getSeniorVersion(symptomSeverity);
    } else {
      return getAdultVersion(symptomSeverity);
    }
  }

  private Tenant findCurrentTenant() {
    return defaultIfNull(screeningProperties.getTenant(), Tenant.BAG);
  }

  private JsonScreeningTree findScreeningTree(final String version) {
    return screeningParser.readScreeningDecisionTree(version);
  }

  private static JsonQuestion findQuestionById(
      final JsonScreeningTree screeningTree, final String questionId) {
    return screeningTree.getQuestions().stream()
        .filter(question -> question.getId().equals(questionId))
        .findFirst()
        .orElse(null);
  }

  public Node answerScreeningQuestion(final PartialUserAnswer answer) {
    final Tenant tenant = findCurrentTenant();
    final JsonScreeningTree screeningTree = findScreeningTree(answer.getVersion());
    final NodeId lastNodeId = answer.getNodeIds().get(answer.getNodeIds().size() - 1);
    final JsonNode lastNode = findNodeById(screeningTree, lastNodeId);
    final JsonQuestion question = findQuestionById(screeningTree, lastNode.getQuestion());
    final SwissCanton swissCanton = answer.getProfile().getCanton();
    return toNode(lastNode, question, screeningTree, tenant, answer.getProfile(), swissCanton);
  }

  private static JsonNode findNodeById(final JsonScreeningTree screeningTree, final NodeId nodeId) {
    return screeningTree.getNodes().stream()
        .filter(node -> node.getId().equals(nodeId.get()))
        .findFirst()
        .orElse(null);
  }

  private InitialQuestion initialRecommendation(
      final JsonScreeningTree jsonScreeningTree,
      final UserProfile userProfile,
      final Tenant tenant) {
    final Recommendation recommendation =
        findRecommendationById(
            Collections.singletonList(jsonScreeningTree.getRecommendations().get(0)),
            jsonScreeningTree.getCantons(),
            jsonScreeningTree.getCantonalRecommendationFor(),
            jsonScreeningTree.getRecommendations().get(0).getId(),
            userProfile,
            tenant,
            userProfile.getCanton());

    final CompletedUserAnswer userAnswer =
        CompletedUserAnswer.builder()
            .exchanges(emptyList())
            .profile(userProfile)
            .recommendationId(recommendation.getId())
            .version(findScreeningVersion(userProfile.getAge(), userProfile.getSymptoms()))
            .tenant(tenant)
            .submittedAt(now())
            .build();
    userAnswerService.saveUserScreeningAnswers(userAnswer, true);
    return InitialQuestion.builder()
        .version(findScreeningVersion(userProfile.getAge(), userProfile.getSymptoms()))
        .initialNodeId(null)
        .node(null)
        .recommendation(Optional.of(recommendation))
        .build();
  }

  private InitialQuestion toInitialQuestion(
      final JsonNode firstNode,
      final JsonQuestion question,
      final JsonScreeningTree jsonScreeningTree,
      final UserProfile userProfile,
      final Tenant tenant) {
    if (jsonScreeningTree.getRecommendations().size() == 1) {
      return initialRecommendation(jsonScreeningTree, userProfile, tenant);
    }
    return InitialQuestion.builder()
        .version(findScreeningVersion(userProfile.getAge(), userProfile.getSymptoms()))
        .initialNodeId(NodeId.of(firstNode.getId()))
        .node(
            toNode(
                firstNode,
                question,
                jsonScreeningTree,
                tenant,
                userProfile,
                userProfile.getCanton()))
        .build();
  }

  private static Node toNode(
      final JsonNode jsonNode,
      final JsonQuestion jsonQuestion,
      final JsonScreeningTree jsonScreeningTree,
      final Tenant tenant,
      final UserProfile userProfile,
      final SwissCanton swissCanton) {
    if (jsonQuestion == null) {
      return null;
    }
    return Node.builder()
        .questionId(QuestionId.of(jsonQuestion.getId()))
        .question(jsonQuestion.getTitle().getOrDefault(userProfile.getLocale(), EMPTY))
        .description(jsonQuestion.getDescription().getOrDefault(userProfile.getLocale(), EMPTY))
        .answers(
            toAnswers(jsonNode.getAnswers(), jsonScreeningTree, userProfile, tenant, swissCanton))
        .build();
  }

  private static List<Answer> toAnswers(
      final List<JsonAnswerNode> jsonAnswerNodes,
      final JsonScreeningTree jsonScreeningTree,
      final UserProfile userProfile,
      final Tenant tenant,
      final SwissCanton swissCanton) {
    return emptyIfNull(jsonAnswerNodes).stream()
        .map(
            jsonAnswerNode ->
                toAnswer(jsonAnswerNode, jsonScreeningTree, userProfile, tenant, swissCanton))
        .collect(toList());
  }

  private static Answer toAnswer(
      final JsonAnswerNode jsonAnswerNode,
      final JsonScreeningTree jsonScreeningTree,
      final UserProfile userProfile,
      final Tenant tenant,
      final SwissCanton swissCanton) {
    return Answer.builder()
        .id(AnswerId.of(jsonAnswerNode.getText()))
        .type(jsonAnswerNode.getType())
        .text(toAnswerText(jsonAnswerNode, jsonScreeningTree, userProfile.getLocale()))
        .nextNodeId(NodeId.fromString(jsonAnswerNode.getNode()))
        .recommendation(
            toRecommendation(jsonAnswerNode, jsonScreeningTree, userProfile, tenant, swissCanton))
        .build();
  }

  private static String toAnswerText(
      final JsonAnswerNode jsonAnswerNode,
      final JsonScreeningTree jsonScreeningTree,
      final Locale userLocale) {
    return findAnswer(jsonScreeningTree.getAnswers(), jsonAnswerNode.getText())
        .map(jsonAnswer -> jsonAnswer.getText().getOrDefault(userLocale, EMPTY))
        .orElse(EMPTY);
  }

  private static Optional<JsonAnswer> findAnswer(
      final List<JsonAnswer> jsonAnswers, final String answerId) {
    return emptyIfNull(jsonAnswers).stream()
        .filter(jsonAnswer -> jsonAnswer.getId().equals(answerId))
        .findFirst();
  }

  private static Recommendation toRecommendation(
      final JsonAnswerNode jsonAnswerNode,
      final JsonScreeningTree jsonScreeningTree,
      final UserProfile userProfile,
      final Tenant tenant,
      final SwissCanton swissCanton) {
    return findRecommendationById(
        jsonScreeningTree.getRecommendations(),
        jsonScreeningTree.getCantons(),
        jsonScreeningTree.getCantonalRecommendationFor(),
        jsonAnswerNode.getRecommendation(),
        userProfile,
        tenant,
        swissCanton);
  }

  private static Recommendation findRecommendationById(
      final List<JsonRecommendation> jsonRecommendations,
      final List<JsonCantonalRecommendation> jsonCantonalRecommendations,
      final List<String> cantonalRecommendationFor,
      final String recommendationId,
      final UserProfile userProfile,
      final Tenant tenant,
      final SwissCanton swissCanton) {
    if (isBlank(recommendationId)) {
      return null;
    }

    return emptyIfNull(jsonRecommendations).stream()
        .filter(jsonRecommendation -> jsonRecommendation.getId().equals(recommendationId))
        .findFirst()
        .map(jsonRecommendation -> withAdditionalText(jsonRecommendation, tenant))
        .map(
            jsonRecommendation ->
                toRecommendation(
                    jsonRecommendation,
                    jsonCantonalRecommendations,
                    cantonalRecommendationFor,
                    userProfile,
                    swissCanton))
        .orElse(null);
  }

  private static JsonRecommendation withAdditionalText(
      final JsonRecommendation jsonRecommendation, final Tenant tenant) {
    if (!isMedgateTenant(tenant)) {
      return jsonRecommendation
          .toBuilder()
          .medgateTitle(emptyMap())
          .medgateDescription(emptyMap())
          .build();
    }
    return jsonRecommendation;
  }

  private static Recommendation toRecommendation(
      final JsonRecommendation jsonRecommendation,
      final List<JsonCantonalRecommendation> jsonCantonalRecommendations,
      final List<String> cantonalRecommendationFor,
      final UserProfile userProfile,
      final SwissCanton swissCanton) {

    final String description =
        getRecommendationDescription(
            jsonRecommendation,
            jsonCantonalRecommendations,
            cantonalRecommendationFor,
            swissCanton,
            userProfile);

    return Recommendation.builder()
        .id(RecommendationId.of(jsonRecommendation.getId()))
        .title(toTranslatedString(jsonRecommendation.getTitle(), userProfile.getLocale()))
        .description(description)
        .medgateTitle(
            toTranslatedString(jsonRecommendation.getMedgateTitle(), userProfile.getLocale()))
        .medgateDescription(
            toTranslatedString(jsonRecommendation.getMedgateDescription(), userProfile.getLocale()))
        .severity(jsonRecommendation.getSeverity())
        .build();
  }

  private static String getRecommendationDescription(
      final JsonRecommendation jsonRecommendation,
      final List<JsonCantonalRecommendation> jsonCantonalRecommendations,
      final List<String> cantonalRecommendationFor,
      final SwissCanton swissCanton,
      final UserProfile userProfile) {
    final String federalDescription =
        toTranslatedString(jsonRecommendation.getDescription(), userProfile.getLocale());
    final String cantonalDescription =
        toTranslatedString(jsonRecommendation.getCantonalDescription(), userProfile.getLocale());

    final String cantonalRecommendation =
        getCantonalRecommendation(
            jsonRecommendation,
            jsonCantonalRecommendations,
            cantonalRecommendationFor,
            swissCanton,
            userProfile);

    final StringBuilder description = new StringBuilder();
    if (!cantonalRecommendation.isEmpty() && !cantonalDescription.isEmpty()) {
      description.append(cantonalDescription);
    } else {
      description.append(federalDescription);
    }

    if (!cantonalRecommendation.isEmpty()) {
      description.append("<br><br>");
      description.append(cantonalRecommendation);
    }

    return description.toString();
  }

  private static String getCantonalRecommendation(
      final JsonRecommendation jsonRecommendation,
      final List<JsonCantonalRecommendation> jsonCantonalRecommendations,
      final List<String> cantonalRecommendationFor,
      final SwissCanton swissCanton,
      final UserProfile userProfile) {
    if (cantonalRecommendationFor == null) {
      return EMPTY;
    } else if (cantonalRecommendationFor.contains(jsonRecommendation.getId())) {
      final JsonCantonalRecommendation jsonCantonalRecommendation =
          jsonCantonalRecommendations.stream()
              .filter(canton -> canton.getId().equals(swissCanton.getCode()))
              .findAny()
              .orElse(null);
      if (jsonCantonalRecommendation != null) {
        return toTranslatedString(
            jsonCantonalRecommendation.getDescription(), userProfile.getLocale());
      }
    }
    return EMPTY;
  }

  private static String toTranslatedString(
      final Map<Locale, String> localizedText, final Locale userLocale) {
    if (MapUtils.isEmpty(localizedText)) {
      return EMPTY;
    }
    return localizedText.getOrDefault(userLocale, EMPTY);
  }

  private static boolean isMedgateTenant(final Tenant tenant) {
    return Tenant.MEDGATE.equals(tenant);
  }
}
