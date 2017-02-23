package com.vliolios.eventposter;

public class RepositorySettings {

	interface Keys {
		String WEBHOOK = "webhook";
		String PULL_REQUEST_COMMIT_COMMENT_ADDED_ON = "pullRequestCommitCommentAddedOn";
		String PULL_REQUEST_COMMENT_REPLIED_ON = "pullRequestCommentRepliedOn";
		String PULL_REQUEST_COMMENT_EDITED_ON = "pullRequestCommentEditedOn";
		String PULL_REQUEST_COMMENT_DELETED_ON = "pullRequestCommentDeletedOn";
		String PULL_REQUEST_COMMENT_ADDED_ON = "pullRequestCommentAddedOn";
		String PULL_REQUEST_DECLINED_ON = "pullRequestDeclinedOn";
		String PULL_REQUEST_OPENED_ON = "pullRequestOpenedOn";
		String PULL_REQUEST_MERGED_ON = "pullRequestMergedOn";
		String PULL_REQUEST_PARTICIPANT_STATUS_UPDATED_ON = "pullRequestParticipantStatusUpdatedOn";
		String PULL_REQUEST_RESCOPED_ON = "pullRequestRescopedOn";
		String PULL_REQUEST_REOPENED_ON = "pullRequestReopenedOn";
		String PULL_REQUEST_UPDATED_ON = "pullRequestUpdatedOn";
		String PULL_REQUEST_REVIEWERS_UPDATED_ON = "pullRequestReviewersUpdatedOn";
		String TASK_CREATED_ON = "taskCreatedOn";
		String TASK_UPDATED_ON = "taskUpdatedOn";
		String TASK_DELETED_ON = "taskDeletedOn";
	}

	private String webhook;
	private boolean pullRequestReviewersUpdatedOn;
	private boolean pullRequestUpdatedOn;
	private boolean pullRequestReopenedOn;
	private boolean pullRequestRescopedOn;
	private boolean pullRequestParticipantStatusUpdatedOn;
	private boolean pullRequestMergedOn;
	private boolean pullRequestOpenedOn;
	private boolean pullRequestDeclinedOn;
	private boolean pullRequestCommentAddedOn;
	private boolean pullRequestCommentDeletedOn;
	private boolean pullRequestCommentEditedOn;
	private boolean pullRequestCommentRepliedOn;
	private boolean pullRequestCommitCommentAddedOn;
	private boolean taskCreatedOn;
	private boolean taskDeletedOn;
	private boolean taskUpdatedOn;

	private RepositorySettings(Builder builder) {
		this.webhook = builder.webhook;
		this.pullRequestReviewersUpdatedOn = builder.pullRequestReviewersUpdatedOn;
		this.pullRequestUpdatedOn = builder.pullRequestUpdatedOn;
		this.pullRequestReopenedOn = builder.pullRequestReopenedOn;
		this.pullRequestRescopedOn = builder.pullRequestRescopedOn;
		this.pullRequestParticipantStatusUpdatedOn = builder.pullRequestParticipantStatusUpdatedOn;
		this.pullRequestMergedOn = builder.pullRequestMergedOn;
		this.pullRequestOpenedOn = builder.pullRequestOpenedOn;
		this.pullRequestDeclinedOn = builder.pullRequestDeclinedOn;
		this.pullRequestCommentAddedOn = builder.pullRequestCommentAddedOn;
		this.pullRequestCommentDeletedOn = builder.pullRequestCommentDeletedOn;
		this.pullRequestCommentEditedOn = builder.pullRequestCommentEditedOn;
		this.pullRequestCommentRepliedOn = builder.pullRequestCommentRepliedOn;
		this.pullRequestCommitCommentAddedOn = builder.pullRequestCommitCommentAddedOn;
		this.taskCreatedOn = builder.taskCreatedOn;
		this.taskDeletedOn = builder.taskDeletedOn;
		this.taskUpdatedOn = builder.taskUpdatedOn;
	}

	public String getWebhook() {
		return webhook;
	}

	public boolean isPullRequestReviewersUpdatedOn() {
		return pullRequestReviewersUpdatedOn;
	}

	public boolean isPullRequestUpdatedOn() {
		return pullRequestUpdatedOn;
	}

	public boolean isPullRequestReopenedOn() {
		return pullRequestReopenedOn;
	}

	public boolean isPullRequestRescopedOn() {
		return pullRequestRescopedOn;
	}

	public boolean isPullRequestParticipantStatusUpdatedOn() {
		return pullRequestParticipantStatusUpdatedOn;
	}

	public boolean isPullRequestMergedOn() {
		return pullRequestMergedOn;
	}

	public boolean isPullRequestOpenedOn() {
		return pullRequestOpenedOn;
	}

	public boolean isPullRequestDeclinedOn() {
		return pullRequestDeclinedOn;
	}

