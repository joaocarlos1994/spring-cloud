package com.thoughtmechanix.licenses.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.licenses.clients.OrganizationRestTemplateClient;
import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.repository.LicenseRepository;
import com.thoughtmechanix.licenses.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class LicenseService {

    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;
    private final OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    public LicenseService(final LicenseRepository licenseRepository, final ServiceConfig config,
                          final OrganizationRestTemplateClient organizationRestClient) {
        this.licenseRepository = licenseRepository;
        this.config = config;
        this.organizationRestClient = organizationRestClient;
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
                     @HystrixProperty(name="maxQueueSize", value="10")},
            commandProperties={
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
                    @HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")}
    )
    public List<License> getLicensesByOrg(final String organizationId){
        logger.debug("getLicensesByOrg Correlation id: {}",
                UserContextHolder
                        .getContext()
                        .getCorrelationId());
        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public License getLicense(String organizationId,String licenseId, String clientType) {
        final License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        final Organization org = getOrganization(organizationId);

        return license
                .withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    @HystrixCommand
    private Organization getOrganization(String organizationId) {
        return organizationRestClient.getOrganization(organizationId);
    }

}
