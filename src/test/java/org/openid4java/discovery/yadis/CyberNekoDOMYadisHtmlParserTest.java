/*
 * Copyright 2006-2008 Sxip Identity Corporation
 */

package org.openid4java.discovery.yadis;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Sutra Zhou
 * 
 */
public class CyberNekoDOMYadisHtmlParserTest extends TestCase
{
    private CyberNekoDOMYadisHtmlParser parser;

    /**
     * {@inheritDoc}
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        parser = new CyberNekoDOMYadisHtmlParser();
    }

    /**
     * Test method for
     * {@link org.openid4java.discovery.yadis.CyberNekoDOMYadisHtmlParser#getHtmlMeta(java.lang.String)}
     * .
     * 
     * @throws IOException
     * @throws YadisException
     */
    public final void testGetHtmlMetaIssue83() throws IOException, YadisException
    {
        String htmlData = loadResource("issue83.html");
        String s = parser.getHtmlMeta(htmlData);
        assertEquals("http://edevil.livejournal.com/data/yadis", s);
    }

    public void testParseHtmlMetaXXE() throws Exception {
        parser.getHtmlMeta(loadResource("identityPageWithExternalEntityReference.html"));
        // don't fail trying to read "/path/to/some/file" from the input data
    }

    private String loadResource(String name) throws IOException{
        final URL resource = Objects.requireNonNull(getClass().getClassLoader().getResource(name));

        try(final BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
