package com.slin.git.api.entity

import com.google.gson.annotations.SerializedName
import java.util.*


/**
 * author: slin
 * date: 2020/10/22
 * description:
 *
 */
data class Issue(
    var id: String? = null,
    var number: Int = 0,
    var title: String? = null,
    var state: IssueState? = null,
    var locked: Boolean = false,

    @SerializedName("comments")
    var commentNum: Int = 0,

    @SerializedName("created_at")
    var createdAt: Date? = null,

    @SerializedName("updated_at")
    var updatedAt: Date? = null,

    @SerializedName("closed_at")
    var closedAt: Date? = null,
    var body: String? = null,

    @SerializedName("body_html")
    var bodyHtml: String? = null,
    var user: UserInfo? = null,

    @SerializedName("author_association")
    var authorAssociation: IssueAuthorAssociation? = null,

    @SerializedName("repository_url")
    var repoUrl: String? = null,

    @SerializedName("html_url")
    var htmlUrl: String? = null,
    var labels: ArrayList<Label>? = null,
    var assignee: UserInfo? = null,
    var assignees: ArrayList<UserInfo>? = null,
    var milestone: Milestone? = null,

    @SerializedName("closed_by")
    var closedBy: UserInfo? = null,

    ) {

    enum class IssueState {
        open, closed
    }

    enum class IssueAuthorAssociation {
        OWNER, CONTRIBUTOR, NONE
    }


}


data class Label(
    var id: Long = 0,
    var name: String = "",
    var color: String? = null,
    @SerializedName("default")
    var isDefault: Boolean = false,
    var select: Boolean = false,
)

data class Milestone(
    var id: Long = 0,
    var number: Int = 0,
    var title: String = "",
    var description: String = "",
    var creator: UserInfo? = null,

    @SerializedName("open_issues")
    var openIssues: Int = 0,

    @SerializedName("closed_issues")
    var closedIssues: Int = 0,
    var state: State? = null,

    @SerializedName("created_at")
    var createdAt: Date? = null,

    @SerializedName("updated_at")
    var updatedAt: Date? = null,

    @SerializedName("due_on")
    var dueOn: Date? = null,

    @SerializedName("closed_at")
    var closedAt: Date? = null,
) {
    enum class State {
        OPEN, CLOSED
    }
}