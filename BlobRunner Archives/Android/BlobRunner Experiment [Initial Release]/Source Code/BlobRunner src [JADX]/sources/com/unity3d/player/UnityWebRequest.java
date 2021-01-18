package com.unity3d.player;

import com.unity3d.player.C0049b;
import edu.wit.BlobRunner.BuildConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

class UnityWebRequest implements Runnable {

    /* renamed from: k */
    private static final HostnameVerifier f152k = new HostnameVerifier() {
        public final boolean verify(String str, SSLSession sSLSession) {
            return true;
        }
    };

    /* renamed from: a */
    private long f153a;

    /* renamed from: b */
    private String f154b;

    /* renamed from: c */
    private String f155c;

    /* renamed from: d */
    private Map f156d;

    /* renamed from: e */
    private boolean f157e;

    /* renamed from: f */
    private int f158f;

    /* renamed from: g */
    private long f159g;

    /* renamed from: h */
    private long f160h;

    /* renamed from: i */
    private boolean f161i;

    /* renamed from: j */
    private boolean f162j;

    static {
        if (CookieHandler.getDefault() == null) {
            CookieHandler.setDefault(new CookieManager());
        }
    }

    UnityWebRequest(long j, String str, Map map, String str2, boolean z, int i) {
        this.f153a = j;
        this.f154b = str2;
        this.f155c = str;
        this.f156d = map;
        this.f157e = z;
        this.f158f = i;
    }

    static void clearCookieCache(String str, String str2) {
        CookieStore cookieStore;
        CookieHandler cookieHandler = CookieHandler.getDefault();
        if (cookieHandler != null && (cookieHandler instanceof CookieManager) && (cookieStore = ((CookieManager) cookieHandler).getCookieStore()) != null) {
            if (str == null) {
                cookieStore.removeAll();
                return;
            }
            try {
                URI uri = new URI((String) null, str, str2, (String) null);
                List<HttpCookie> list = cookieStore.get(uri);
                if (list != null) {
                    for (HttpCookie remove : list) {
                        cookieStore.remove(uri, remove);
                    }
                }
            } catch (URISyntaxException unused) {
            }
        }
    }

    private static native void contentLengthCallback(long j, int i);

    private static native boolean downloadCallback(long j, ByteBuffer byteBuffer, int i);

    private static native void errorCallback(long j, int i, String str);

    private boolean hasTimedOut() {
        return this.f158f > 0 && System.currentTimeMillis() - this.f159g >= ((long) this.f158f);
    }

    private static native void headerCallback(long j, String str, String str2);

    private static native void responseCodeCallback(long j, int i);

