package org.openid4java.samples;

import org.openid4java.consumer.ConsumerManager;

public class SteamLoginDevelopmentHelper {

    public static void main(String[] args) {
        try {
            final var consumer = new ConsumerManager();
            consumer.setMaxAssocAttempts(0);

            final var discoveries = consumer.discover("http://steamcommunity.com/openid");
            final var info = consumer.associate(discoveries);
            System.out.println(info);

            final var authUri = consumer.authenticate(info, "http://localhost:3000/callback");
            System.out.println(authUri.getDestinationUrl(true));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
