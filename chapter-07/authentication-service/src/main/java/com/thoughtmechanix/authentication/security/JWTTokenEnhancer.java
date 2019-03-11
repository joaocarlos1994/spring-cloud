package com.thoughtmechanix.authentication.security;

import com.thoughtmechanix.authentication.model.OrgUserRepository;
import com.thoughtmechanix.authentication.model.UserOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenEnhancer implements TokenEnhancer {

    @Autowired
    private OrgUserRepository orgUserRepo;

    @Override
    public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>(1);
        final String orgId =  getOrgId(authentication.getName());
        additionalInfo.put("organizationId", orgId);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    private String getOrgId(final String userName){
        final UserOrganization orgUser = orgUserRepo.findByUserName(userName);
        return orgUser.getOrganizationId();
    }
}
