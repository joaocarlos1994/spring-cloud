package com.thoughtmechanix.authentication.model;

import org.springframework.data.repository.CrudRepository;

public interface OrgUserRepository extends CrudRepository<UserOrganization,String> {

    UserOrganization findByUserName(String userName);

}
