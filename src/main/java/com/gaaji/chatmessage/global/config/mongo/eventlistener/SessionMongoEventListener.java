package com.gaaji.chatmessage.global.config.mongo.eventlistener;

import com.gaaji.chatmessage.domain.entity.Session;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class SessionMongoEventListener extends AbstractMongoEventListener<Session> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Session> event) {
        super.onBeforeConvert(event);

        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.HOUR, 9);

        event.getSource().setConnectedAt(c.getTime());
    }
}
