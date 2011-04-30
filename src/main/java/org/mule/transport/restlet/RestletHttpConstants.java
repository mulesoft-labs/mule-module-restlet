/*
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.restlet;

/**
 * Constants related to the HTTP protocol taken from the old
 * <a href="http://www.restlet.org/documentation/1.1/nre/com/noelios/restlet/http/HttpConstants.html">HttpConstants</a>
 * coming from the 1.x restlet module, added here to do not have to rewrite a 
 * few classes that were using those constants.
 * TODO AA either find an other class that provides those constants or 
 * modify/rename this class and remove any duplicate.
 * @author Jerome Louvel
 * @author <a href="mailto:alberto.aresca@gmail.co">Alberto Aresca</a>
 */
public final class RestletHttpConstants {
    // ---------------------
    // --- Status codes ---
    // ---------------------

    public static final int STATUS_INFO_CONTINUE = 100;

    public static final int STATUS_INFO_SWITCHING_PROTOCOL = 101;

    public static final int STATUS_INFO_PROCESSING = 102;

    public static final int STATUS_SUCCESS_OK = 200;

    public static final int STATUS_SUCCESS_CREATED = 201;

    public static final int STATUS_SUCCESS_ACCEPTED = 202;

    public static final int STATUS_SUCCESS_NON_AUTHORITATIVE = 203;

    public static final int STATUS_SUCCESS_NO_CONTENT = 204;

    public static final int STATUS_SUCCESS_RESET_CONTENT = 205;

    public static final int STATUS_SUCCESS_PARTIAL_CONTENT = 206;

    public static final int STATUS_SUCCESS_MULTI_STATUS = 207;

    public static final int STATUS_REDIRECTION_MULTIPLE_CHOICES = 300;

    public static final int STATUS_REDIRECTION_MOVED_PERMANENTLY = 301;

    public static final int STATUS_REDIRECTION_FOUND = 302;

    public static final int STATUS_REDIRECTION_SEE_OTHER = 303;

    public static final int STATUS_REDIRECTION_NOT_MODIFIED = 304;

    public static final int STATUS_REDIRECTION_USE_PROXY = 305;

    public static final int STATUS_REDIRECTION_MOVED_TEMPORARILY = 307;

    public static final int STATUS_CLIENT_ERROR_BAD_REQUEST = 400;

    public static final int STATUS_CLIENT_ERROR_UNAUTHORIZED = 401;

    public static final int STATUS_CLIENT_ERROR_PAYMENT_REQUIRED = 402;

    public static final int STATUS_CLIENT_ERROR_FORBIDDEN = 403;

    public static final int STATUS_CLIENT_ERROR_NOT_FOUND = 404;

    public static final int STATUS_CLIENT_ERROR_METHOD_NOT_ALLOWED = 405;

    public static final int STATUS_CLIENT_ERROR_NOT_ACCEPTABLE = 406;

    public static final int STATUS_CLIENT_ERROR_PROXY_AUTHENTIFICATION_REQUIRED = 407;

    public static final int STATUS_CLIENT_ERROR_REQUEST_TIMEOUT = 408;

    public static final int STATUS_CLIENT_ERROR_CONFLICT = 409;

    public static final int STATUS_CLIENT_ERROR_GONE = 410;

    public static final int STATUS_CLIENT_ERROR_LENGTH_REQUIRED = 411;

    public static final int STATUS_CLIENT_ERROR_PRECONDITION_FAILED = 412;

    public static final int STATUS_CLIENT_ERROR_REQUEST_ENTITY_TOO_LARGE = 413;

    public static final int STATUS_CLIENT_ERROR_REQUEST_URI_TOO_LONG = 414;

    public static final int STATUS_CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE = 415;

    public static final int STATUS_CLIENT_ERROR_REQUESTED_RANGE_NOT_SATISFIABLE = 416;

    public static final int STATUS_CLIENT_ERROR_EXPECTATION_FAILED = 417;

    public static final int STATUS_CLIENT_ERROR_UNPROCESSABLE_ENTITY = 422;

    public static final int STATUS_CLIENT_ERROR_LOCKED = 423;

    public static final int STATUS_CLIENT_ERROR_FAILED_DEPENDENCY = 424;

    public static final int STATUS_SERVER_ERROR_INTERNAL = 500;

