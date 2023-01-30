package com.dgahn.slack

import com.slack.api.webhook.Payload
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SlackController(
    private val slackClient: SlackClient
) {

    @PostMapping("/api/v1/slack/message")
    suspend fun postMessage(@RequestBody requestPayload: Payload) {
        slackClient.send(requestPayload)
    }
}
