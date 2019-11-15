package com.thoughtmechanix.licenses.repository;

import com.thoughtmechanix.licenses.model.Organization;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 19/03/2019
 */
public interface OrganizationRedisRepository {

    void saveOrganization(final Organization org);
    void updateOrganization(final Organization org);
    void deleteOrganization(final String organizationId);
    Organization findOrganization(final String organizationId);

}