    public static final int STATUS_SERVER_ERROR_NOT_IMPLEMENTED = 501;

    public static final int STATUS_SERVER_ERROR_BAD_GATEWAY = 502;

    public static final int STATUS_SERVER_ERROR_SERVICE_UNAVAILABLE = 503;

    public static final int STATUS_SERVER_ERROR_GATEWAY_TIMEOUT = 504;

    public static final int STATUS_SERVER_ERROR_HTTP_VERSION_NOT_SUPPORTED = 505;

    public static final int STATUS_SERVER_ERROR_INSUFFICIENT_STORAGE = 507;

    // ---------------------
    // --- Header names ---
    // ---------------------

    public static final String HEADER_ACCEPT = "Accept";

    public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";

    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";

    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";

    public static final String HEADER_ACCEPT_RANGES = "Accept-Ranges";

    public static final String HEADER_AGE = "Age";

    public static final String HEADER_ALLOW = "Allow";

    public static final String HEADER_AUTHENTICATION_INFO = "Authentication-Info";

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String HEADER_CACHE_CONTROL = "Cache-Control";

    public static final String HEADER_CONNECTION = "Connection";

    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";

    public static final String HEADER_CONTENT_LANGUAGE = "Content-Language";

    public static final String HEADER_CONTENT_LENGTH = "Content-Length";

    public static final String HEADER_CONTENT_LOCATION = "Content-Location";

    public static final String HEADER_CONTENT_MD5 = "Content-MD5";

    public static final String HEADER_CONTENT_RANGE = "Content-Range";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    public static final String HEADER_COOKIE = "Cookie";

    public static final String HEADER_DATE = "Date";

    public static final String HEADER_ETAG = "ETag";

    public static final String HEADER_EXPECT = "Expect";

    public static final String HEADER_EXPIRES = "Expires";

    public static final String HEADER_FROM = "From";

    public static final String HEADER_HOST = "Host";

    public static final String HEADER_IF_MATCH = "If-Match";

    public static final String HEADER_IF_MODIFIED_SINCE = "If-Modified-Since";

    public static final String HEADER_IF_NONE_MATCH = "If-None-Match";

    public static final String HEADER_IF_RANGE = "If-Range";

    public static final String HEADER_IF_UNMODIFIED_SINCE = "If-Unmodified-Since";

    public static final String HEADER_LAST_MODIFIED = "Last-Modified";

    public static final String HEADER_LOCATION = "Location";

    public static final String HEADER_MAX_FORWARDS = "Max-Forwards";

    public static final String HEADER_PRAGMA = "Pragma";

    public static final String HEADER_PROXY_AUTHENTICATE = "Proxy-Authenticate";

    public static final String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";

    public static final String HEADER_RANGE = "Range";

    public static final String HEADER_REFERRER = "Referer";

    public static final String HEADER_RETRY_AFTER = "Retry-After";

    public static final String HEADER_SERVER = "Server";

    public static final String HEADER_SET_COOKIE = "Set-Cookie";

    public static final String HEADER_SET_COOKIE2 = "Set-Cookie2";

    public static final String HEADER_TRAILER = "Trailer";

    public static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";

    public static final String HEADER_TRANSFER_EXTENSION = "TE";

    public static final String HEADER_UPGRADE = "Upgrade";

    public static final String HEADER_USER_AGENT = "User-Agent";

    public static final String HEADER_VARY = "Vary";

    public static final String HEADER_VIA = "Via";

    public static final String HEADER_WARNING = "Warning";

    public static final String HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";

    public static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";

    public static final String HEADER_X_HTTP_METHOD_OVERRIDE = "X-HTTP-Method-Override";

    // -------------------------
    // --- Attribute names ---
    // -------------------------

    public static final String ATTRIBUTE_HEADERS = "org.restlet.http.headers";

    public static final String ATTRIBUTE_VERSION = "org.restlet.http.version";

    public static final String ATTRIBUTE_HTTPS_CLIENT_CERTIFICATES = "org.restlet.https.clientCertificates";

    public static final String ATTRIBUTE_HTTPS_CIPHER_SUITE = "org.restlet.https.cipherSuite";

    public static final String ATTRIBUTE_HTTPS_KEY_SIZE = "org.restlet.https.keySize";
}
