package com.moomark.post.configuration.profile;

import java.util.List;

public class LocalProfileCondition extends BaseProfileCondition {

  @Override
  List<Profile> getTargetProfile() {
    return List.of(Profile.LOCAL);
  }
}
