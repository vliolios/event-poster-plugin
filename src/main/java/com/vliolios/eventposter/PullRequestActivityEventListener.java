package com.vliolios.eventposter;

import com.atlassian.bitbucket.event.pull.PullRequestActivityEvent;
import com.atlassian.event.api.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named("pullRequestActivityEventListener")
public class PullRequestActivityEventListener {

    private static final Logger log = LoggerFactory.getLogger(PullRequestActivityEventListener.class);

    @EventListener
    public void postEvent(PullRequestActivityEvent event) {
        // do something
    }

}
