package com.thoughtmechanix.licenses.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "licenses")
public class License {

    @Id
    @Column(name = "license_id", nullable = false)
    private String id;

    @Column(name = "organization_id", nullable = false)
    private String organizationId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "license_type", nullable = false)
    private String licenseType;

    @Column(name = "license_max", nullable = false)
    private Integer licenseMax;

    @Column(name = "license_allocated", nullable = false)
    private Integer licenseAllocated;

    @Column(name="comment")
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(final String licenseType) {
        this.licenseType = licenseType;
    }

    public Integer getLicenseMax() {
        return licenseMax;
    }

    public void setLicenseMax(Integer licenseMax) {
        this.licenseMax = licenseMax;
    }

    public Integer getLicenseAllocated() {
        return licenseAllocated;
    }

    public void setLicenseAllocated(Integer licenseAllocated) {
        this.licenseAllocated = licenseAllocated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public License withId(final String id){
        this.setId(id);
        return this;
    }

    public License withOrganizationId(final String organizationId){
        this.setOrganizationId(organizationId);
        return this;
    }

    public License withProductName(final String productName){
        this.setProductName(productName);
        return this;
    }

    public License withLicenseType(final String licenseType){
        this.setLicenseType(licenseType);
        return this;
    }

    public License withLicenseMax(Integer licenseMax){
        this.setLicenseMax(licenseMax);
        return this;
    }

    public License withLicenseAllocated(Integer licenseAllocated){
        this.setLicenseAllocated(licenseAllocated);
        return this;
    }

    public License withComment(String comment){
        this.setComment(comment);
        return this;
    }
}
