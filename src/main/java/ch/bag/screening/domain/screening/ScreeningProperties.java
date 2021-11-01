package ch.bag.screening.domain.screening;

import ch.bag.screening.domain.profile.Tenant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "app.screening")
public class ScreeningProperties {
  private Tenant tenant;
}
