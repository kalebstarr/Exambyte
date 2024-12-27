package com.soup.exambyte.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "roles")
public class RolesConfig {

  private List<Object> correctors;
  private List<Object> organizers;

  public List<Object> getCorrectors() {
    return correctors;
  }

  public void setCorrectors(List<Object> correctors) {
    this.correctors = correctors;
  }

  public List<Object> getOrganizers() {
    return organizers;
  }

  public void setOrganizers(List<Object> organizers) {
    this.organizers = organizers;
  }
}
