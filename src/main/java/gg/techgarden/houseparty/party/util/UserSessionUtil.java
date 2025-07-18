package gg.techgarden.houseparty.party.util;

import gg.techgarden.houseparty.party.persistence.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;
import java.util.UUID;

public class UserSessionUtil {

    public static Optional<UUID> getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            UUID userId = UUID.fromString(jwt.getSubject());
            return Optional.of(userId);
        }

        return Optional.empty();
    }

    public static Optional<UserInfo> getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            UserInfo userInfo = new UserInfo();
            userInfo.setId(UUID.fromString(jwt.getSubject()));
            userInfo.setUsername(jwt.getClaimAsString("preferred_username"));
            userInfo.setFirstName(jwt.getClaimAsString("given_name"));
            userInfo.setLastName(jwt.getClaimAsString("family_name"));
            userInfo.setName(jwt.getClaimAsString("name"));
            userInfo.setEmail(jwt.getClaimAsString("email"));
            userInfo.setRoles(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
            return Optional.of(userInfo);
        }

        return Optional.empty(); // or throw an exception if you prefer
    }

    public static Optional<String> getClaim(String claimName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            Object claimValue = jwt.getClaims().get(claimName);
            return Optional.ofNullable(claimValue != null ? claimValue.toString() : null);
        }

        return Optional.empty();
    }
}

