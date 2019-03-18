package com.thoughtmechanix.organization.events.models;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 18/03/2019
 */
public class OrganizationChangeModel {

    private String type;
    private String action;
    private String organizationId;
    private String correlationId;

    public  OrganizationChangeModel(final String type, final String action, final String organizationId,
                                    final String correlationId) {
        super();
        this.type   = type;
        this.action = action;
        this.organizationId = organizationId;
        this.correlationId = correlationId;
    }

}
