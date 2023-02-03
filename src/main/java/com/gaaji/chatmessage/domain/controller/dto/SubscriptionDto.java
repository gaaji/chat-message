package com.gaaji.chatmessage.domain.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class SubscriptionDto {
    private String subscriptionId;
    private String destination;

    public static SubscriptionDto create(String subscriptionId, String destination) {
        return new SubscriptionDto(subscriptionId, destination);
    }
}
