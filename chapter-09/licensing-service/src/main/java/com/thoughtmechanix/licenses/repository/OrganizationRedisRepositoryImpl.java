package com.thoughtmechanix.licenses.repository;

import com.thoughtmechanix.licenses.model.Organization;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 19/03/2019
 */
@Repository
public class OrganizationRedisRepositoryImpl implements OrganizationRedisRepository {

    private static final String HASH_NAME = "organization";

    private RedisTemplate<String, Organization> redisTemplate;
    private HashOperations hashOperations;

    public OrganizationRedisRepositoryImpl() { super(); }

    @Autowired
    private OrganizationRedisRepositoryImpl(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveOrganization(final Organization org) {
        hashOperations.put(HASH_NAME, org.getId(), org);
    }

    @Override
    public void updateOrganization(final Organization org) {
        hashOperations.put(HASH_NAME, org.getId(), org);
    }

    @Override
    public void deleteOrganization(final String organizationId) {
        hashOperations.delete(HASH_NAME, organizationId);
    }

    @Override
    public Organization findOrganization(final String organizationId) {
        return (Organization) hashOperations.get(HASH_NAME, organizationId);
    }
}
