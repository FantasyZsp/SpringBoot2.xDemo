create table mq_message_error_record
(
    id                      varchar(64)                              not null comment '主键id'
        primary key,
    msg_id                  varchar(64)                              not null comment '具体消息表id，如mq_message_delay.id,mq_message_tc.id',
    matched                 tinyint     default 1                    not null comment 'msg_id是否匹配到主消息表 1匹配 2不匹配(生产环节一定一致，但是消费环节，可能消费到不是主表中有的消息)',
    channel                 varchar(128)                             not null comment '消息发送管道或topic',
    mq_platform             tinyint     default 1                    not null comment 'mq平台 1 RocketMQ 2 RabbitMQ 3 Kafka 4其他',
    mq_platform_msg_id      varchar(64)                              null comment '中间件提供的消息标识，如rocketmq中的msgId',
    business_id             varchar(64)                              null comment '业务id，方便检索',
    error_type              tinyint                                  not null comment '错误类型: 1发送失败 2消费失败',
    retry_times             int         default 0                    not null comment '单次补偿内已重试次数',
    retry_times_when_failed int         default 0                    not null comment '最终失败时发起的补偿重试次数。0从未从失败后发起过，1~max失败后发起次数',
    error_reason            varchar(128)                             not null comment '描述，可用于记录异常原因等',
    error_code              int                                      not null comment '异常码，细化具体的异常原因。如半消息投递异常，回查异常等',
    created_at              datetime(6) default CURRENT_TIMESTAMP(6) not null comment '创建时间'
)
    comment '消息处理失败记录表';

create index idx_business_id
    on mq_message_error_record (business_id);

create index idx_channel_created_at
    on mq_message_error_record (channel, created_at);

create index idx_msg_id
    on mq_message_error_record (msg_id);

