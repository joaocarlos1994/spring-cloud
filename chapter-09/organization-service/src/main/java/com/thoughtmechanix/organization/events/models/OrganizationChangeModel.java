package com.thoughtmechanix.organization.events.models;

import java.io.Serializable;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 18/03/2019
 */
public class OrganizationChangeModel implements Serializable {

    private String type;
    private String action;
    private String organizationId;
    private String correlationId;

    public OrganizationChangeModel() {}

    public  OrganizationChangeModel(final String type, final String action, final String organizationId,
                                    final String correlationId) {
        super();
        this.type   = type;
        this.action = action;
        this.organizationId = organizationId;
        this.correlationId = correlationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }


    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public String toString() {
        return "OrganizationChangeModel [type=" + type +
                ", action=" + action +
                ", orgId="  + organizationId +
                ", correlationId=" + correlationId + "]";
    }

}