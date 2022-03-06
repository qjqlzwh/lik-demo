package com.lik.mq;

public class MqMessage<T> {

    // 每条消息的唯一id
    private String uid;

    // 消息体
    private T body;

    public MqMessage() {
    }

    public MqMessage(String uid, T body) {
        this.uid = uid;
        this.body = body;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public T getBody() {
        return this.body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
