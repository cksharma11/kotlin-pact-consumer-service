package com.pactconsumer.pactconsumer

import com.sun.net.httpserver.Headers
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class UserClient {
    fun getUser(url: String): User {
        return RestTemplate().exchange(
                url,
                HttpMethod.GET,
                HttpEntity(Headers()),
                User::class.java
        ).body!!
    }
}