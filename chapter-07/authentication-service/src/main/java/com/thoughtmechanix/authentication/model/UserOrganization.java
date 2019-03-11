package com.thoughtmechanix.authentication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_orgs")
public class UserOrganization implements Serializable {

    @Column(name = "organization_id", nullable = false)
    private String organizationId;

    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(final String organizationId) {
        this.organizationId = organizationId;
    }
}
