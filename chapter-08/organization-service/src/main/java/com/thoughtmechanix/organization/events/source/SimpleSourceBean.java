package com.thoughtmechanix.organization.events.source;

import com.thoughtmechanix.organization.events.models.OrganizationChangeModel;
import com.thoughtmechanix.organization.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * All communication to a specific message topic occurs through a
 * Spring Cloud Stream construct called a channel. A channel is
 * represented by a Java interface class. In this construct
 * called a channel.
 *
 * The Source interface is a convenient interface to use when
 * your service only needs to publish to a single channel.
 *
 * @author Joao Batista
 * @version 1.0 18/03/2019
 */
@Component
public class SimpleSourceBean {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    private Source source;

    @Autowired
    public SimpleSourceBean(final Source source) {
        this.source = source;
    }

    public void publishOrgChange(final String action, final String orgId) {

        logger.debug("Sending Kafka message {}", action, orgId);

        final OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                orgId,
                UserContext.getCorrelationId());
        source
         .output()
         .send(MessageBuilder
                 .withPayload(change).build());


    }

}
