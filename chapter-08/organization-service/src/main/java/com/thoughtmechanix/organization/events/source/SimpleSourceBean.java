package com.thoughtmechanix.organization.events.source;

import com.thoughtmechanix.organization.events.models.OrganizationChangeModel;
import com.thoughtmechanix.organization.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
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
@EnableBinding(CustomChannels.class)
public class SimpleSourceBean {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    private CustomChannels customChannels;

    @Autowired
    public SimpleSourceBean(CustomChannels customChannels) {
        this.customChannels = customChannels;
    }

    public void publishOrgChange(final String action, final String orgId) {

        logger.debug("Sending Kafka message {}", action, orgId);

        final OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                orgId,
                UserContext.getCorrelationId());

        customChannels
                .outboundOrg()
                    .send(MessageBuilder
                            .withPayload(change).build());
    }
}
