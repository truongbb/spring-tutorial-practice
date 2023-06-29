package grabcall.api.app.config;

import grabcall.api.security.UserAuthentication;
import grabcall.api.security.UserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaAuditingConfig implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        } else {
            try {
                UserAuthentication userAuthentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
                UserDetails userDetails = (UserDetails) userAuthentication.getDetails();

                return Optional.of(userDetails.getUsername());
            } catch (Exception e) {
                return Optional.empty();
            }
        }


    }

}
