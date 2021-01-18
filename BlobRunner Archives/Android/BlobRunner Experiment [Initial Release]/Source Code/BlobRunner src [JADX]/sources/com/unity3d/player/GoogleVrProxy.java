package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import com.unity3d.player.GoogleVrVideo;
import edu.wit.BlobRunner.BuildConfig;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

class GoogleVrProxy extends C0052c implements GoogleVrVideo {

    /* renamed from: f */
    private boolean f7f = false;

    /* renamed from: g */
    private boolean f8g = false;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public Runnable f9h = null;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public Vector f10i = new Vector();

    /* renamed from: j */
    private SurfaceView f11j = null;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public C0005a f12k = new C0005a();

    /* renamed from: l */
    private Thread f13l = null;

    /* renamed from: m */
    private Handler f14m = new Handler(Looper.getMainLooper()) {
        public final void handleMessage(Message message) {
            if (message.what != 135711) {
                super.handleMessage(message);
                return;
            }
            switch (message.arg1) {
                case 2147483645:
                    Iterator it = GoogleVrProxy.this.f10i.iterator();
                    while (it.hasNext()) {
                        ((GoogleVrVideo.GoogleVrVideoCallbacks) it.next()).onFrameAvailable();
                    }
                    return;
                case 2147483646:
                    Surface surface = (Surface) message.obj;
                    Iterator it2 = GoogleVrProxy.this.f10i.iterator();
                    while (it2.hasNext()) {
                        ((GoogleVrVideo.GoogleVrVideoCallbacks) it2.next()).onSurfaceAvailable(surface);
                    }
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    };

    /* renamed from: com.unity3d.player.GoogleVrProxy$a */
    class C0005a {

        /* renamed from: a */
        public boolean f26a = false;

        /* renamed from: b */
        public boolean f27b = false;

        /* renamed from: c */
        public boolean f28c = false;

        /* renamed from: d */
        public boolean f29d = false;

        /* renamed from: e */
        public boolean f30e = true;

        /* renamed from: f */
        public boolean f31f = false;

        C0005a() {
        }

        /* renamed from: a */
        public final boolean mo42a() {
            return this.f26a && this.f27b;
        }

        /* renamed from: b */
        public final void mo43b() {
            this.f26a = false;
            this.f27b = false;
            this.f29d = false;
            this.f30e = true;
            this.f31f = false;
        }
    }

    public GoogleVrProxy(C0056f fVar) {
        super("Google VR", fVar);
        initVrJni();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m11a(boolean z) {
        this.f12k.f29d = z;
    }

    /* renamed from: a */
    private static boolean m12a(int i) {
        return Build.VERSION.SDK_INT >= i;
    }

    /* renamed from: a */
    private boolean m13a(ClassLoader classLoader) {
        try {
            Class<?> loadClass = classLoader.loadClass("com.unity3d.unitygvr.GoogleVR");
            C0070o oVar = new C0070o(loadClass, loadClass.getConstructor(new Class[0]).newInstance(new Object[0]));
            oVar.mo238a("initialize", new Class[]{Activity.class, Context.class, SurfaceView.class, Boolean.TYPE, Handler.class});
            oVar.mo238a("deinitialize", new Class[0]);
            oVar.mo238a("load", new Class[]{Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Runnable.class});
            oVar.mo238a("enable", new Class[]{Boolean.TYPE});
            oVar.mo238a("unload", new Class[0]);
            oVar.mo238a("pause", new Class[0]);
            oVar.mo238a("resume", new Class[0]);
            oVar.mo238a("getGvrLayout", new Class[0]);
            oVar.mo238a("getVideoSurfaceId", new Class[0]);
            oVar.mo238a("getVideoSurface", new Class[0]);
            this.f211a = oVar;
            return true;
        } catch (Exception e) {
            reportError("Exception initializing GoogleVR from Unity library. " + e.getLocalizedMessage());
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public boolean m16d() {
        return this.f12k.f29d;
    }

    /* renamed from: e */
    private void m18e() {
        Activity activity = (Activity) this.f213c;
        if (this.f8g && !this.f12k.f31f && activity != null) {
            this.f12k.f31f = true;
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            intent.setFlags(268435456);
            activity.startActivity(intent);
        }
    }

    private final native void initVrJni();

    private final native boolean isQuiting();

    private final native void setVrVideoTransform(float[][] fArr);

    /* renamed from: a */
    public final void mo23a(Intent intent) {
        if (intent != null && intent.getBooleanExtra("android.intent.extra.VR_LAUNCH", false)) {
            this.f8g = true;
        }
    }

    /* renamed from: a */
    public final boolean mo24a() {
        return this.f12k.f26a;
    }

    /* renamed from: a */
    public final boolean mo25a(Activity activity, Context context, SurfaceView surfaceView, Runnable runnable) {
        String str;
        boolean z;
        if (activity == null || context == null || surfaceView == null || runnable == null) {
            str = "Invalid parameters passed to Google VR initiialization.";
        } else {
            this.f12k.mo43b();
            this.f213c = context;
            this.f9h = runnable;
            if (!m12a(19)) {
                str = "Google VR requires a device that supports an api version of 19 (KitKat) or better.";
            } else if (this.f8g && !m12a(24)) {
                str = "Daydream requires a device that supports an api version of 24 (Nougat) or better.";
            } else if (!m13a(UnityPlayer.class.getClassLoader())) {
                return false;
            } else {
                try {
                    z = ((Boolean) this.f211a.mo237a("initialize", activity, context, surfaceView, Boolean.valueOf(this.f8g), this.f14m)).booleanValue();
                } catch (Exception e) {
                    reportError("Exception while trying to intialize Unity Google VR Library. " + e.getLocalizedMessage());
                    z = false;
                }
                if (!z) {
                    str = "Unable to initialize GoogleVR library.";
                } else {
                    this.f11j = surfaceView;
                    this.f12k.f26a = true;
                    this.f214d = BuildConfig.FLAVOR;
                    return true;
                }
            }
        }
        reportError(str);
        return false;
    }

    /* renamed from: b */
    public final void mo26b() {
        resumeGvrLayout();
    }

    /* renamed from: c */
    public final void mo27c() {
        if (this.f11j != null) {
            this.f11j.getHolder().setSizeFromLayout();
        }
    }

    public void deregisterGoogleVrVideoListener(GoogleVrVideo.GoogleVrVideoCallbacks googleVrVideoCallbacks) {
        if (this.f10i.contains(googleVrVideoCallbacks)) {
            googleVrVideoCallbacks.onSurfaceUnavailable();
            this.f10i.remove(googleVrVideoCallbacks);
        }
    }

    /* access modifiers changed from: protected */
    public Object getVideoSurface() {
        if (m16d() && !this.f12k.f30e) {
            try {
                return this.f211a.mo237a("getVideoSurface", new Object[0]);
            } catch (Exception e) {
                reportError("Exception caught while Getting GoogleVR Video Surface. " + e.getLocalizedMessage());
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int getVideoSurfaceId() {
        if (m16d() && !this.f12k.f30e) {
            try {
                return ((Integer) this.f211a.mo237a("getVideoSurfaceId", new Object[0])).intValue();
            } catch (Exception e) {
                reportError("Exception caught while getting Video Surface ID from GoogleVR. " + e.getLocalizedMessage());
            }
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public long loadGoogleVr(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (!this.f12k.f26a) {
            return 0;
        }
        AtomicLong atomicLong = new AtomicLong(0);
        this.f214d = (z || z2) ? "Daydream" : "Cardboard";
        final AtomicLong atomicLong2 = atomicLong;
        final boolean z6 = z;
        final boolean z7 = z2;
        final boolean z8 = z3;
        final boolean z9 = z4;
        final boolean z10 = z5;
        if (!runOnUiThreadWithSync(new Runnable() {
            public final void run() {
                try {
                    atomicLong2.set(((Long) GoogleVrProxy.this.f211a.mo237a("load", Boolean.valueOf(z6), Boolean.valueOf(z7), Boolean.valueOf(z8), Boolean.valueOf(z9), Boolean.valueOf(z10), GoogleVrProxy.this.f9h)).longValue());
                    GoogleVrProxy.this.f12k.f27b = true;
                } catch (Exception e) {
                    GoogleVrProxy googleVrProxy = GoogleVrProxy.this;
                    googleVrProxy.reportError("Exception caught while loading GoogleVR. " + e.getLocalizedMessage());
                    atomicLong2.set(0);
                }
            }
        }) || atomicLong.longValue() == 0) {
            reportError("Google VR had a fatal issue while loading. VR will not be available.");
        }
        return atomicLong.longValue();
    }

    /* access modifiers changed from: protected */
    public void pauseGvrLayout() {
        if (this.f12k.mo42a() && !this.f12k.f30e) {
            if (m16d()) {
                Iterator it = this.f10i.iterator();
                while (it.hasNext()) {
                    ((GoogleVrVideo.GoogleVrVideoCallbacks) it.next()).onSurfaceUnavailable();
                }
            }
            if (this.f211a != null) {
                this.f211a.mo237a("pause", new Object[0]);
            }
            this.f12k.f30e = true;
        }
    }

    public void registerGoogleVrVideoListener(GoogleVrVideo.GoogleVrVideoCallbacks googleVrVideoCallbacks) {
        if (!this.f10i.contains(googleVrVideoCallbacks)) {
            this.f10i.add(googleVrVideoCallbacks);
            Surface surface = (Surface) getVideoSurface();
            if (surface != null) {
                googleVrVideoCallbacks.onSurfaceAvailable(surface);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void resumeGvrLayout() {
        if (this.f12k.mo42a() && this.f12k.f30e) {
            if (this.f211a != null) {
                this.f211a.mo237a("resume", new Object[0]);
            }
            this.f12k.f30e = false;
        }
    }

    /* access modifiers changed from: protected */
    public void setGoogleVrModeEnabled(final boolean z) {
        if (this.f12k.mo42a() && this.f212b != null && this.f213c != null) {
            if (!z && isQuiting()) {
                m18e();
            }
            runOnUiThread(new Runnable() {
                public final void run() {
                    if (z != GoogleVrProxy.this.m16d()) {
                        try {
                            if (!z || GoogleVrProxy.this.m16d()) {
                                if (!z && GoogleVrProxy.this.m16d()) {
                                    GoogleVrProxy.this.m11a(false);
                                    if (GoogleVrProxy.this.f211a != null) {
                                        GoogleVrProxy.this.f211a.mo237a("enable", false);
                                    }
                                    if (GoogleVrProxy.this.f211a != null && GoogleVrProxy.this.f212b != null) {
                                        GoogleVrProxy.this.f212b.removeViewFromPlayer((View) GoogleVrProxy.this.f211a.mo237a("getGvrLayout", new Object[0]));
                                    }
                                }
                            } else if (GoogleVrProxy.this.f211a == null || GoogleVrProxy.this.f212b == null || GoogleVrProxy.this.f212b.addViewToPlayer((View) GoogleVrProxy.this.f211a.mo237a("getGvrLayout", new Object[0]), true)) {
                                if (GoogleVrProxy.this.f211a != null) {
                                    GoogleVrProxy.this.f211a.mo237a("enable", true);
                                }
                                GoogleVrProxy.this.m11a(true);
                            } else {
                                GoogleVrProxy.this.reportError("Unable to add Google VR to view hierarchy.");
                            }
                        } catch (Exception e) {
                            GoogleVrProxy googleVrProxy = GoogleVrProxy.this;
                            googleVrProxy.reportError("Exception enabling Google VR on UI Thread. " + e.getLocalizedMessage());
                        }
                    }
                }
            });
        }
    }

    public void setVideoLocationTransform(float[] fArr) {
        float[][] fArr2 = (float[][]) Array.newInstance(float.class, new int[]{4, 4});
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                fArr2[i][i2] = fArr[(i * 4) + i2];
            }
        }
        setVrVideoTransform(fArr2);
    }

    /* access modifiers changed from: protected */
    public void unloadGoogleVr() {
        if (this.f12k.f29d) {
            setGoogleVrModeEnabled(false);
        }
        if (this.f12k.f28c) {
            this.f12k.f28c = false;
        }
        this.f11j = null;
        runOnUiThread(new Runnable() {
            public final void run() {
                try {
                    if (GoogleVrProxy.this.f211a != null) {
                        GoogleVrProxy.this.f211a.mo237a("unload", new Object[0]);
                        GoogleVrProxy.this.f211a.mo237a("deinitialize", new Object[0]);
                        GoogleVrProxy.this.f211a = null;
                    }
                    GoogleVrProxy.this.f12k.f27b = false;
                } catch (Exception e) {
                    GoogleVrProxy googleVrProxy = GoogleVrProxy.this;
                    googleVrProxy.reportError("Exception unloading Google VR on UI Thread. " + e.getLocalizedMessage());
                }
            }
        });
    }
}
