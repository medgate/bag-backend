package ch.bag.screening.domain.profile;

import java.util.stream.Stream;

public enum Symptom {
  FEVER,
  WEAKNESS,
  HEADACHES,
  SNIFFLES,
  SORE_THROAT,
  COUGH,
  SHORTNESS_BREATH,
  CHEST_PAIN,
  LOST_SMELL_OR_TASTE,
  GASTRO,
  MUSCLE_PAIN,
  SKIN_RASH,
  NO_SYMPTOMS;

  public static Symptom fromString(final String symptomString) {
    return Stream.of(values())
        .filter(symptom -> symptom.toString().equalsIgnoreCase(symptomString))
        .findFirst()
        .orElse(null);
  }

  public enum SymptomSeverity {
    HIGH,
    MEDIUM,
    LOW
  }
}
