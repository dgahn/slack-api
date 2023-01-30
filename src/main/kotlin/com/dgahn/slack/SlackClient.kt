package com.dgahn.slack

import com.slack.api.Slack
import com.slack.api.webhook.Payload
import org.springframework.stereotype.Component

@Component
class SlackClient {
    fun send(payload: Payload) {
        val slack = Slack.getInstance()
        slack.send("url", payload)
    }
}
