# ELK日志服务搭建

```git
git clone git@github.com:deviantony/docker-elk.git
```



## 镜像加速

```yaml
{
  "registry-mirrors": [
    "https://ustc-edu-cn.mirror.aliyuncs.com/"
  ],
  "insecure-registries": [],
  "debug": false,
  "experimental": false,
  "features": {
    "buildkit": true
  }
}
```

## 启动elk

cd ./docker-elk

[可选]

```
docker-compose build --build-arg  ELK_VERSION=7.10.1
```

docker-compose up -d



## 配置应用

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashAccessTcpSocketAppender">
    <destination>localhost:5000</destination>
    <encoder charset="UTF-8" class="net.logstash.logback.encoder.LogstashEncoder"/>
  </appender>
  <include resource="org/springframework/boot/logging/logback/base.xml"/>
  <root level="INFO">
    <appender-ref ref="LOGSTASH"/>
    <appender-ref ref="CONSOLE"/>
  </root>
</configuration>
```



### 到logstash上配置索引项

http://localhost:5601/app/management/kibana/indexPatterns

### 查看日志内容

http://localhost:5601/app/discover



## 结构化日志

### 配置应用日志格式

> logback.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashAccessTcpSocketAppender">
    <destination>localhost:5000</destination>
    <encoder>
      <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
      <charset>UTF-8</charset>
    </encoder>
  </appender>
  <include resource="org/springframework/boot/logging/logback/base.xml"/>
  <root level="INFO">
    <appender-ref ref="LOGSTASH"/>
    <appender-ref ref="CONSOLE"/>
  </root>
</configuration>
```

### 配置logstash过滤器

> logstash\pipeline\logstash.conf

```conf
input {
	beats {
		port => 5044
	}

	tcp {
		port => 5000
	}
}

## Add your filters / logstash plugins configuration here

filter {
    grok {
         match => {
             "message" => "%{TIMESTAMP_ISO8601:logTime} %{GREEDYDATA:logThread} %{LOGLEVEL:logLevel} %{GREEDYDATA:logContent} - %{GREEDYDATA:logContent}"
         }
    }
}

output {
	elasticsearch {
		hosts => "elasticsearch:9200"
		user => "elastic"
		password => "changeme"
		ecs_compatibility => disabled
	}
}

```



## 接入kafka

应用接入kafka，由kafka对接logstash

### 下载kafka-docker

```bash
git clone git@github.com:wurstmeister/kafka-docker.git
```

### 启动单机kafka

```bash
cd kafka-docker
docker-compose -f docker-compose-single-broker.yml up -d
```

### 调整logstash

调整input

```conf
input {
 beats {
  port => 5044
 }

 #tcp {
 #  port => 5000
 #}

 kafka {
   id => "my_plugin_id"
   bootstrap_servers => "192.168.0.66:9092"
   topics => ["test"]
   auto_offset_reset => "latest"
 }
}

## Add your filters / logstash plugins configuration here

filter {
    grok {
         match => {
             "message" => "%{TIMESTAMP_ISO8601:logTime} %{GREEDYDATA:logThread} %{LOGLEVEL:logLevel} %{GREEDYDATA:logContent} - %{GREEDYDATA:logContent}"
         }
    }
}

output {
 elasticsearch {
  hosts => "elasticsearch:9200"
  user => "elastic"
  password => "changeme"
  ecs_compatibility => disabled
 }
}
```

### 调整应用连接

增加依赖

```xml
<dependency>
  <groupId>com.github.danielwegener</groupId>
  <artifactId>logback-kafka-appender</artifactId>
  <version>0.2.0-RC2</version>
</dependency>
```

调整logback.xml

增加kafka appender，删除以前的logstash appender

```xml
<appender name="KAFKA" class="com.github.danielwegener.logback.kafka.KafkaAppender">
    <destination>localhost:5000</destination>
    <encoder>
        <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        <charset>UTF-8</charset>
    </encoder>
    <topic>test</topic>
    <keyingStrategy class="com.github.danielwegener.logback.kafka.keying.NoKeyKeyingStrategy"/>
    <deliveryStrategy class="com.github.danielwegener.logback.kafka.delivery.AsynchronousDeliveryStrategy"/>
    <producerConfig>bootstrap.servers=192.168.0.66:9092</producerConfig>
    <appender-ref ref="CONSOLE"/>
</appender>
```