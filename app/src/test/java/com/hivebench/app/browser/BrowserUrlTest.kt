package com.hivebench.app.browser

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BrowserUrlTest {
    @Test fun keepsExplicitSchemes() {
        assertEquals("https://example.com", normalizeUrl("https://example.com"))
        assertEquals("http://127.0.0.1:3000/", normalizeUrl("  http://127.0.0.1:3000/ "))
    }

    @Test fun localHostsGetHttp() {
        assertEquals("http://localhost:5173", normalizeUrl("localhost:5173"))
        assertEquals("http://127.0.0.1:8080", normalizeUrl("127.0.0.1:8080"))
        assertEquals("http://myhost:4000", normalizeUrl("myhost:4000"))
    }

    @Test fun bareDomainsGetHttps() {
        assertEquals("https://example.com", normalizeUrl("example.com"))
    }

    @Test fun blankStaysBlank() {
        assertEquals("", normalizeUrl("   "))
    }

    @Test fun mapsCommonWebViewErrors() {
        assertTrue(friendlyError("net::ERR_CONNECTION_REFUSED").contains("dev server"))
        assertTrue(friendlyError("net::ERR_CLEARTEXT_NOT_PERMITTED").contains("localhost"))
        assertEquals("weird", friendlyError("weird"))
    }
}
