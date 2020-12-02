package com.crisis.management.utilities;

import com.crisis.management.models.UserPrincipal;
import org.springframework.security.core.Authentication;

public class AuthMiner {

    public static String getUsername(Authentication authentication) {
        return ((UserPrincipal) authentication.getPrincipal()).getUsername();
    }

    public static long getUserId(Authentication authentication) {
        return ((UserPrincipal) authentication).getId();
    }

}
