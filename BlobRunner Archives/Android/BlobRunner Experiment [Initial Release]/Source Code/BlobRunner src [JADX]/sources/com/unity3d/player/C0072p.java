package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;

/* renamed from: com.unity3d.player.p */
public final class C0072p extends FrameLayout implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static boolean f254a = false;

    /* renamed from: b */
    private final Context f255b;

    /* renamed from: c */
    private final SurfaceView f256c;

    /* renamed from: d */
    private final SurfaceHolder f257d;

    /* renamed from: e */
    private final String f258e;

    /* renamed from: f */
    private final int f259f;

    /* renamed from: g */
    private final int f260g;

    /* renamed from: h */
    private final boolean f261h;

    /* renamed from: i */
    private final long f262i;

    /* renamed from: j */
    private final long f263j;

    /* renamed from: k */
    private final FrameLayout f264k;

    /* renamed from: l */
    private final Display f265l;

    /* renamed from: m */
    private int f266m;

    /* renamed from: n */
    private int f267n;

    /* renamed from: o */
    private int f268o;

    /* renamed from: p */
    private int f269p;

    /* renamed from: q */
    private MediaPlayer f270q;

    /* renamed from: r */
    private MediaController f271r;

    /* renamed from: s */
    private boolean f272s = false;

    /* renamed from: t */
    private boolean f273t = false;

    /* renamed from: u */
    private int f274u = 0;

    /* renamed from: v */
    private boolean f275v = false;

    /* renamed from: w */
    private boolean f276w = false;

    /* renamed from: x */
    private C0073a f277x;

    /* renamed from: y */
    private C0074b f278y;

    /* renamed from: z */
    private volatile int f279z = 0;

    /* renamed from: com.unity3d.player.p$a */
    public interface C0073a {
        /* renamed from: a */
        void mo263a(int i);
    }

    /* renamed from: com.unity3d.player.p$b */
    public class C0074b implements Runnable {

        /* renamed from: b */
        private C0072p f281b;

        /* renamed from: c */
        private boolean f282c = false;

        public C0074b(C0072p pVar) {
            this.f281b = pVar;
        }

        /* renamed from: a */
        public final void mo264a() {
            this.f282c = true;
        }

        public final void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
            if (!this.f282c) {
                if (C0072p.f254a) {
                    C0072p.m190b("Stopping the video player due to timeout.");
                }
                this.f281b.CancelOnPrepare();
            }
        }
    }

    protected C0072p(Context context, String str, int i, int i2, int i3, boolean z, long j, long j2, C0073a aVar) {
        super(context);
        this.f277x = aVar;
        this.f255b = context;
        this.f264k = this;
        this.f256c = new SurfaceView(context);
        this.f257d = this.f256c.getHolder();
        this.f257d.addCallback(this);
        this.f264k.setBackgroundColor(i);
        this.f264k.addView(this.f256c);
        this.f265l = ((WindowManager) this.f255b.getSystemService("window")).getDefaultDisplay();
        this.f258e = str;
        this.f259f = i2;
        this.f260g = i3;
        this.f261h = z;
        this.f262i = j;
        this.f263j = j2;
        if (f254a) {
            m190b("fileName: " + this.f258e);
        }
        if (f254a) {
            m190b("backgroundColor: " + i);
        }
        if (f254a) {
            m190b("controlMode: " + this.f259f);
        }
        if (f254a) {
            m190b("scalingMode: " + this.f260g);
        }
        if (f254a) {
            m190b("isURL: " + this.f261h);
        }
        if (f254a) {
            m190b("videoOffset: " + this.f262i);
        }
        if (f254a) {
            m190b("videoLength: " + this.f263j);
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    /* renamed from: a */
    private void m188a(int i) {
        this.f279z = i;
        if (this.f277x != null) {
            this.f277x.mo263a(this.f279z);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static void m190b(String str) {
        Log.i("Video", "VideoPlayer: " + str);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:17|18|19|20|21) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0081 */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m192c() {
        /*
            r8 = this;
            android.media.MediaPlayer r0 = r8.f270q
            if (r0 == 0) goto L_0x001e
            android.media.MediaPlayer r0 = r8.f270q
            android.view.SurfaceHolder r1 = r8.f257d
            r0.setDisplay(r1)
            boolean r0 = r8.f275v
            if (r0 != 0) goto L_0x001d
            boolean r0 = f254a
            if (r0 == 0) goto L_0x0018
            java.lang.String r0 = "Resuming playback"
            m190b(r0)
        L_0x0018:
            android.media.MediaPlayer r0 = r8.f270q
            r0.start()
        L_0x001d:
            return
        L_0x001e:
            r0 = 0
            r8.m188a((int) r0)
            r8.doCleanUp()
            android.media.MediaPlayer r0 = new android.media.MediaPlayer     // Catch:{ Exception -> 0x00d0 }
            r0.<init>()     // Catch:{ Exception -> 0x00d0 }
            r8.f270q = r0     // Catch:{ Exception -> 0x00d0 }
            boolean r0 = r8.f261h     // Catch:{ Exception -> 0x00d0 }
            if (r0 == 0) goto L_0x003e
            android.media.MediaPlayer r0 = r8.f270q     // Catch:{ Exception -> 0x00d0 }
            android.content.Context r1 = r8.f255b     // Catch:{ Exception -> 0x00d0 }
            java.lang.String r2 = r8.f258e     // Catch:{ Exception -> 0x00d0 }
            android.net.Uri r2 = android.net.Uri.parse(r2)     // Catch:{ Exception -> 0x00d0 }
            r0.setDataSource(r1, r2)     // Catch:{ Exception -> 0x00d0 }
            goto L_0x0092
        L_0x003e:
            long r0 = r8.f263j     // Catch:{ Exception -> 0x00d0 }
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x005e
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00d0 }
            java.lang.String r1 = r8.f258e     // Catch:{ Exception -> 0x00d0 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00d0 }
            android.media.MediaPlayer r2 = r8.f270q     // Catch:{ Exception -> 0x00d0 }
            java.io.FileDescriptor r3 = r0.getFD()     // Catch:{ Exception -> 0x00d0 }
            long r4 = r8.f262i     // Catch:{ Exception -> 0x00d0 }
            long r6 = r8.f263j     // Catch:{ Exception -> 0x00d0 }
            r2.setDataSource(r3, r4, r6)     // Catch:{ Exception -> 0x00d0 }
        L_0x005a:
            r0.close()     // Catch:{ Exception -> 0x00d0 }
            goto L_0x0092
        L_0x005e:
            android.content.res.Resources r0 = r8.getResources()     // Catch:{ Exception -> 0x00d0 }
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch:{ Exception -> 0x00d0 }
            java.lang.String r1 = r8.f258e     // Catch:{ IOException -> 0x0081 }
            android.content.res.AssetFileDescriptor r0 = r0.openFd(r1)     // Catch:{ IOException -> 0x0081 }
            android.media.MediaPlayer r1 = r8.f270q     // Catch:{ IOException -> 0x0081 }
            java.io.FileDescriptor r2 = r0.getFileDescriptor()     // Catch:{ IOException -> 0x0081 }
            long r3 = r0.getStartOffset()     // Catch:{ IOException -> 0x0081 }
            long r5 = r0.getLength()     // Catch:{ IOException -> 0x0081 }
            r1.setDataSource(r2, r3, r5)     // Catch:{ IOException -> 0x0081 }
            r0.close()     // Catch:{ IOException -> 0x0081 }
            goto L_0x0092
        L_0x0081:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00d0 }
            java.lang.String r1 = r8.f258e     // Catch:{ Exception -> 0x00d0 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00d0 }
            android.media.MediaPlayer r1 = r8.f270q     // Catch:{ Exception -> 0x00d0 }
            java.io.FileDescriptor r2 = r0.getFD()     // Catch:{ Exception -> 0x00d0 }
            r1.setDataSource(r2)     // Catch:{ Exception -> 0x00d0 }
            goto L_0x005a
        L_0x0092:
            android.media.MediaPlayer r0 = r8.f270q     // Catch:{ Exception -> 0x00d0 }
            android.view.SurfaceHolder r1 = r8.f257d     // Catch:{ Exception -> 0x00d0 }
            r0.setDisplay(r1)     // Catch:{ Exception -> 0x00d0 }
            android.media.MediaPlayer r0 = r8.f270q     // Catch:{ Exception -> 0x00d0 }
            r1 = 1
            r0.setScreenOnWhilePlaying(r1)     // Catch:{ Exception -> 0x00d0 }
            android.media.MediaPlayer r0 = r8.f270q     // Catch:{ Exception -> 0x00d0 }
            r0.setOnBufferingUpdateListener(r8)     // Catch:{ Exception -> 0x00d0 }
            android.media.MediaPlayer r0 = r8.f270q     // Catch:{ Exception -> 0x00d0 }
            r0.setOnCompletionListener(r8)     // Catch:{ Exception -> 0x00d0 }
            android.media.MediaPlayer r0 = r8.f270q     // Catch:{ Exception -> 0x00d0 }
            r0.setOnPreparedListener(r8)     // Catch:{ Exception -> 0x00d0 }
            android.media.MediaPlayer r0 = r8.f270q     // Catch:{ Exception -> 0x00d0 }
            r0.setOnVideoSizeChangedListener(r8)     // Catch:{ Exception -> 0x00d0 }
            android.media.MediaPlayer r0 = r8.f270q     // Catch:{ Exception -> 0x00d0 }
            r1 = 3
            r0.setAudioStreamType(r1)     // Catch:{ Exception -> 0x00d0 }
            android.media.MediaPlayer r0 = r8.f270q     // Catch:{ Exception -> 0x00d0 }
            r0.prepareAsync()     // Catch:{ Exception -> 0x00d0 }
            com.unity3d.player.p$b r0 = new com.unity3d.player.p$b     // Catch:{ Exception -> 0x00d0 }
            r0.<init>(r8)     // Catch:{ Exception -> 0x00d0 }
            r8.f278y = r0     // Catch:{ Exception -> 0x00d0 }
            java.lang.Thread r0 = new java.lang.Thread     // Catch:{ Exception -> 0x00d0 }
            com.unity3d.player.p$b r1 = r8.f278y     // Catch:{ Exception -> 0x00d0 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00d0 }
            r0.start()     // Catch:{ Exception -> 0x00d0 }
            return
        L_0x00d0:
            r0 = move-exception
            boolean r1 = f254a
            if (r1 == 0) goto L_0x00ed
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "error: "
            r1.<init>(r2)
            java.lang.String r2 = r0.getMessage()
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            m190b(r0)
        L_0x00ed:
            r0 = 2
            r8.m188a((int) r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.player.C0072p.m192c():void");
    }

    /* renamed from: d */
    private void m193d() {
        if (!isPlaying()) {
            m188a(1);
            if (f254a) {
                m190b("startVideoPlayback");
            }
            updateVideoLayout();
            if (!this.f275v) {
                start();
            }
        }
    }

    public final void CancelOnPrepare() {
        m188a(2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final boolean mo240a() {
        return this.f275v;
    }

    public final boolean canPause() {
        return true;
    }

    public final boolean canSeekBackward() {
        return true;
    }

    public final boolean canSeekForward() {
        return true;
    }

    /* access modifiers changed from: protected */
    public final void destroyPlayer() {
        if (f254a) {
            m190b("destroyPlayer");
        }
        if (!this.f275v) {
            pause();
        }
        doCleanUp();
    }

    /* access modifiers changed from: protected */
    public final void doCleanUp() {
        if (this.f278y != null) {
            this.f278y.mo264a();
            this.f278y = null;
        }
        if (this.f270q != null) {
            this.f270q.release();
            this.f270q = null;
        }
        this.f268o = 0;
        this.f269p = 0;
        this.f273t = false;
        this.f272s = false;
    }

    public final int getBufferPercentage() {
        if (this.f261h) {
            return this.f274u;
        }
        return 100;
    }

    public final int getCurrentPosition() {
        if (this.f270q == null) {
            return 0;
        }
        return this.f270q.getCurrentPosition();
    }

    public final int getDuration() {
        if (this.f270q == null) {
            return 0;
        }
        return this.f270q.getDuration();
    }

    public final boolean isPlaying() {
        boolean z = this.f273t && this.f272s;
        return this.f270q == null ? !z : this.f270q.isPlaying() || !z;
    }

    public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        if (f254a) {
            m190b("onBufferingUpdate percent:" + i);
        }
        this.f274u = i;
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        if (f254a) {
            m190b("onCompletion called");
        }
        destroyPlayer();
        m188a(3);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 && (this.f259f != 2 || i == 0 || keyEvent.isSystem())) {
            return this.f271r != null ? this.f271r.onKeyDown(i, keyEvent) : super.onKeyDown(i, keyEvent);
        }
        destroyPlayer();
        m188a(3);
        return true;
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        if (f254a) {
            m190b("onPrepared called");
        }
        if (this.f278y != null) {
            this.f278y.mo264a();
            this.f278y = null;
        }
        if (this.f259f == 0 || this.f259f == 1) {
            this.f271r = new MediaController(this.f255b);
            this.f271r.setMediaPlayer(this);
            this.f271r.setAnchorView(this);
            this.f271r.setEnabled(true);
            if (this.f255b instanceof Activity) {
                this.f271r.setSystemUiVisibility(((Activity) this.f255b).getWindow().getDecorView().getSystemUiVisibility());
            }
            this.f271r.show();
        }
        this.f273t = true;
        if (this.f273t && this.f272s) {
            m193d();
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (this.f259f != 2 || action != 0) {
            return this.f271r != null ? this.f271r.onTouchEvent(motionEvent) : super.onTouchEvent(motionEvent);
        }
        destroyPlayer();
        m188a(3);
        return true;
    }

    public final void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        if (f254a) {
            m190b("onVideoSizeChanged called " + i + "x" + i2);
        }
        if (i != 0 && i2 != 0) {
            this.f272s = true;
            this.f268o = i;
            this.f269p = i2;
            if (this.f273t && this.f272s) {
                m193d();
            }
        } else if (f254a) {
            m190b("invalid video width(" + i + ") or height(" + i2 + ")");
        }
    }

    public final void pause() {
        if (this.f270q != null) {
            if (this.f276w) {
                this.f270q.pause();
            }
            this.f275v = true;
        }
    }

    public final void seekTo(int i) {
        if (this.f270q != null) {
            this.f270q.seekTo(i);
        }
    }

    public final void start() {
        if (f254a) {
            m190b("Start");
        }
        if (this.f270q != null) {
            if (this.f276w) {
                this.f270q.start();
            }
            this.f275v = false;
        }
    }

    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (f254a) {
            m190b("surfaceChanged called " + i + " " + i2 + "x" + i3);
        }
        if (this.f266m != i2 || this.f267n != i3) {
            this.f266m = i2;
            this.f267n = i3;
            if (this.f276w) {
                updateVideoLayout();
            }
        }
    }

    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (f254a) {
            m190b("surfaceCreated called");
        }
        this.f276w = true;
        m192c();
    }

    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (f254a) {
            m190b("surfaceDestroyed called");
        }
        this.f276w = false;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0051, code lost:
        if (r3 <= r2) goto L_0x0053;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0059, code lost:
        r0 = (int) (((float) r6.f267n) * r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0067, code lost:
        if (r3 >= r2) goto L_0x0053;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateVideoLayout() {
        /*
            r6 = this;
            boolean r0 = f254a
            if (r0 == 0) goto L_0x0009
            java.lang.String r0 = "updateVideoLayout"
            m190b(r0)
        L_0x0009:
            android.media.MediaPlayer r0 = r6.f270q
            if (r0 != 0) goto L_0x000e
            return
        L_0x000e:
            int r0 = r6.f266m
            if (r0 == 0) goto L_0x0016
            int r0 = r6.f267n
            if (r0 != 0) goto L_0x0034
        L_0x0016:
            android.content.Context r0 = r6.f255b
            java.lang.String r1 = "window"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.WindowManager r0 = (android.view.WindowManager) r0
            android.util.DisplayMetrics r1 = new android.util.DisplayMetrics
            r1.<init>()
            android.view.Display r0 = r0.getDefaultDisplay()
            r0.getMetrics(r1)
            int r0 = r1.widthPixels
            r6.f266m = r0
            int r0 = r1.heightPixels
            r6.f267n = r0
        L_0x0034:
            int r0 = r6.f266m
            int r1 = r6.f267n
            boolean r2 = r6.f272s
            if (r2 == 0) goto L_0x0073
            int r2 = r6.f268o
            float r2 = (float) r2
            int r3 = r6.f269p
            float r3 = (float) r3
            float r2 = r2 / r3
            int r3 = r6.f266m
            float r3 = (float) r3
            int r4 = r6.f267n
            float r4 = (float) r4
            float r3 = r3 / r4
            int r4 = r6.f260g
            r5 = 1
            if (r4 != r5) goto L_0x0060
            int r3 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r3 > 0) goto L_0x0059
        L_0x0053:
            int r1 = r6.f266m
            float r1 = (float) r1
            float r1 = r1 / r2
            int r1 = (int) r1
            goto L_0x007c
        L_0x0059:
            int r0 = r6.f267n
            float r0 = (float) r0
            float r0 = r0 * r2
            int r0 = (int) r0
            goto L_0x007c
        L_0x0060:
            int r4 = r6.f260g
            r5 = 2
            if (r4 != r5) goto L_0x006a
            int r3 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r3 < 0) goto L_0x0059
            goto L_0x0053
        L_0x006a:
            int r2 = r6.f260g
            if (r2 != 0) goto L_0x007c
            int r0 = r6.f268o
            int r1 = r6.f269p
            goto L_0x007c
        L_0x0073:
            boolean r2 = f254a
            if (r2 == 0) goto L_0x007c
            java.lang.String r2 = "updateVideoLayout: Video size is not known yet"
            m190b(r2)
        L_0x007c:
            int r2 = r6.f266m
            if (r2 != r0) goto L_0x0084
            int r2 = r6.f267n
            if (r2 == r1) goto L_0x00af
        L_0x0084:
            boolean r2 = f254a
            if (r2 == 0) goto L_0x00a1
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "frameWidth = "
            r2.<init>(r3)
            r2.append(r0)
            java.lang.String r3 = "; frameHeight = "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            m190b(r2)
        L_0x00a1:
            android.widget.FrameLayout$LayoutParams r2 = new android.widget.FrameLayout$LayoutParams
            r3 = 17
            r2.<init>(r0, r1, r3)
            android.widget.FrameLayout r0 = r6.f264k
            android.view.SurfaceView r1 = r6.f256c
            r0.updateViewLayout(r1, r2)
        L_0x00af:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.player.C0072p.updateVideoLayout():void");
    }
}
