package com.thoughtmechanix.licenses.repository;

import com.thoughtmechanix.licenses.model.License;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LicenseRepository extends CrudRepository<License,String> {

    List<License> findByOrganizationId(final String organizationId);

    License findByOrganizationIdAndLicenseId(final String organizationId, final String licenseId);
}
