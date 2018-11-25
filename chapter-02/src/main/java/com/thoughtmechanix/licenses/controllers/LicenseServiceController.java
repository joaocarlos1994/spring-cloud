package com.thoughtmechanix.licenses.controllers;

import com.thoughtmechanix.licenses.model.License;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/***
 *A {@code {@link RestController}} tells Spring Boot this is a REST-based
 * services and will automatically serialize/deserialize service request/response to JSON.
 *
 */
@RestController
@RequestMapping(value="/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    @GetMapping(value="/{licenseId}")
    public License getLicenses(@PathVariable("organizationId") final String organizationId,
                               @PathVariable("licenseId") final String licenseId) {
        return new License()
                .withId(licenseId)
                .withOrganizationId(organizationId)
                .withProductName("Teleco")
                .withLicenseType("Seat")
                .withOrganizationId("Test Org");
    }
}
