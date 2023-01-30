package com.dgahn.slack

import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.webhook.Payload
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class SlackCommandRunner(
    private val slackClient: SlackClient
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val withBlocks = withBlocks {
            section {
                plainText("hello")
            }
            section {
                plainText("world")
            }
        }
        val payload = Payload.builder().blocks(withBlocks).build()
        slackClient.send(payload)
    }
}
