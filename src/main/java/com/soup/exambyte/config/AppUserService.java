package com.soup.exambyte.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;


@Component
public class AppUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();

  private final RolesConfig rolesConfig;

  public AppUserService(RolesConfig rolesConfig) {
    this.rolesConfig = rolesConfig;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User originalUser = defaultService.loadUser(userRequest);
    Set<GrantedAuthority> authorities = new HashSet<>(originalUser.getAuthorities());
    Integer id = originalUser.getAttribute("id");

    if (id != null) {
      if (rolesConfig.getOrganizers().contains(id)) {
        authorities.add(new SimpleGrantedAuthority("ROLE_ORGANIZER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_CORRECTOR"));
      }

      if (rolesConfig.getCorrectors().contains(id)) {
        authorities.add(new SimpleGrantedAuthority("ROLE_CORRECTOR"));
      }
    }

    return new DefaultOAuth2User(authorities, originalUser.getAttributes(), "id");
  }
}
