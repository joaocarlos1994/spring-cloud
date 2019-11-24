package com.thoughtmechanix.organization.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.thoughtmechanix.organization.events.source.SimpleSourceBean;
import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationService {

    private final OrganizationRepository orgRepository;
    private final SimpleSourceBean simpleSourceBean;
    private final Tracer tracer;

    @Autowired
    public OrganizationService(OrganizationRepository orgRepository, SimpleSourceBean simpleSourceBean,
                               Tracer tracer) {
        this.orgRepository = orgRepository;
        this.simpleSourceBean = simpleSourceBean;
        this.tracer = tracer;
    }

    @HystrixCommand
    public Organization getOrg(String organizationId) {
        final Span newSpan = tracer.createSpan("getOrgDBCall");
        try {
            return orgRepository.findById(organizationId);
        } finally {
            newSpan.tag("peer.service", "postgres");
            newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
            tracer.close(newSpan);
        }
    }

    @Transactional
    public void saveOrg(final Organization org){
        org.setId( UUID.randomUUID().toString());
        orgRepository.save(org);
        simpleSourceBean.publishOrgChange("SAVE", org.getId());
    }

    @Transactional
    public void updateOrg(final Organization org){
        orgRepository.save(org);
        simpleSourceBean.publishOrgChange("UPDATE", org.getId());
    }

    @Transactional
    public void deleteOrg(final Organization org){
        orgRepository.delete(org.getId());
        simpleSourceBean.publishOrgChange("DELETE", org.getId());
    }
}
