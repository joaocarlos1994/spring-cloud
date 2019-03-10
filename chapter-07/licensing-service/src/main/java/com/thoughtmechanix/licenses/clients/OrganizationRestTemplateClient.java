package com.thoughtmechanix.licenses.clients;

import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrganizationRestTemplateClient {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

    @Autowired
    private OAuth2RestTemplate oauth2RestTemplate;

    public Organization getOrganization(final String organizationId) {
        logger.debug("In Licensing Service.getOrganization: {}", UserContext.getCorrelationId());

        final ResponseEntity<Organization> restExchange =
                oauth2RestTemplate.exchange(
                        "http://zuulservice/api/organization/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);
        return restExchange.getBody();
    }

}
