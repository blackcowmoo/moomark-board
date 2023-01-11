package com.moomark.post.configuration.profile;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public abstract class BaseProfileCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    String[] activeProfile = context.getEnvironment().getActiveProfiles();
    List<Profile> targetProfiles = this.getTargetProfile();

    return Arrays.stream(activeProfile)
        .anyMatch(e -> this.isActiveProfileMatchedWithTargetProfiles(e, targetProfiles));
  }

  abstract List<Profile> getTargetProfile();

  private boolean isActiveProfileMatchedWithTargetProfiles(String activeProfile, List<Profile> targetProfile) {
    return targetProfile.stream().anyMatch(e -> e.isEqualTo(activeProfile));
  }
}