package org.openid4java.samples;

import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.Discovery;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.html.HtmlResolver;
import org.openid4java.discovery.xri.LocalXriResolver;
import org.openid4java.discovery.yadis.YadisResolver;
import org.openid4java.message.AuthRequest;
import org.openid4java.util.HttpFetcherFactory;

import java.util.List;

public class SteamLoginDevelopmentHelper {

    public static void main(String[] args) {
        try {
            final ConsumerManager consumer = new ConsumerManager(
                    new Discovery(
                            new HtmlResolver(new HttpFetcherFactory()),
                            new YadisResolver(new HttpFetcherFactory()),
                            new LocalXriResolver()
                    )
            );
            consumer.setMaxAssocAttempts(0);

            final List<DiscoveryInformation> discoveries = consumer.discover("http://steamcommunity.com/openid");
            final DiscoveryInformation info = consumer.associate(discoveries);
            System.out.println(info);

            final AuthRequest authUri = consumer.authenticate(info, "http://localhost:3000/callback");
            System.out.println(authUri.getDestinationUrl(true));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
