package ch.bag.screening.domain.screening;

import ch.bag.screening.domain.node.Node;
import ch.bag.screening.domain.node.NodeId;
import ch.bag.screening.domain.node.Recommendation;
import java.util.Optional;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class InitialQuestion {
  private final String version;
  private final NodeId initialNodeId;
  private final Node node;
  private final Optional<Recommendation> recommendation;
}
