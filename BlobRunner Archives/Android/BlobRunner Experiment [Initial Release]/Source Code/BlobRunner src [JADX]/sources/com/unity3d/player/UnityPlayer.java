package com.unity3d.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import com.unity3d.player.C0065l;
import com.unity3d.player.C0075q;
import edu.wit.BlobRunner.BuildConfig;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class UnityPlayer extends FrameLayout implements C0056f {
    public static Activity currentActivity;

    /* renamed from: t */
    private static boolean f54t;

    /* renamed from: a */
    C0037e f55a = new C0037e(this, (byte) 0);

    /* renamed from: b */
    C0061k f56b = null;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public int f57c = -1;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public boolean f58d = false;

    /* renamed from: e */
    private boolean f59e = true;

    /* renamed from: f */
    private C0069n f60f = new C0069n();

    /* renamed from: g */
    private final ConcurrentLinkedQueue f61g = new ConcurrentLinkedQueue();

    /* renamed from: h */
    private BroadcastReceiver f62h = null;

    /* renamed from: i */
    private boolean f63i = false;

    /* renamed from: j */
    private C0035c f64j = new C0035c(this, (byte) 0);

    /* renamed from: k */
    private TelephonyManager f65k;

    /* renamed from: l */
    private ClipboardManager f66l;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public C0065l f67m;

    /* renamed from: n */
    private GoogleARCoreApi f68n = null;

    /* renamed from: o */
    private C0033a f69o = new C0033a();

    /* renamed from: p */
    private Camera2Wrapper f70p = null;

    /* renamed from: q */
    private HFPStatus f71q = null;
    /* access modifiers changed from: private */

    /* renamed from: r */
    public Context f72r;
    /* access modifiers changed from: private */

    /* renamed from: s */
    public SurfaceView f73s;
    /* access modifiers changed from: private */

    /* renamed from: u */
    public boolean f74u;

    /* renamed from: v */
    private boolean f75v = false;
    /* access modifiers changed from: private */

    /* renamed from: w */
    public C0075q f76w;

    /* renamed from: com.unity3d.player.UnityPlayer$a */
    class C0033a implements SensorEventListener {
        C0033a() {
        }

        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        public final void onSensorChanged(SensorEvent sensorEvent) {
        }
    }

    /* renamed from: com.unity3d.player.UnityPlayer$b */
    enum C0034b {
        ;

        static {
            f133d = new int[]{f130a, f131b, f132c};
        }
    }

    /* renamed from: com.unity3d.player.UnityPlayer$c */
    private class C0035c extends PhoneStateListener {
        private C0035c() {
        }

        /* synthetic */ C0035c(UnityPlayer unityPlayer, byte b) {
            this();
        }

        public final void onCallStateChanged(int i, String str) {
            UnityPlayer unityPlayer = UnityPlayer.this;
            boolean z = true;
            if (i != 1) {
                z = false;
            }
            unityPlayer.nativeMuteMasterAudio(z);
        }
    }

    /* renamed from: com.unity3d.player.UnityPlayer$d */
    enum C0036d {
        PAUSE,
        RESUME,
        QUIT,
        SURFACE_LOST,
        SURFACE_ACQUIRED,
        FOCUS_LOST,
        FOCUS_GAINED,
        NEXT_FRAME
    }

    /* renamed from: com.unity3d.player.UnityPlayer$e */
    private class C0037e extends Thread {

        /* renamed from: a */
        Handler f144a;

        /* renamed from: b */
        boolean f145b;

        /* renamed from: c */
        boolean f146c;

        /* renamed from: d */
        int f147d;

        /* renamed from: e */
        int f148e;

        private C0037e() {
            this.f145b = false;
            this.f146c = false;
            this.f147d = C0034b.f131b;
            this.f148e = 5;
        }

        /* synthetic */ C0037e(UnityPlayer unityPlayer, byte b) {
            this();
        }

        /* renamed from: a */
        private void m96a(C0036d dVar) {
            if (this.f144a != null) {
                Message.obtain(this.f144a, 2269, dVar).sendToTarget();
            }
        }

        /* renamed from: a */
        public final void mo128a() {
            m96a(C0036d.QUIT);
        }

        /* renamed from: a */
        public final void mo129a(Runnable runnable) {
            if (this.f144a != null) {
                m96a(C0036d.PAUSE);
                Message.obtain(this.f144a, runnable).sendToTarget();
            }
        }

        /* renamed from: b */
        public final void mo130b() {
            m96a(C0036d.RESUME);
        }

        /* renamed from: b */
        public final void mo131b(Runnable runnable) {
            if (this.f144a != null) {
                m96a(C0036d.SURFACE_LOST);
                Message.obtain(this.f144a, runnable).sendToTarget();
            }
        }

        /* renamed from: c */
        public final void mo132c() {
            m96a(C0036d.FOCUS_GAINED);
        }

        /* renamed from: c */
        public final void mo133c(Runnable runnable) {
            if (this.f144a != null) {
                Message.obtain(this.f144a, runnable).sendToTarget();
                m96a(C0036d.SURFACE_ACQUIRED);
            }
        }

        /* renamed from: d */
        public final void mo134d() {
            m96a(C0036d.FOCUS_LOST);
        }

        /* renamed from: d */
        public final void mo135d(Runnable runnable) {
            if (this.f144a != null) {
                Message.obtain(this.f144a, runnable).sendToTarget();
            }
        }

        public final void run() {
            setName("UnityMain");
            Looper.prepare();
            this.f144a = new Handler(new Handler.Callback() {
                /* renamed from: a */
                private void m105a() {
                    if (C0037e.this.f147d == C0034b.f132c && C0037e.this.f146c) {
                        UnityPlayer.this.nativeFocusChanged(true);
                        C0037e.this.f147d = C0034b.f130a;
                    }
                }

                public final boolean handleMessage(Message message) {
                    if (message.what != 2269) {
                        return false;
                    }
                    C0036d dVar = (C0036d) message.obj;
                    if (dVar == C0036d.NEXT_FRAME) {
                        UnityPlayer.this.executeGLThreadJobs();
                        if (!C0037e.this.f145b || !C0037e.this.f146c) {
                            return true;
                        }
                        if (C0037e.this.f148e >= 0) {
                            if (C0037e.this.f148e == 0 && UnityPlayer.this.m77k()) {
                                UnityPlayer.this.m43a();
                            }
                            C0037e.this.f148e--;
                        }
                        if (!UnityPlayer.this.isFinishing() && !UnityPlayer.this.nativeRender()) {
                            UnityPlayer.this.m65e();
                        }
                    } else if (dVar == C0036d.QUIT) {
                        Looper.myLooper().quit();
                    } else if (dVar == C0036d.RESUME) {
                        C0037e.this.f145b = true;
                    } else if (dVar == C0036d.PAUSE) {
                        C0037e.this.f145b = false;
                    } else if (dVar == C0036d.SURFACE_LOST) {
                        C0037e.this.f146c = false;
                    } else {
                        if (dVar == C0036d.SURFACE_ACQUIRED) {
                            C0037e.this.f146c = true;
                        } else if (dVar == C0036d.FOCUS_LOST) {
                            if (C0037e.this.f147d == C0034b.f130a) {
                                UnityPlayer.this.nativeFocusChanged(false);
                            }
                            C0037e.this.f147d = C0034b.f131b;
                        } else if (dVar == C0036d.FOCUS_GAINED) {
                            C0037e.this.f147d = C0034b.f132c;
                        }
                        m105a();
                    }
                    if (C0037e.this.f145b) {
                        Message.obtain(C0037e.this.f144a, 2269, C0036d.NEXT_FRAME).sendToTarget();
                    }
                    return true;
                }
            });
            Looper.loop();
        }
    }

    /* renamed from: com.unity3d.player.UnityPlayer$f */
    private abstract class C0039f implements Runnable {
        private C0039f() {
        }

        /* synthetic */ C0039f(UnityPlayer unityPlayer, byte b) {
            this();
        }

        /* renamed from: a */
        public abstract void mo104a();

        public final void run() {
            if (!UnityPlayer.this.isFinishing()) {
                mo104a();
            }
        }
    }

    static {
        new C0068m().mo226a();
        f54t = false;
        f54t = loadLibraryStatic("main");
    }

    public UnityPlayer(Context context) {
        super(context);
        if (context instanceof Activity) {
            currentActivity = (Activity) context;
            this.f57c = currentActivity.getRequestedOrientation();
        }
        m45a(currentActivity);
        this.f72r = context;
        if (currentActivity != null && m77k()) {
            this.f67m = new C0065l(this.f72r, C0065l.C0067a.m172a()[getSplashMode()]);
            addView(this.f67m);
        }
        m46a(this.f72r.getApplicationInfo());
        if (!C0069n.m176c()) {
            AlertDialog create = new AlertDialog.Builder(this.f72r).setTitle("Failure to initialize!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialogInterface, int i) {
                    UnityPlayer.this.m65e();
                }
            }).setMessage("Your hardware does not support this application, sorry!").create();
            create.setCancelable(false);
            create.show();
            return;
        }
        initJni(context);
        this.f60f.mo230c(true);
        this.f73s = m60c();
        addView(this.f73s);
        bringChildToFront(this.f67m);
        this.f74u = false;
        nativeInitWebRequest(UnityWebRequest.class);
        m80m();
        this.f65k = (TelephonyManager) this.f72r.getSystemService("phone");
        this.f66l = (ClipboardManager) this.f72r.getSystemService("clipboard");
        this.f70p = new Camera2Wrapper(this.f72r);
        this.f71q = new HFPStatus(this.f72r);
        this.f55a.start();
    }

    public static void UnitySendMessage(String str, String str2, String str3) {
        if (!C0069n.m176c()) {
            C0057g.Log(5, "Native libraries not loaded - dropping message for " + str + "." + str2);
            return;
        }
        nativeUnitySendMessage(str, str2, str3);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m43a() {
        mo55a((Runnable) new Runnable() {
            public final void run() {
                UnityPlayer.this.removeView(UnityPlayer.this.f67m);
                C0065l unused = UnityPlayer.this.f67m = null;
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m44a(int i, Surface surface) {
        if (!this.f58d) {
            m59b(0, surface);
        }
    }

    /* renamed from: a */
    private static void m45a(Activity activity) {
        View decorView;
        if (activity != null && activity.getIntent().getBooleanExtra("android.intent.extra.VR_LAUNCH", false) && activity.getWindow() != null && (decorView = activity.getWindow().getDecorView()) != null) {
            decorView.setSystemUiVisibility(7);
        }
    }

    /* renamed from: a */
    private static void m46a(ApplicationInfo applicationInfo) {
        if (f54t && NativeLoader.load(applicationInfo.nativeLibraryDir)) {
            C0069n.m174a();
        }
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m47a(android.view.View r5, android.view.View r6) {
        /*
            r4 = this;
            com.unity3d.player.n r0 = r4.f60f
            boolean r0 = r0.mo232d()
            r1 = 0
            if (r0 != 0) goto L_0x000e
            r4.pause()
            r0 = 1
            goto L_0x000f
        L_0x000e:
            r0 = 0
        L_0x000f:
            if (r5 == 0) goto L_0x0030
            android.view.ViewParent r2 = r5.getParent()
            boolean r3 = r2 instanceof com.unity3d.player.UnityPlayer
            if (r3 == 0) goto L_0x001e
            r3 = r2
            com.unity3d.player.UnityPlayer r3 = (com.unity3d.player.UnityPlayer) r3
            if (r3 == r4) goto L_0x0030
        L_0x001e:
            boolean r3 = r2 instanceof android.view.ViewGroup
            if (r3 == 0) goto L_0x0027
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            r2.removeView(r5)
        L_0x0027:
            r4.addView(r5)
            r4.bringChildToFront(r5)
            r5.setVisibility(r1)
        L_0x0030:
            if (r6 == 0) goto L_0x0040
            android.view.ViewParent r5 = r6.getParent()
            if (r5 != r4) goto L_0x0040
            r5 = 8
            r6.setVisibility(r5)
            r4.removeView(r6)
        L_0x0040:
            if (r0 == 0) goto L_0x0045
            r4.resume()
        L_0x0045:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.player.UnityPlayer.m47a(android.view.View, android.view.View):void");
    }

    /* renamed from: a */
    private void m48a(C0039f fVar) {
        if (!isFinishing()) {
            m57b((Runnable) fVar);
        }
    }

    /* renamed from: b */
    private void m57b(Runnable runnable) {
        if (C0069n.m176c()) {
            if (Thread.currentThread() == this.f55a) {
                runnable.run();
            } else {
                this.f61g.add(runnable);
            }
        }
    }

    /* renamed from: b */
    private static boolean m58b() {
        if (currentActivity == null) {
            return false;
        }
        TypedValue typedValue = new TypedValue();
        return currentActivity.getTheme().resolveAttribute(16842840, typedValue, true) && typedValue.type == 18 && typedValue.data != 0;
    }

    /* renamed from: b */
    private boolean m59b(final int i, final Surface surface) {
        if (!C0069n.m176c() || !this.f60f.mo233e()) {
            return false;
        }
        final Semaphore semaphore = new Semaphore(0);
        C002220 r1 = new Runnable() {
            public final void run() {
                UnityPlayer.this.nativeRecreateGfxState(i, surface);
                semaphore.release();
            }
        };
        if (i != 0) {
            r1.run();
        } else if (surface == null) {
            this.f55a.mo131b(r1);
        } else {
            this.f55a.mo133c(r1);
        }
        if (surface != null || i != 0) {
            return true;
        }
        try {
            if (semaphore.tryAcquire(4, TimeUnit.SECONDS)) {
                return true;
            }
            C0057g.Log(5, "Timeout while trying detaching primary window.");
            return true;
        } catch (InterruptedException unused) {
            C0057g.Log(5, "UI thread got interrupted while trying to detach the primary window from the Unity Engine.");
            return true;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public SurfaceView m60c() {
        SurfaceView surfaceView = new SurfaceView(this.f72r);
        if (m58b()) {
            surfaceView.getHolder().setFormat(-3);
            surfaceView.setZOrderOnTop(true);
        } else {
            surfaceView.getHolder().setFormat(-1);
        }
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                UnityPlayer.this.m44a(0, surfaceHolder.getSurface());
                UnityPlayer.this.m62d();
            }

            public final void surfaceCreated(SurfaceHolder surfaceHolder) {
                UnityPlayer.this.m44a(0, surfaceHolder.getSurface());
            }

            public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                UnityPlayer.this.m44a(0, (Surface) null);
            }
        });
        surfaceView.setFocusable(true);
        surfaceView.setFocusableInTouchMode(true);
        return surfaceView;
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void m62d() {
        if (C0069n.m176c() && this.f60f.mo233e()) {
            this.f55a.mo135d(new Runnable() {
                public final void run() {
                    UnityPlayer.this.nativeSendSurfaceChangedEvent();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public void m65e() {
        if ((this.f72r instanceof Activity) && !((Activity) this.f72r).isFinishing()) {
            ((Activity) this.f72r).finish();
        }
    }

    /* renamed from: f */
    private void m67f() {
        reportSoftInputStr((String) null, 1, true);
        if (this.f60f.mo235g()) {
            if (C0069n.m176c()) {
                final Semaphore semaphore = new Semaphore(0);
                this.f55a.mo129a(isFinishing() ? new Runnable() {
                    public final void run() {
                        UnityPlayer.this.m68g();
                        semaphore.release();
                    }
                } : new Runnable() {
                    public final void run() {
                        if (UnityPlayer.this.nativePause()) {
                            boolean unused = UnityPlayer.this.f74u = true;
                            UnityPlayer.this.m68g();
                            semaphore.release(2);
                            return;
                        }
                        semaphore.release();
                    }
                });
                try {
                    if (!semaphore.tryAcquire(4, TimeUnit.SECONDS)) {
                        C0057g.Log(5, "Timeout while trying to pause the Unity Engine.");
                    }
                } catch (InterruptedException unused) {
                    C0057g.Log(5, "UI thread got interrupted while trying to pause the Unity Engine.");
                }
                if (semaphore.drainPermits() > 0) {
                    destroy();
                }
            }
            this.f60f.mo231d(false);
            this.f60f.mo229b(true);
            if (this.f63i) {
                this.f65k.listen(this.f64j, 0);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public void m68g() {
        this.f75v = true;
        nativeDone();
        this.f60f.mo230c(false);
    }

    /* renamed from: h */
    private void m70h() {
        if (this.f60f.mo234f()) {
            this.f60f.mo231d(true);
            m57b((Runnable) new Runnable() {
                public final void run() {
                    UnityPlayer.this.nativeResume();
                }
            });
            this.f55a.mo130b();
        }
    }

    /* renamed from: i */
    private static void m72i() {
        if (C0069n.m176c()) {
            if (NativeLoader.unload()) {
                C0069n.m175b();
                return;
            }
            throw new UnsatisfiedLinkError("Unable to unload libraries from libmain.so");
        }
    }

    private final native void initJni(Context context);

    /* renamed from: j */
    private ApplicationInfo m74j() {
        return this.f72r.getPackageManager().getApplicationInfo(this.f72r.getPackageName(), 128);
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public boolean m77k() {
        try {
            return m74j().metaData.getBoolean("unity.splash-enable");
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: l */
    private boolean m78l() {
        try {
            return m74j().metaData.getBoolean("unity.tango-enable");
        } catch (Exception unused) {
            return false;
        }
    }

    protected static boolean loadLibraryStatic(String str) {
        StringBuilder sb;
        try {
            System.loadLibrary(str);
            return true;
        } catch (UnsatisfiedLinkError unused) {
            sb = new StringBuilder("Unable to find ");
            sb.append(str);
            C0057g.Log(6, sb.toString());
            return false;
        } catch (Exception e) {
            sb = new StringBuilder("Unknown error ");
            sb.append(e);
            C0057g.Log(6, sb.toString());
            return false;
        }
    }

    /* renamed from: m */
    private void m80m() {
        if (this.f72r instanceof Activity) {
            ((Activity) this.f72r).getWindow().setFlags(1024, 1024);
        }
    }

    private final native void nativeDone();

    /* access modifiers changed from: private */
    public final native void nativeFocusChanged(boolean z);

    private final native void nativeInitWebRequest(Class cls);

    private final native boolean nativeInjectEvent(InputEvent inputEvent);

    /* access modifiers changed from: private */
    public final native boolean nativeIsAutorotationOn();

    /* access modifiers changed from: private */
    public final native void nativeLowMemory();

    /* access modifiers changed from: private */
    public final native void nativeMuteMasterAudio(boolean z);

    /* access modifiers changed from: private */
    public final native boolean nativePause();

    /* access modifiers changed from: private */
    public final native void nativeRecreateGfxState(int i, Surface surface);

    /* access modifiers changed from: private */
    public final native boolean nativeRender();

    private final native void nativeRestartActivityIndicator();

    /* access modifiers changed from: private */
    public final native void nativeResume();

    /* access modifiers changed from: private */
    public final native void nativeSendSurfaceChangedEvent();

    /* access modifiers changed from: private */
    public final native void nativeSetInputSelection(int i, int i2);

    /* access modifiers changed from: private */
    public final native void nativeSetInputString(String str);

    /* access modifiers changed from: private */
    public final native void nativeSoftInputCanceled();

    /* access modifiers changed from: private */
    public final native void nativeSoftInputClosed();

    private final native void nativeSoftInputLostFocus();

    private static native void nativeUnitySendMessage(String str, String str2, String str3);

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo55a(Runnable runnable) {
        if (this.f72r instanceof Activity) {
            ((Activity) this.f72r).runOnUiThread(runnable);
        } else {
            C0057g.Log(5, "Not running Unity from an Activity; ignored...");
        }
    }

    /* access modifiers changed from: protected */
    public void addPhoneCallListener() {
        this.f63i = true;
        this.f65k.listen(this.f64j, 32);
    }

    public boolean addViewToPlayer(View view, boolean z) {
        m47a(view, (View) z ? this.f73s : null);
        boolean z2 = false;
        boolean z3 = view.getParent() == this;
        boolean z4 = z && this.f73s.getParent() == null;
        boolean z5 = this.f73s.getParent() == this;
        if (z3 && (z4 || z5)) {
            z2 = true;
        }
        if (!z2) {
            if (!z3) {
                C0057g.Log(6, "addViewToPlayer: Failure adding view to hierarchy");
            }
            if (!z4 && !z5) {
                C0057g.Log(6, "addViewToPlayer: Failure removing old view from hierarchy");
            }
        }
        return z2;
    }

    public void configurationChanged(Configuration configuration) {
        if (this.f73s instanceof SurfaceView) {
            this.f73s.getHolder().setSizeFromLayout();
        }
        if (this.f76w != null) {
            this.f76w.mo269c();
        }
        GoogleVrProxy b = GoogleVrApi.m8b();
        if (b != null) {
            b.mo27c();
        }
    }

    public void destroy() {
        if (GoogleVrApi.m8b() != null) {
            GoogleVrApi.m6a();
        }
        if (this.f70p != null) {
            this.f70p.mo4a();
            this.f70p = null;
        }
        if (this.f71q != null) {
            this.f71q.mo47a();
            this.f71q = null;
        }
        this.f74u = true;
        if (!this.f60f.mo232d()) {
            pause();
        }
        this.f55a.mo128a();
        try {
            this.f55a.join(4000);
        } catch (InterruptedException unused) {
            this.f55a.interrupt();
        }
        if (this.f62h != null) {
            this.f72r.unregisterReceiver(this.f62h);
        }
        this.f62h = null;
        if (C0069n.m176c()) {
            removeAllViews();
        }
        if (!this.f75v) {
            kill();
        }
        m72i();
    }

    /* access modifiers changed from: protected */
    public void disableLogger() {
        C0057g.f219a = true;
    }

    public boolean displayChanged(int i, Surface surface) {
        if (i == 0) {
            this.f58d = surface != null;
            mo55a((Runnable) new Runnable() {
                public final void run() {
                    if (UnityPlayer.this.f58d) {
                        UnityPlayer.this.removeView(UnityPlayer.this.f73s);
                    } else {
                        UnityPlayer.this.addView(UnityPlayer.this.f73s);
                    }
                }
            });
        }
        return m59b(i, surface);
    }

    /* access modifiers changed from: protected */
    public void executeGLThreadJobs() {
        while (true) {
            Runnable runnable = (Runnable) this.f61g.poll();
            if (runnable != null) {
                runnable.run();
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public String getClipboardText() {
        ClipData primaryClip = this.f66l.getPrimaryClip();
        return primaryClip != null ? primaryClip.getItemAt(0).coerceToText(this.f72r).toString() : BuildConfig.FLAVOR;
    }

    public Bundle getSettings() {
        return Bundle.EMPTY;
    }

    /* access modifiers changed from: protected */
    public int getSplashMode() {
        try {
            return m74j().metaData.getInt("unity.splash-mode");
        } catch (Exception unused) {
            return 0;
        }
    }

    public View getView() {
        return this;
    }

    /* access modifiers changed from: protected */
    public void hideSoftInput() {
        final C00285 r0 = new Runnable() {
            public final void run() {
                if (UnityPlayer.this.f56b != null) {
                    UnityPlayer.this.f56b.dismiss();
                    UnityPlayer.this.f56b = null;
                }
            }
        };
        if (C0060j.f221b) {
            m48a((C0039f) new C0039f() {
                /* renamed from: a */
                public final void mo104a() {
                    UnityPlayer.this.mo55a(r0);
                }
            });
        } else {
            mo55a((Runnable) r0);
        }
    }

    public void init(int i, boolean z) {
    }

    /* access modifiers changed from: protected */
    public boolean initializeGoogleAr() {
        if (this.f68n != null || currentActivity == null || !m78l()) {
            return false;
        }
        this.f68n = new GoogleARCoreApi();
        this.f68n.initializeARCore(currentActivity);
        if (this.f60f.mo232d()) {
            return false;
        }
        this.f68n.resumeARCore();
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean initializeGoogleVr() {
        final GoogleVrProxy b = GoogleVrApi.m8b();
        if (b == null) {
            GoogleVrApi.m7a(this);
            b = GoogleVrApi.m8b();
            if (b == null) {
                C0057g.Log(6, "Unable to create Google VR subsystem.");
                return false;
            }
        }
        final Semaphore semaphore = new Semaphore(0);
        final C001413 r3 = new Runnable() {
            public final void run() {
                UnityPlayer.this.injectEvent(new KeyEvent(0, 4));
                UnityPlayer.this.injectEvent(new KeyEvent(1, 4));
            }
        };
        mo55a((Runnable) new Runnable() {
            public final void run() {
                if (!b.mo25a(UnityPlayer.currentActivity, UnityPlayer.this.f72r, UnityPlayer.this.m60c(), r3)) {
                    C0057g.Log(6, "Unable to initialize Google VR subsystem.");
                }
                if (UnityPlayer.currentActivity != null) {
                    b.mo23a(UnityPlayer.currentActivity.getIntent());
                }
                semaphore.release();
            }
        });
        try {
            if (semaphore.tryAcquire(4, TimeUnit.SECONDS)) {
                return b.mo24a();
            }
            C0057g.Log(5, "Timeout while trying to initialize Google VR.");
            return false;
        } catch (InterruptedException e) {
            C0057g.Log(5, "UI thread was interrupted while initializing Google VR. " + e.getLocalizedMessage());
            return false;
        }
    }

    public boolean injectEvent(InputEvent inputEvent) {
        if (!C0069n.m176c()) {
            return false;
        }
        return nativeInjectEvent(inputEvent);
    }

    /* access modifiers changed from: protected */
    public boolean isFinishing() {
        if (!this.f74u) {
            boolean z = (this.f72r instanceof Activity) && ((Activity) this.f72r).isFinishing();
            this.f74u = z;
            return z;
        }
    }

    /* access modifiers changed from: protected */
    public void kill() {
        Process.killProcess(Process.myPid());
    }

    /* access modifiers changed from: protected */
    public boolean loadLibrary(String str) {
        return loadLibraryStatic(str);
    }

    public void lowMemory() {
        if (C0069n.m176c()) {
            m57b((Runnable) new Runnable() {
                public final void run() {
                    UnityPlayer.this.nativeLowMemory();
                }
            });
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    public void pause() {
        if (this.f68n != null) {
            this.f68n.pauseARCore();
        }
        if (this.f76w != null) {
            this.f76w.mo266a();
        }
        GoogleVrProxy b = GoogleVrApi.m8b();
        if (b != null) {
            b.pauseGvrLayout();
        }
        m67f();
    }

    public void quit() {
        destroy();
    }

    public void removeViewFromPlayer(View view) {
        m47a((View) this.f73s, view);
        boolean z = false;
        boolean z2 = view.getParent() == null;
        boolean z3 = this.f73s.getParent() == this;
        if (z2 && z3) {
            z = true;
        }
        if (!z) {
            if (!z2) {
                C0057g.Log(6, "removeViewFromPlayer: Failure removing view from hierarchy");
            }
            if (!z3) {
                C0057g.Log(6, "removeVireFromPlayer: Failure agging old view to hierarchy");
            }
        }
    }

    public void reportError(String str, String str2) {
        C0057g.Log(6, str + ": " + str2);
    }

    /* access modifiers changed from: protected */
    public void reportSoftInputSelection(final int i, final int i2) {
        m48a((C0039f) new C0039f() {
            /* renamed from: a */
            public final void mo104a() {
                UnityPlayer.this.nativeSetInputSelection(i, i2);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void reportSoftInputStr(final String str, final int i, final boolean z) {
        if (i == 1) {
            hideSoftInput();
        }
        m48a((C0039f) new C0039f() {
            /* renamed from: a */
            public final void mo104a() {
                if (z) {
                    UnityPlayer.this.nativeSoftInputCanceled();
                } else if (str != null) {
                    UnityPlayer.this.nativeSetInputString(str);
                }
                if (i == 1) {
                    UnityPlayer.this.nativeSoftInputClosed();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void requestUserAuthorization(String str) {
        if (C0060j.f222c && str != null && !str.isEmpty() && currentActivity != null) {
            C0060j.f223d.mo205a(currentActivity, str);
        }
    }

    public void resume() {
        if (this.f68n != null) {
            this.f68n.resumeARCore();
        }
        this.f60f.mo229b(false);
        if (this.f76w != null) {
            this.f76w.mo268b();
        }
        m70h();
        nativeRestartActivityIndicator();
        GoogleVrProxy b = GoogleVrApi.m8b();
        if (b != null) {
            b.mo26b();
        }
    }

    /* access modifiers changed from: protected */
    public void setCharacterLimit(final int i) {
        mo55a((Runnable) new Runnable() {
            public final void run() {
                if (UnityPlayer.this.f56b != null) {
                    UnityPlayer.this.f56b.mo209a(i);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setClipboardText(String str) {
        this.f66l.setPrimaryClip(ClipData.newPlainText("Text", str));
    }

    /* access modifiers changed from: protected */
    public void setHideInputField(final boolean z) {
        mo55a((Runnable) new Runnable() {
            public final void run() {
                if (UnityPlayer.this.f56b != null) {
                    UnityPlayer.this.f56b.mo212a(z);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setSelection(final int i, final int i2) {
        mo55a((Runnable) new Runnable() {
            public final void run() {
                if (UnityPlayer.this.f56b != null) {
                    UnityPlayer.this.f56b.mo210a(i, i2);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setSoftInputStr(final String str) {
        mo55a((Runnable) new Runnable() {
            public final void run() {
                if (UnityPlayer.this.f56b != null && str != null) {
                    UnityPlayer.this.f56b.mo211a(str);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void showSoftInput(String str, int i, boolean z, boolean z2, boolean z3, boolean z4, String str2, int i2, boolean z5) {
        final String str3 = str;
        final int i3 = i;
        final boolean z6 = z;
        final boolean z7 = z2;
        final boolean z8 = z3;
        final boolean z9 = z4;
        final String str4 = str2;
        final int i4 = i2;
        final boolean z10 = z5;
        mo55a((Runnable) new Runnable() {
            public final void run() {
                UnityPlayer.this.f56b = new C0061k(UnityPlayer.this.f72r, this, str3, i3, z6, z7, z8, str4, i4, z10);
                UnityPlayer.this.f56b.show();
            }
        });
    }

    /* access modifiers changed from: protected */
    public boolean showVideoPlayer(String str, int i, int i2, int i3, boolean z, int i4, int i5) {
        if (this.f76w == null) {
            this.f76w = new C0075q(this);
        }
        boolean a = this.f76w.mo267a(this.f72r, str, i, i2, i3, z, (long) i4, (long) i5, new C0075q.C0082a() {
            /* renamed from: a */
            public final void mo107a() {
                C0075q unused = UnityPlayer.this.f76w = null;
            }
        });
        if (a) {
            mo55a((Runnable) new Runnable() {
                public final void run() {
                    if (UnityPlayer.this.nativeIsAutorotationOn() && (UnityPlayer.this.f72r instanceof Activity)) {
                        ((Activity) UnityPlayer.this.f72r).setRequestedOrientation(UnityPlayer.this.f57c);
                    }
                }
            });
        }
        return a;
    }

    /* access modifiers changed from: protected */
    public boolean skipPermissionsDialog() {
        if (!C0060j.f222c || currentActivity == null) {
            return false;
        }
        return C0060j.f223d.mo206a(currentActivity);
    }

    public void start() {
    }

    public void stop() {
    }

    /* access modifiers changed from: protected */
    public void toggleGyroscopeSensor(boolean z) {
        SensorManager sensorManager = (SensorManager) this.f72r.getSystemService("sensor");
        Sensor defaultSensor = sensorManager.getDefaultSensor(11);
        if (z) {
            sensorManager.registerListener(this.f69o, defaultSensor, 1);
        } else {
            sensorManager.unregisterListener(this.f69o);
        }
    }

    public void windowFocusChanged(boolean z) {
        this.f60f.mo228a(z);
        if (this.f60f.mo233e()) {
            if (z && this.f56b != null) {
                nativeSoftInputLostFocus();
                reportSoftInputStr((String) null, 1, false);
            }
            if (z) {
                this.f55a.mo132c();
            } else {
                this.f55a.mo134d();
            }
            m70h();
        }
    }
}