    private void runSafe() {
        C00412 r4;
        this.f159g = System.currentTimeMillis();
        try {
            URL url = new URL(this.f154b);
            URLConnection openConnection = url.openConnection();
            openConnection.setConnectTimeout(this.f158f);
            openConnection.setReadTimeout(this.f158f);
            InputStream inputStream = null;
            if (openConnection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) openConnection;
                if (this.f157e) {
                    r4 = new C0049b.C0051b() {
                        public final void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
                            if (!UnityWebRequest.this.validateCertificateCallback((x509CertificateArr == null || x509CertificateArr.length <= 0) ? new byte[0] : x509CertificateArr[0].getEncoded())) {
                                throw new CertificateException();
                            }
                        }
                    };
                    httpsURLConnection.setHostnameVerifier(f152k);
                } else {
                    r4 = null;
                }
                SSLSocketFactory a = C0049b.m149a((C0049b.C0051b) r4);
                if (a != null) {
                    httpsURLConnection.setSSLSocketFactory(a);
                }
            }
            if (!url.getProtocol().equalsIgnoreCase("file") || url.getHost().isEmpty()) {
                boolean z = openConnection instanceof HttpURLConnection;
                if (z) {
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
                        httpURLConnection.setRequestMethod(this.f155c);
                        httpURLConnection.setInstanceFollowRedirects(false);
                        if (this.f160h > 0) {
                            if (this.f162j) {
                                httpURLConnection.setChunkedStreamingMode(0);
                            } else {
                                httpURLConnection.setFixedLengthStreamingMode((int) this.f160h);
                            }
                            if (this.f161i) {
                                httpURLConnection.addRequestProperty("Expect", "100-continue");
                            }
                        }
                    } catch (ProtocolException e) {
                        badProtocolCallback(e.toString());
                        return;
                    }
                }
                if (this.f156d != null) {
                    for (Map.Entry entry : this.f156d.entrySet()) {
                        openConnection.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(131072);
                if (uploadCallback((ByteBuffer) null) > 0) {
                    openConnection.setDoOutput(true);
                    try {
                        OutputStream outputStream = openConnection.getOutputStream();
                        while (true) {
                            int uploadCallback = uploadCallback(allocateDirect);
                            if (uploadCallback <= 0) {
                                break;
                            } else if (hasTimedOut()) {
                                outputStream.close();
                                errorCallback(this.f153a, 14, "WebRequest timed out.");
                                return;
                            } else {
                                outputStream.write(allocateDirect.array(), allocateDirect.arrayOffset(), uploadCallback);
                            }
                        }
                    } catch (Exception e2) {
                        errorCallback(e2.toString());
                        return;
                    }
                }
                if (z) {
                    try {
                        responseCodeCallback(((HttpURLConnection) openConnection).getResponseCode());
                    } catch (UnknownHostException e3) {
                        unknownHostCallback(e3.toString());
                        return;
                    } catch (SSLException e4) {
                        sslCannotConnectCallback(e4);
                        return;
                    } catch (SocketTimeoutException e5) {
                        errorCallback(this.f153a, 14, e5.toString());
                        return;
                    } catch (IOException e6) {
                        errorCallback(e6.toString());
                        return;
                    }
                }
                Map<String, List<String>> headerFields = openConnection.getHeaderFields();
                headerCallback(headerFields);
                if ((headerFields == null || !headerFields.containsKey("content-length")) && openConnection.getContentLength() != -1) {
                    headerCallback("content-length", String.valueOf(openConnection.getContentLength()));
                }
                if ((headerFields == null || !headerFields.containsKey("content-type")) && openConnection.getContentType() != null) {
                    headerCallback("content-type", openConnection.getContentType());
                }
                if (headerFields != null && headerFields.containsKey("Set-Cookie") && CookieHandler.getDefault() != null && (CookieHandler.getDefault() instanceof CookieManager)) {
                    CookieStore cookieStore = ((CookieManager) CookieHandler.getDefault()).getCookieStore();
                    for (String parse : headerFields.get("Set-Cookie")) {
                        try {
                            HttpCookie httpCookie = HttpCookie.parse(parse).get(0);
                            if (httpCookie.getPath() != null && !httpCookie.getPath().equals(BuildConfig.FLAVOR)) {
                                if (httpCookie.getDomain() == null || httpCookie.getDomain().equals(url.getHost())) {
                                    URI uri = new URI(url.getProtocol(), url.getHost(), httpCookie.getPath(), (String) null);
                                    httpCookie.setDomain(url.getHost());
                                    cookieStore.add(uri, httpCookie);
                                }
                            }
                        } catch (URISyntaxException e7) {
                            C0057g.Log(6, "UnityWebRequest: error constructing URI: " + e7.getMessage());
                        }
                    }
                }
                contentLengthCallback(openConnection.getContentLength());
                try {
                    if (openConnection instanceof HttpURLConnection) {
                        HttpURLConnection httpURLConnection2 = (HttpURLConnection) openConnection;
                        responseCodeCallback(httpURLConnection2.getResponseCode());
                        inputStream = httpURLConnection2.getErrorStream();
                    }
                    if (inputStream == null) {
                        inputStream = openConnection.getInputStream();
                    }
                    ReadableByteChannel newChannel = Channels.newChannel(inputStream);
                    while (true) {
                        int read = newChannel.read(allocateDirect);
                        if (read != -1) {
                            if (!hasTimedOut()) {
                                if (!downloadCallback(allocateDirect, read)) {
                                    break;
                                }
                                allocateDirect.clear();
                            } else {
                                newChannel.close();
                                errorCallback(this.f153a, 14, "WebRequest timed out.");
                                return;
                            }
                        } else {
                            break;
                        }
                    }
                    newChannel.close();
                } catch (UnknownHostException e8) {
                    unknownHostCallback(e8.toString());
                } catch (SSLException e9) {
                    sslCannotConnectCallback(e9);
                } catch (SocketTimeoutException e10) {
                    errorCallback(this.f153a, 14, e10.toString());
                } catch (IOException e11) {
                    errorCallback(this.f153a, 12, e11.toString());
                } catch (Exception e12) {
                    errorCallback(e12.toString());
                }
            } else {
                malformattedUrlCallback("file:// must use an absolute path");
            }
        } catch (MalformedURLException e13) {
            malformattedUrlCallback(e13.toString());
        } catch (IOException e14) {
            errorCallback(e14.toString());
        }
    }

    private static native int uploadCallback(long j, ByteBuffer byteBuffer);

    private static native boolean validateCertificateCallback(long j, byte[] bArr);

    /* access modifiers changed from: protected */
    public void badProtocolCallback(String str) {
        errorCallback(this.f153a, 4, str);
    }

    /* access modifiers changed from: protected */
    public void contentLengthCallback(int i) {
        contentLengthCallback(this.f153a, i);
    }

    /* access modifiers changed from: protected */
    public boolean downloadCallback(ByteBuffer byteBuffer, int i) {
        return downloadCallback(this.f153a, byteBuffer, i);
    }

    /* access modifiers changed from: protected */
    public void errorCallback(String str) {
        errorCallback(this.f153a, 2, str);
    }

    /* access modifiers changed from: protected */
    public void headerCallback(String str, String str2) {
        headerCallback(this.f153a, str, str2);
    }

    /* access modifiers changed from: protected */
    public void headerCallback(Map map) {
        if (map != null && map.size() != 0) {
            for (Map.Entry entry : map.entrySet()) {
                String str = (String) entry.getKey();
                if (str == null) {
                    str = "Status";
                }
                for (String headerCallback : (List) entry.getValue()) {
                    headerCallback(str, headerCallback);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void malformattedUrlCallback(String str) {
        errorCallback(this.f153a, 5, str);
    }

    /* access modifiers changed from: protected */
    public void responseCodeCallback(int i) {
        responseCodeCallback(this.f153a, i);
    }

    public void run() {
        try {
            runSafe();
        } catch (Exception e) {
            errorCallback(e.toString());
        }
    }

    /* access modifiers changed from: package-private */
    public void setupTransferSettings(long j, boolean z, boolean z2) {
        this.f160h = j;
        this.f161i = z;
        this.f162j = z2;
    }

    /* access modifiers changed from: protected */
    public void sslCannotConnectCallback(SSLException sSLException) {
        int i;
        String sSLException2 = sSLException.toString();
        Throwable th = sSLException;
        while (true) {
            if (th == null) {
                i = 16;
                break;
            } else if (th instanceof SSLKeyException) {
                i = 23;
                break;
            } else if ((th instanceof SSLPeerUnverifiedException) || (th instanceof CertPathValidatorException)) {
                i = 25;
            } else {
                th = th.getCause();
            }
        }
        i = 25;
        errorCallback(this.f153a, i, sSLException2);
    }

    /* access modifiers changed from: protected */
    public void unknownHostCallback(String str) {
        errorCallback(this.f153a, 7, str);
    }

    /* access modifiers changed from: protected */
    public int uploadCallback(ByteBuffer byteBuffer) {
        return uploadCallback(this.f153a, byteBuffer);
    }

    /* access modifiers changed from: protected */
    public boolean validateCertificateCallback(byte[] bArr) {
        return validateCertificateCallback(this.f153a, bArr);
    }
}
