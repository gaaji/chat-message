package com.gaaji.chatmessage.global.config;

import com.gaaji.chatmessage.domain.entity.Chat;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class MongoEventListener extends AbstractMongoEventListener<Chat> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Chat> event) {
        super.onBeforeConvert(event);

        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.HOUR, 9);

        event.getSource().setCreatedAt(c.getTime());
    }
}
