package com.thoughtmechanix.organization.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.thoughtmechanix.organization.events.source.SimpleSourceBean;
import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository orgRepository;

    @Autowired
    private SimpleSourceBean simpleSourceBean;

    @HystrixCommand
    public Organization getOrg(String organizationId) {
        return orgRepository.findById(organizationId);
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
