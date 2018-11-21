# Spring Cloud Book

<h1>Microservice Patterns</h1>

<h2>Core Development Patterns</h2>
<p>
  The core development microservice development pattern addresses the basics of building a microservice.
</p>
<ol>
  <li>
    <b>Service granularity:<b> Making a service
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
