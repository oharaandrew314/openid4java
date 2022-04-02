/*
 * Copyright 2006-2008 Sxip Identity Corporation
 */

package org.openid4java.discovery.html;

import junit.framework.TestCase;
import org.openid4java.discovery.DiscoveryException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Sutra Zhou
 * 
 */
public class CyberNekoDOMHtmlParserTest extends TestCase
{
    private CyberNekoDOMHtmlParser parser;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        parser = new CyberNekoDOMHtmlParser();
    }

    /**
     * Test method for
     * {@link org.openid4java.discovery.html.CyberNekoDOMHtmlParser#parseHtml(java.lang.String, org.openid4java.discovery.html.HtmlResult)}
     * .
     * 
     * @throws IOException
     * @throws DiscoveryException
     */
    public void testParseHtml() throws IOException, DiscoveryException {
        String htmlData = loadResource("identityPage.html");
        HtmlResult result = new HtmlResult();
        parser.parseHtml(htmlData, result);
        assertEquals("http://www.example.com:8080/openidserver/users/myusername", result
                .getDelegate1());
        System.out.println(result.getOP1Endpoint());
        assertEquals("http://www.example.com:8080/openidserver/openid.server", result
                .getOP1Endpoint().toExternalForm());
    }

    /**
     * Test method for
     * {@link org.openid4java.discovery.html.CyberNekoDOMHtmlParser#parseHtml(java.lang.String, org.openid4java.discovery.html.HtmlResult)}
     * .
     * 
     * @throws IOException
     * @throws DiscoveryException
     */
    public void testParseHtmlWithXmlNamespace() throws IOException,
            DiscoveryException
    {
        String htmlData = loadResource("identityPage-with-xml-namespace.html");
        HtmlResult result = new HtmlResult();
        parser.parseHtml(htmlData, result);
        assertEquals("http://www.example.com:8080/openidserver/users/myusername", result
                .getDelegate1());
        assertEquals("http://www.example.com:8080/openidserver/openid.server", result
                .getOP1Endpoint().toExternalForm());
    }

    public void testParseHtmlXXE() throws Exception
    {
        String htmlData = loadResource("identityPageWithExternalEntityReference.html");
        parser.parseHtml(htmlData, new HtmlResult());
        // don't fail trying to read "/path/to/some/file" from the input data
    }

    private String loadResource(String name) throws IOException{
        final var resource = Objects.requireNonNull(getClass().getClassLoader().getResource(name));

        try(final var reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
