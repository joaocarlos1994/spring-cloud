package com.thoughtmechanix.licenses.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomChannels {

    /**
     * Each channel exposed through the
     * @Input annotation must return a SubscribableChannel
     * class
     *
     * */
    @Input("inboundOrgChanges")
    SubscribableChannel orgs();

}