	public boolean isPullRequestCommentAddedOn() {
		return pullRequestCommentAddedOn;
	}

	public boolean isPullRequestCommentDeletedOn() {
		return pullRequestCommentDeletedOn;
	}

	public boolean isPullRequestCommentEditedOn() {
		return pullRequestCommentEditedOn;
	}

	public boolean isPullRequestCommentRepliedOn() {
		return pullRequestCommentRepliedOn;
	}

	public boolean isPullRequestCommitCommentAddedOn() {
		return pullRequestCommitCommentAddedOn;
	}

	public boolean isTaskCreatedOn() {
		return taskCreatedOn;
	}

	public boolean isTaskDeletedOn() {
		return taskDeletedOn;
	}

	public boolean isTaskUpdatedOn() {
		return taskUpdatedOn;
	}

	public static class Builder {
		private String webhook = "";
		private boolean pullRequestReviewersUpdatedOn = true;
		private boolean pullRequestUpdatedOn = true;
		private boolean pullRequestReopenedOn = true;
		private boolean pullRequestRescopedOn = true;
		private boolean pullRequestParticipantStatusUpdatedOn = true;
		private boolean pullRequestMergedOn = true;
		private boolean pullRequestOpenedOn = true;
		private boolean pullRequestDeclinedOn = true;
		private boolean pullRequestCommentAddedOn = true;
		private boolean pullRequestCommentDeletedOn = true;
		private boolean pullRequestCommentEditedOn = true;
		private boolean pullRequestCommentRepliedOn = true;
		private boolean pullRequestCommitCommentAddedOn = true;
		private boolean taskCreatedOn = true;
		private boolean taskDeletedOn = true;
		private boolean taskUpdatedOn = true;

		public Builder webhook(String webhook) {
			this.webhook = webhook;
			return this;
		}

		public Builder pullRequestReviewersUpdatedOn(boolean pullRequestReviewersUpdatedOn) {
			this.pullRequestReviewersUpdatedOn = pullRequestReviewersUpdatedOn;
			return this;
		}

		public Builder pullRequestUpdatedOn(boolean pullRequestUpdatedOn) {
			this.pullRequestUpdatedOn = pullRequestUpdatedOn;
			return this;
		}

		public Builder pullRequestReopenedOn(boolean pullRequestReopenedOn) {
			this.pullRequestReopenedOn = pullRequestReopenedOn;
			return this;
		}

		public Builder pullRequestRescopedOn(boolean pullRequestRescopedOn) {
			this.pullRequestRescopedOn = pullRequestRescopedOn;
			return this;
		}

		public Builder pullRequestParticipantStatusUpdatedOn(boolean pullRequestParticipantStatusUpdatedOn) {
			this.pullRequestParticipantStatusUpdatedOn = pullRequestParticipantStatusUpdatedOn;
			return this;
		}

		public Builder pullRequestMergedOn(boolean pullRequestMergedOn) {
			this.pullRequestMergedOn = pullRequestMergedOn;
			return this;
		}

		public Builder pullRequestOpenedOn(boolean pullRequestOpenedOn) {
			this.pullRequestOpenedOn = pullRequestOpenedOn;
			return this;
		}

		public Builder pullRequestDeclinedOn(boolean pullRequestDeclinedOn) {
			this.pullRequestDeclinedOn = pullRequestDeclinedOn;
			return this;
		}

		public Builder pullRequestCommentAddedOn(boolean pullRequestCommentAddedOn) {
			this.pullRequestCommentAddedOn = pullRequestCommentAddedOn;
			return this;
		}

		public Builder pullRequestCommentDeletedOn(boolean pullRequestCommentDeletedOn) {
			this.pullRequestCommentDeletedOn = pullRequestCommentDeletedOn;
			return this;
		}

		public Builder pullRequestCommentEditedOn(boolean pullRequestCommentEditedOn) {
			this.pullRequestCommentEditedOn = pullRequestCommentEditedOn;
			return this;
		}

		public Builder pullRequestCommentRepliedOn(boolean pullRequestCommentRepliedOn) {
			this.pullRequestCommentRepliedOn = pullRequestCommentRepliedOn;
			return this;
		}

		public Builder pullRequestCommitCommentAddedOn(boolean pullRequestCommitCommentAddedOn) {
			this.pullRequestCommitCommentAddedOn = pullRequestCommitCommentAddedOn;
			return this;
		}

		public Builder taskCreatedOn(boolean taskCreatedOn) {
			this.taskCreatedOn = taskCreatedOn;
			return this;
		}

		public Builder taskDeletedOn(boolean taskDeletedOn) {
			this.taskDeletedOn = taskDeletedOn;
			return this;
		}

		public Builder taskUpdatedOn(boolean taskUpdatedOn) {
			this.taskUpdatedOn = taskUpdatedOn;
			return this;
		}

		public RepositorySettings build() {
			return new RepositorySettings(this);
		}
	}
}
