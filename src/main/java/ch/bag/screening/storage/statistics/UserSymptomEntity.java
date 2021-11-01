package ch.bag.screening.storage.statistics;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_symptoms")
public class UserSymptomEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "symptom_id")
  private SymptomEntity symptom;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "screening_id")
  private ScreeningEntity screening;
}
