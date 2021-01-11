# kotlin-pact-consumer-service

What is Pact?

Pact is a code-first tool for testing http and message integration using CONTACT-TESTS.

What is a contact test?

Contact tests assert that inter-application application messages conforms to a shared understanding that is documented in the contact.

Why contact tests?

Without contact tests, the only way to test if an application works correctly is to have expensive integration tests.

Problem with Integration Tests?

Before we deploy an application to a production environment, we need to be sure the application works correctly with other applications it integrates with. Traditionally we do integration tests in a live environment.

Benefits : Gives confidence to release Cons : Introduce dependencies. Give slow feedback. Break easily. Requires lots of maintenance.

Why not use isolated tests?

By testing each side of an application using a simulated version of the other application, we get to sets of tests.

Pros: Runs independently. Gives fast feedback. Are stable. Are easy to maintain. Cons: Do not give confidence to release. This is because there is no guarantee that the simulated version works the same as the real one.

How does Pact help?

Pact solves the problem of keeping the two sets of tests in sync by use of a “contract” also known as “pact”.

During the consumer tests, each request to a pact mock gets recorded into the contract file, along with the expected response.

A pact simulated consumer then runs the request against the actual provider and compares the expected and actual response. If they match we are verified that the simulated application works in the same way the real application. This means that two real applications should communicate correctly in real life.

Pros: Runs independently. Gives fast feedback. Are stable. Are easy to maintain. And : Give us confidence to release.

When to use contract testing?

Contract testing is immediately applicable anywhere where two services need to communicate such as API client and web framework. Although a single client and a single service is a common use case, contact testing really shines in an environment with many services, as is common for microservice architecture. Having a well formed contract test helps developers to avoid version hell.

Contract test terminologies

In general a contract is between a consumer (the client who wants to receive the data) and a provider (the API server that provides data to the client). In microservice architecture clients, server terminologies do not always comply, when communication is achieved through a queue still the provider consumer definition applies.

Consumer driven contract testing?

Pact is code first consumer driven testing tool, and is generally used by developers and testers who code. The contract is generated during the execution of automated tests. The major advantage of this pattern is that only part of the communication which is being used by consumers gets tested.This means that providers behaviors which are not being used by consumers can be changed independently without breaking the tests.

How does Pact work?

Consumer: An application which makes use of functionality or data from another application to do its job. For applications which use HTTP consumer is always the application which initiates the HTTP request regardless of direction of flow. For a queue application consumer is the application which reads the messages from the queue.

Provider: An application often called a service that provides functionality or data for other applications to use often via an API. For applications which use HTTP, the provider is the application which provides the response. For applications which use queue the provider also called producer is the one who writes messages in queue.

A contract between a provider and a consumer is called “pact”. Each pact is a collection of interactions. Each interaction describes

For HTTP : An expected request: describes what a consumer is expected to send to the provider. A minimal expected response: describes the part of response the consumer wants the provider to return. For Messages: The minimal expected message: describes the part of message the consumer wants to use.

The first step is to write this interaction.

Consumer testing

Consumer pact test operates on each interaction described earlier to say “assuming that the provider returns the correct response for a given request, does the consumer code correctly generates the request and handle the response?”.

Each interaction is tested using a pact framework, driven by the unit testing framework inside the consumer codebase.

Using a pact DSL, expected request and response gets registered in with the mock service. Consumer tests fires a real request to mock server(created by pact framework) Mock provider compares the actual request with expected request, and emits the expected response if comparison is successful. The consumer code confirms that the response is correctly understood.

Pact tests are only successful if all the steps gets completed without any errors.

Usually the interaction definition and consumer tests are written together.

In Pact each interaction is considered to be independent. This means each test only tests one interaction. If you need to describe interaction that depends on a pre-existing state, you can use provider state to do this. Provider state allows you to describe preconditions on the provider required to generate expected response, for example the existence of specific data.

Once all interactions have been tested on the consumer side, the pact framework generates a “pact file”, which describes each interaction.

Pact file can be used to verify that the provider meets the consumers expectations.

Provider verification:

In contrast to consumer testing, provider verification is entirely driven by the pact framework.

In provider verification each request is sent to the provider, and the actual response generated is compared with the minimal expected response described in the consumer test.

Provider verification passes if each request generates a response that contains at least the data described in the minimal expected response.

In many scenarios your provider will need to be in a particular state (such as “user 123 logged in”). The pact framework supports this by letting you set up the data described by the provider state before the interaction replayed.

Putting it all together:

Practices

Contract test should focus on messages (request and responses) rather than the behaviour Pact tests should not be used as functional tests. Pact tests should be data independent. They are best when they don't depend on specific data that the provider will return. Use the broker to integrate pact with your CI infrastructure.
