package ch.bag.screening.domain.answer;

import ch.bag.screening.storage.statistics.ScreeningEntity;

public interface StatisticsStore {
  ScreeningEntity saveScreeningStatistics(CompletedUserAnswer userAnswer);
}
