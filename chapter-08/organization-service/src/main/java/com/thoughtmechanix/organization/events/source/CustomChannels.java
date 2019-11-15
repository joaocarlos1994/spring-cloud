package com.thoughtmechanix.organization.events.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomChannels {

    @Output("outboundOrg")
    MessageChannel outboundOrg();

}
