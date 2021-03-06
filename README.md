### Event Store Journal and Snapshot Store for Akka Persistence [![Build Status](https://travis-ci.org/EventStore/EventStore.Akka.Persistence.png?branch=master)](https://travis-ci.org/EventStore/EventStore.Akka.Persistence)

[Akka Persistence](http://doc.akka.io/docs/akka/2.3.3/scala/persistence.html) journal and snapshot-store backed by [Event Store](http://geteventstore.com/).

To use this plugin prior default one, add the following to `application.conf`:

```
akka.persistence {
  journal.plugin = eventstore.persistence.journal
  snapshot-store.plugin = eventstore.persistence.snapshot-store
}
```

To configure EventStore.JVM client, see it's [reference.conf](https://github.com/EventStore/EventStore.JVM/blob/master/src/main/resources/reference.conf)

### JSON serialization

Akka serializes your messages into binary data by default.
However you can [add your own serializer](http://doc.akka.io/docs/akka/2.3.3/scala/serialization.html#Customization) to serialize as JSON,
But make sure to extend `akka.persistence.eventstore.EventStoreSerializer` rather then `akka.serialization.Serializer`
 
```scala
class JsonSerializer extends EventStoreSerializer {
  def contentType = ContentType.Json // This will tell Event Store to handle your data as JSON
}
```
 
Please check out some real examples used in tests:
* [json4s](src/test/scala/akka/persistence/eventstore/Json4sSerializer.scala)
* [spray-json](src/test/scala/akka/persistence/eventstore/SprayJsonSerializer.scala)

### Setup

* Maven:

Add to `pom.xml`

```xml
    <dependency>
        <groupId>com.geteventstore</groupId>
        <artifactId>akka-persistence-eventstore_2.11</artifactId>
        <version>0.0.2</version>
    </dependency>
```

* Sbt

Add to `build.sbt`

```scala
    libraryDependencies += "com.geteventstore" %% "akka-persistence-eventstore" % "0.0.2"
```
