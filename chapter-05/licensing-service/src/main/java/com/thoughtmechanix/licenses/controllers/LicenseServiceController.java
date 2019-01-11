package com.thoughtmechanix.licenses.controllers;

import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.services.LicenseService;
import com.thoughtmechanix.licenses.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/***
 *A {@code {@link RestController}} tells Spring Boot this is a REST-based
 * services and will automatically serialize/deserialize service request/response to JSON.
 *
 */
@RestController
@RequestMapping(value="/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

    private final LicenseService licenseService;
    private final ServiceConfig serviceConfig;

    @Autowired
    public LicenseServiceController(final LicenseService licenseService, final ServiceConfig serviceConfig) {
        this.licenseService = licenseService;
        this.serviceConfig = serviceConfig;
    }

    @GetMapping(value="/")
    public List<License> getLicenses(@PathVariable("organizationId") final String organizationId) {
        logger.debug("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return licenseService.getLicensesByOrg(organizationId);
    }

    @GetMapping(value="/{licenseId}")
    public License getLicenses(@PathVariable("organizationId") final String organizationId,
                               @PathVariable("licenseId") final String licenseId) {
        return licenseService.getLicense(organizationId,licenseId);
    }

    @PutMapping(value="{licenseId}")
    public String updateLicenses(@PathVariable("licenseId")final String licenseId, @RequestBody License license) {
        return String.format("This is the put");
    }

    @GetMapping(value="/{licenseId}/{clientType}")
    public License getLicensesWithClient(@PathVariable("organizationId") final String organizationId,
                                         @PathVariable("licenseId") final String licenseId,
                                         @PathVariable("clientType") final String clientType) {
        return licenseService.getLicense(organizationId,licenseId, clientType);
    }

}
