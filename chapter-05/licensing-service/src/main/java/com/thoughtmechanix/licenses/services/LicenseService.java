package com.thoughtmechanix.licenses.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

import java.util.Arrays;
import java.util.List;
import java.util.Random;
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


    private void randomlyRunLong(){
        Random rand = new Random();

        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        if (randomNum==3) sleep();
    }

    private void sleep(){
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<License> buildFallbackLicenseList(String organizationId){
        final License license = new License()
                .withId("0000000-00-00000")
                .withOrganizationId( organizationId )
                .withProductName("Sorry no licensing information currently available");
        return Arrays.asList(license);
    }

    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList",
        threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties =
                    {@HystrixProperty(name = "coreSize", value="30"),
                     @HystrixProperty(name="maxQueueSize", value="10")}
    )
    public List<License> getLicensesByOrg(final String organizationId){
        randomlyRunLong();
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
