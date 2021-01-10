package com.pactconsumer.pactconsumer

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import au.com.dius.pact.core.model.annotations.PactFolder
import au.com.dius.pact.core.support.hasProperty
import io.pactfoundation.consumer.dsl.LambdaDsl
import junit.framework.Assert.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@PactFolder("target/pacts")
@ExtendWith(PactConsumerTestExt::class, SpringExtension::class)
class ConsumerContractTest {
    @Pact(provider = "user-provider-service", consumer = "user-consume-service")
    fun userPact(builder: PactDslWithProvider): RequestResponsePact {
        val responseBody = LambdaDsl.newJsonBody { user ->
            user.stringType("name", "someName")
            user.stringType("age", "20")
            user.stringType("lastName", "someLastName")
        }

        return builder
                .given("a user is present")
                .uponReceiving("a request to get user")
                .pathFromProviderState("/user", "/user")
                .method("GET")
                .willRespondWith()
                .body(responseBody.build())
                .toPact()
    }

    @Test
    fun `should return user`(mockServer: MockServer) {
        val url = mockServer.getUrl() + "/user"

        val user = UserClient().getUser(url)

        assertTrue(user.hasProperty("age"))
        assertTrue(user.hasProperty("name"))
        assertTrue(user.hasProperty("lastName"))
    }
}