package com.gaaji.chatmessage.domain.controller.dto;

import com.gaaji.chatmessage.global.constants.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class SubscriptionDto {
    private String subscriptionId;
    private String destination;
    private String subscriptionRoomId;

    public static SubscriptionDto create(String subscriptionId, String destination) {
        return new SubscriptionDto(
                subscriptionId,
                destination,
                destination.substring(ApiConstants.SUBSCRIBE_ENDPOINT.length())
        );
    }
}
