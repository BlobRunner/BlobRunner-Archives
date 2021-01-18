package com.unity3d.player;

import java.util.concurrent.atomic.AtomicReference;

public class GoogleVrApi {

    /* renamed from: a */
    private static AtomicReference f6a = new AtomicReference();

    private GoogleVrApi() {
    }

    /* renamed from: a */
    static void m6a() {
        f6a.set((Object) null);
    }

    /* renamed from: a */
    static void m7a(C0056f fVar) {
        f6a.compareAndSet((Object) null, new GoogleVrProxy(fVar));
    }

    /* renamed from: b */
    static GoogleVrProxy m8b() {
        return (GoogleVrProxy) f6a.get();
    }

    public static GoogleVrVideo getGoogleVrVideo() {
        return (GoogleVrVideo) f6a.get();
    }
}
