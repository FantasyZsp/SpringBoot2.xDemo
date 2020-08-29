create table mq_message_delay
(
    id                      varchar(64)                               not null comment '主键id'
        primary key,
    channel                 varchar(128)                              not null comment '消息发送管道',
    tag                     varchar(128)                              not null comment '消息标签',
    mq_platform             tinyint      default 1                    not null comment '当前消息mq平台 1 RocketMQ 2 RabbitMQ 3 Kafka 4其他',
    mq_platform_msg_id      varchar(64)                               null comment '中间件提供的消息标识，如rocketmq中的msgId',
    business_id             varchar(64)                               null comment '业务id，方便检索',
    message                 json                                      not null comment '消息json',
    time                    datetime(6)                               not null comment '消息生效时间',
    system_context          json                                      not null comment '系统上下文',
    status                  tinyint      default 0                    not null comment '-1: send error  -2: consume error 0: created default, 1: sent 2: consumed',
    retry_times_when_failed int          default 0                    not null comment '最终失败时发起的补偿重试次数。0失败后未发起过，1~max失败后发起次数',
    total_retry_times       int          default 0                    not null comment '一共重试的次数',
    mark                    varchar(128) default '0'                  not null comment '描述',
    created_at              datetime(6)  default CURRENT_TIMESTAMP(6) not null comment '创建时间',
    updated_at              datetime(6)  default CURRENT_TIMESTAMP(6) not null on update CURRENT_TIMESTAMP(6) comment '更新时间'
);

create index idx_business_id
    on mq_message_delay (business_id, status);

create index idx_mq_platform_msg
    on mq_message_delay (mq_platform, mq_platform_msg_id);

create index idx_time_status
    on mq_message_delay (time, status);

