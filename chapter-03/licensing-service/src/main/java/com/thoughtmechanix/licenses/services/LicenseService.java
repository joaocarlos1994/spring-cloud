package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;

    @Autowired
    public LicenseService(final LicenseRepository licenseRepository, final ServiceConfig config) {
        this.licenseRepository = licenseRepository;
        this.config = config;
    }

    @Transactional
    public void saveLicense(final License license){
        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    @Transactional
    public void updateLicense(final String licenseId, final License aLicense){
        final License license = licenseRepository.findOne(licenseId);
        license.withOrganizationId(license.getOrganizationId()).withLicenseType(aLicense.getLicenseType())
                .withProductName(aLicense.getProductName()).withComment(aLicense.getComment())
                .withLicenseAllocated(aLicense.getLicenseAllocated()).withLicenseMax(aLicense.getLicenseMax());
        licenseRepository.save(license);
    }

    @Transactional
    public void deleteLicense(final License license){
        licenseRepository.delete( license.getId());
    }

    public License getLicense(final String organizationId, final String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(config.getExampleProperty());
    }

    public List<License> getLicensesByOrg(final String organizationId){
        return licenseRepository.findByOrganizationId(organizationId);
    }
}
