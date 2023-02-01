package com.gaaji.chatmessage.global.constants;

public class StompConstants {

    public static final String MAIN_ENDPOINT = "/ws/gaaji-chat";

    public static final String PUBLISH_PREFIX_APP = "/app";
        public static final String ENDPOINT_APP_CHAT = "/chat";
        public static final String ENDPOINT_APP_CHAT_LIST = "/chat/list";

    public static final String SUBSCRIBE_PREFIX_TOPIC = "/topic";
        public static final String ENDPOINT_TOPIC_CHAT_ROOM = SUBSCRIBE_PREFIX_TOPIC + "/chat/room/";

    public static final String SUBSCRIBE_PREFIX_QUEUE = "/queue";
        public static final String ENDPOINT_QUEUE_CHAT_LIST = SUBSCRIBE_PREFIX_QUEUE + "/chat/list";

    public static final String SUBSCRIBE_PREFIX_USER = "/user";



}
