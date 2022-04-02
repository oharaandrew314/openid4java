package org.openid4java.samples;

import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.Discovery;
import org.openid4java.discovery.html.HtmlResolver;
import org.openid4java.discovery.xri.LocalXriResolver;
import org.openid4java.discovery.yadis.YadisResolver;
import org.openid4java.util.HttpFetcherFactory;

public class SteamLoginDevelopmentHelper {

    public static void main(String[] args) {
        try {
            final var consumer = new ConsumerManager(
                    new Discovery(
                            new HtmlResolver(new HttpFetcherFactory()),
                            new YadisResolver(new HttpFetcherFactory()),
                            new LocalXriResolver()
                    )
            );
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
