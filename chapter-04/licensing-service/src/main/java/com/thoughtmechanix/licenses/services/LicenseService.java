package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.licenses.clients.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.clients.OrganizationFeignClient;
import com.thoughtmechanix.licenses.clients.OrganizationRestTemplateClient;
import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.model.Organization;
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
    private final OrganizationDiscoveryClient organizationDiscoveryClient;
    private final OrganizationRestTemplateClient organizationRestClient;
    private final OrganizationFeignClient organizationFeignClient;

    @Autowired
    public LicenseService(final LicenseRepository licenseRepository, final ServiceConfig config,
                          final OrganizationDiscoveryClient organizationDiscoveryClient,
                          final OrganizationRestTemplateClient organizationRestClient,
                          final OrganizationFeignClient organizationFeignClient) {
        this.licenseRepository = licenseRepository;
        this.config = config;
        this.organizationDiscoveryClient = organizationDiscoveryClient;
        this.organizationRestClient = organizationRestClient;
        this.organizationFeignClient = organizationFeignClient;
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
        licenseRepository.delete( license.getLicenseId());
    }

    public License getLicense(final String organizationId, final String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(config.getExampleProperty());
    }

    public List<License> getLicensesByOrg(final String organizationId){
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public License getLicense(String organizationId,String licenseId, String clientType) {
        final License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        final Organization org = retrieveOrgInfo(organizationId, clientType);

        return license
                .withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    private Organization retrieveOrgInfo(String organizationId, String clientType){
        final Organization organization;
        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }
        return organization;
    }
}
