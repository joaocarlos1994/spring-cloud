package com.thoughtmechanix.licenses.controllers;

import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.services.LicenseService;
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

    private final LicenseService licenseService;
    private final ServiceConfig serviceConfig;

    @Autowired
    public LicenseServiceController(final LicenseService licenseService, final ServiceConfig serviceConfig) {
        this.licenseService = licenseService;
        this.serviceConfig = serviceConfig;
    }

    @GetMapping(value="/")
    public List<License> getLicenses(@PathVariable("organizationId") final String organizationId) {
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

}
