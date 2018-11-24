# Spring Cloud Book

<h1>Microservice Patterns</h1>

<h2>Core Development Patterns</h2>
<p>
  The core development microservice development pattern addresses the basics of building a microservice.
</p>
<ol>
  <li>
    <b>Service granularity:</b> Making a service
      too coarse-grained with responsibilities that overlap into different business 
      problems domains makes the service difficult to maintain and change over time. 
      Making the service too fine-grained increases the overall complexity of the 
      application and turns the service into a “dumb” data abstraction layer with no 
      logic except for that needed to access the data store.
  </li>
  <li>
    <b>Communication protocols:</b> How will developers communicate with your service?
       Do you use XML (Extensible Markup Language), JSON (JavaScript Object Notation), 
       or a binary protocol such as Thrift to send data back and forth your microservices?
       We’ll go into why JSON is the ideal choice for microservices....   
  </li>
  <li>
    <b>Interface design:</b> What’s the best way to design the actual service interfaces that
    developers are going to use to call your service? How do you structure your service URLs 
    to communicate service intent? What about versioning your services?
    A well-design microservice interface makes using your service intuitive.   
  </li>
  <li>
    <b>Configuration management of service:</b> How do you manage the configuration of
    your microservice so that as it moves between different environments in the
    cloud you never have to change the core application code or configuration?.   
  </li>
  <li>
    <b>Event processing between services:</b> How do you decouple your microservice using
    events so that you minimize hardcoded dependencies between your services
    and increase the resiliency of your application?   
  </li>
</ol>

<h2>Microservice routing patterns</h2>
<ol>
  <li>
    <b>Service discovery:</b> Service discovery abstracts away the physical location of the service from the client. 
    New microservice instances can be added to scale up, and unhealthy service instances can be transparently removed 
    from the service.
  </li>
  <li>
    <b>Service routing:</b>Service routing gives the microservice client a single logical URL 
    to talk to and acts as a  policy enforcement point for things like authorization, authentication, 
    and content checking.
  </li>
</ol>

<h2>Microservice client resiliency patterns</h2>
<p>
  Because microservice architectures are highly distributed, you have to be extremely
  sensitive in how you prevent a problem in a single service (or service instance) from
  cascading up and out to the consumers of the service. To this end, we’ll cover four cli-
  ent resiliency patterns:
</p>

<ol>
  <li>
    <b>Client-side load balancing:</b>The service client caches microservice endpoints retrieved from the service 
      discovery and   ensures that the service calls are load balanced between instances.
  </li>
  <li>
    <b>Circuit breakers pattern:</b>The circuit breaker pattern ensures that a service client does not repeatedly 
    call a failing service. Instead, a circuit breaker "fails fast" to protect the client.
  </li>
  <li>
    <b>Fallback pattern:</b>When a client does fail, is there an alternative path the client can take to retrieve 
    data from or take action with?
  </li>
  <li>
    <b>Bulkhead pattern:</b>How do you segregate different service calls on a client to make sure one misbehaving 
    service does not take up all the resources on the client?
  </li>
</ol>


<h2>Microservice security patterns</h2>
There is more note on Chapter 7.
<ol>
  <li>
    <b>Authentication</b>
  </li>
  <li>
    <b>Authorization</b>
  </li>
  <li>
    <b>Credential management and propagation</b>
  </li>
</ol>

<h2>Microservice logging and tracing patterns</h2>
There is more note on Chapter 9.
<ol>
  <li>
    <b>Log correlation:</b>All service log entries have a correlation ID that ties the log entry to a single transaction.
  </li>
  <li>
    <b>Log aggregation:</b>An aggregation mechanism collects all of the logs from all the services instances.
  </li>
  <li>
    <b>Microservice tracing:</b>The development and operations teams can query the log data to find individual 
    transactions. They should also be able to visualize the flow of all the services involved in a transaction.
  </li>
</ol>

<h2>Microservice build/deployment patterns</h2>
Note on Chapter 1.


