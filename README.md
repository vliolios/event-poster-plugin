# Event poster plugin - Bitbucket Server [![Build Status](https://travis-ci.org/vliolios/event-poster-plugin.svg?branch=master)](https://travis-ci.org/vliolios/event-poster-plugin)

A plugin that listens to Bitbucket Server events and posts them to a URL specified in each repository's settings.

## Supported events
### Pull requests
* PullRequestReviewersUpdatedEvent - An event raised when the reviewers of a pull request are updated. 
* PullRequestUpdatedEvent - Event that is raised when the pull request title, description, or target branch are updated. 
* PullRequestReopenedEvent - Event that is raised when a pull request is reopened. 
* PullRequestRescopedEvent - Event that is raised when the ref for the source-branch and/or the target-branch of a pull request is updated. 
* PullRequestParticipantStatusUpdatedEvent - Base class for events raised when a participant updates its status on a pull request. 
* PullRequestMergedEvent - Event raised when a pull request is merged via the web UI or REST, or when a remote merge is detected. 
* PullRequestOpenedEvent - Event that is raised when a pull request is opened. 
* PullRequestDeclinedEvent - Event that is raised when a pull request is declined. 
* PullRequestCommentAddedEvent - Event that is raised when a comment is added on a pull request.
* PullRequestCommentDeletedEvent - Event that is raised when a comment is deleted on a pull request.
* PullRequestCommentEditedEvent - Event that is raised when a comment is edited on a pull request. 
* PullRequestCommentRepliedEvent - Event that is raised when a comment is replied to on a pull request.
* PullRequestCommitCommentAddedEvent - Event that is raised when a comment is added on a commit in a pull request.

### Tasks
* TaskCreatedEvent - Event that is raised when a task is created.
* TaskDeletedEvent - Event that is raised when a task is deleted.
* TaskUpdatedEvent - Event that is raised when a task is updated.

