# Mirko Fetcher [![Build Status](https://travis-ci.org/tomekbielaszewski/mirko-fetcher.svg)](https://travis-ci.org/tomekbielaszewski/mirko-fetcher)

Download tool for latest entries on polish micro blogging service: wykop.pl/mikroblog

## usage

```text
Usage: Mirko fetcher [options]
  Options:
  * --fetch-last-x-hours, -fetch
      Fetch entries not older than given amount of hours
      Default: 0
```

`java -jar name-of-package.jar --fetch-last-x-hours 24` - downloads latest 24h history of microblog entries and saves it to local MongoDB

`java -jar name-of-package.jar -fetch 24` - downloads latest 24h history of microblog entries and saves it to local MongoDB

## packaging
`mvn clean package` - creates a Jar file