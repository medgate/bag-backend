package ch.bag.screening.domain.answer;

import ch.bag.screening.domain.node.RecommendationId;
import ch.bag.screening.domain.profile.Tenant;
import ch.bag.screening.domain.profile.UserProfile;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CompletedUserAnswer {

  private final String version;
  private final UserProfile profile;
  private final List<Exchange> exchanges;
  private final RecommendationId recommendationId;
  private final Tenant tenant;
  private final Instant submittedAt;

  public CompletedUserAnswer withTenant(final Tenant tenant) {
    return toBuilder().tenant(tenant).build();
  }

  public CompletedUserAnswer withSubmittedAt(final Instant submittedAt) {
    return toBuilder().submittedAt(submittedAt).build();
  }
}
