package ch.bag.screening.storage.statistics;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.collections4.CollectionUtils.size;

import ch.bag.screening.domain.answer.CompletedUserAnswer;
import ch.bag.screening.domain.answer.Exchange;
import ch.bag.screening.domain.answer.StatisticsStore;
import ch.bag.screening.domain.node.AnswerId;
import ch.bag.screening.domain.node.QuestionId;
import ch.bag.screening.domain.profile.Symptom;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class StatisticsSqlStore implements StatisticsStore {

  private final QuestionSqlRepository questionSqlRepository;
  private final SymptomSqlRepository symptomSqlRepository;
  private final AnswerSqlRepository answerSqlRepository;
  private final RecommendationSqlRepository recommendationSqlRepository;
  private final ScreeningSqlRepository screeningSqlRepository;

  @Override
  @Transactional
  public ScreeningEntity saveScreeningStatistics(final CompletedUserAnswer userAnswer) {
    // Store the initial screening session for the user without exchanges
    final ScreeningEntity screening = screeningSqlRepository.save(buildScreeningEntity(userAnswer));
    // Map and store exchanges for the screening session
    final java.util.Map<QuestionId, QuestionEntity> questions = findQuestionsMap(userAnswer);
    final java.util.Map<AnswerId, AnswerEntity> answers = findAnswersMap(userAnswer);
    screening.setExchanges(buildExchangeEntities(userAnswer, screening, questions, answers));
    // Map and store symptoms for the screening session
    final java.util.Map<Symptom, SymptomEntity> symptoms = findSymptomsMap(userAnswer);
    screening.setUserSymptoms(buildUserSymptomEntities(userAnswer, screening, symptoms));
    return screeningSqlRepository.save(screening);
  }

  private ScreeningEntity buildScreeningEntity(final CompletedUserAnswer userAnswer) {
    return ScreeningEntity.builder()
        .version(userAnswer.getVersion())
        .age(userAnswer.getProfile().getAge())
        .gender(userAnswer.getProfile().getGender())
        .canton(userAnswer.getProfile().getCanton().getCode())
        .language(userAnswer.getProfile().getLocale().getLanguage())
        .tenant(userAnswer.getTenant())
        .submittedAt(userAnswer.getSubmittedAt())
        .recommendation(findRecommendation(userAnswer))
        .build();
  }

  private RecommendationEntity findRecommendation(final CompletedUserAnswer userAnswer) {
    return recommendationSqlRepository
        .findByVersionAndName(userAnswer.getVersion(), userAnswer.getRecommendationId().get())
        .orElse(null);
  }

  private java.util.Map<QuestionId, QuestionEntity> findQuestionsMap(
      final CompletedUserAnswer userAnswer) {
    return questionSqlRepository
        .findAllByVersionAndNameIn(
            userAnswer.getVersion(), toQuestionIds(userAnswer.getExchanges()))
        .stream()
        .collect(toMap(question -> QuestionId.fromString(question.getName()), identity()));
  }

  private java.util.Map<Symptom, SymptomEntity> findSymptomsMap(
      final CompletedUserAnswer userAnswer) {
    return symptomSqlRepository
        .findAllByVersionAndNameIn(
            userAnswer.getVersion(), toSymptomIds(userAnswer.getProfile().getSymptoms()))
        .stream()
        .collect(toMap(symptom -> Symptom.fromString(symptom.getName()), identity()));
  }

  private java.util.Map<AnswerId, AnswerEntity> findAnswersMap(
      final CompletedUserAnswer userAnswer) {
    return answerSqlRepository
        .findAllByVersionAndNameIn(userAnswer.getVersion(), toAnswerIds(userAnswer.getExchanges()))
        .stream()
        .collect(toMap(answer -> AnswerId.fromString(answer.getName()), identity()));
  }

  private static java.util.List<String> toQuestionIds(final java.util.List<Exchange> exchanges) {
    return emptyIfNull(exchanges).stream()
        .map(exchange -> exchange.getQuestionId().toString())
        .collect(toList());
  }

  private static java.util.List<String> toAnswerIds(final java.util.List<Exchange> exchanges) {
    return emptyIfNull(exchanges).stream()
        .map(exchange -> exchange.getAnswerId().toString())
        .collect(toList());
  }

  private static java.util.List<String> toSymptomIds(final java.util.List<Symptom> symptoms) {
    return emptyIfNull(symptoms).stream().map(Enum::toString).collect(toList());
  }

  private static java.util.List<ExchangeEntity> buildExchangeEntities(
      final CompletedUserAnswer userAnswer,
      final ScreeningEntity screening,
      final java.util.Map<QuestionId, QuestionEntity> questions,
      final java.util.Map<AnswerId, AnswerEntity> answers) {
    final java.util.List<ExchangeEntity> exchanges = new java.util.ArrayList<>();
    for (int i = 0; i < size(userAnswer.getExchanges()); i++) {
      final Exchange exchange = userAnswer.getExchanges().get(i);
      final QuestionEntity question = questions.get(exchange.getQuestionId());
      final AnswerEntity answer = answers.get(exchange.getAnswerId());
      exchanges.add(buildExchangeEntity(i, screening, question, answer));
    }
    return exchanges;
  }

  private static ExchangeEntity buildExchangeEntity(
      final int ordering,
      final ScreeningEntity screening,
      final QuestionEntity question,
      final AnswerEntity answer) {
    return ExchangeEntity.builder()
        .ordering(ordering)
        .question(question)
        .answer(answer)
        .screening(screening)
        .build();
  }

  private static UserSymptomEntity buildUserSymptomEntity(
      final ScreeningEntity screening, final SymptomEntity symptom) {
    return UserSymptomEntity.builder().screening(screening).symptom(symptom).build();
  }

  private static Set<UserSymptomEntity> buildUserSymptomEntities(
      final CompletedUserAnswer userAnswer,
      final ScreeningEntity screening,
      final java.util.Map<Symptom, SymptomEntity> symptoms) {
    final Set<UserSymptomEntity> userSymptoms = new java.util.HashSet<>();
    for (int i = 0; i < size(userAnswer.getProfile().getSymptoms()); i++) {
      final Symptom symptom = userAnswer.getProfile().getSymptoms().get(i);
      final SymptomEntity symptomEntity = symptoms.get(symptom);
      userSymptoms.add(buildUserSymptomEntity(screening, symptomEntity));
    }
    return userSymptoms;
  }
}
