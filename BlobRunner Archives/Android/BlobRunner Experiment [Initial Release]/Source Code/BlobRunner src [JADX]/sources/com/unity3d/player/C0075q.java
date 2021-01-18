package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import com.unity3d.player.C0072p;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.unity3d.player.q */
final class C0075q {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public UnityPlayer f283a = null;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public Context f284b = null;

    /* renamed from: c */
    private C0082a f285c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public final Semaphore f286d = new Semaphore(0);
    /* access modifiers changed from: private */

    /* renamed from: e */
    public final Lock f287e = new ReentrantLock();
    /* access modifiers changed from: private */

    /* renamed from: f */
    public C0072p f288f = null;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public int f289g = 2;

    /* renamed from: h */
    private boolean f290h = false;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public boolean f291i = false;

    /* renamed from: com.unity3d.player.q$a */
    public interface C0082a {
        /* renamed from: a */
        void mo107a();
    }

    C0075q(UnityPlayer unityPlayer) {
        this.f283a = unityPlayer;
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void m203d() {
        if (this.f288f != null) {
            this.f283a.removeViewFromPlayer(this.f288f);
            this.f291i = false;
            this.f288f.destroyPlayer();
            this.f288f = null;
            if (this.f285c != null) {
                this.f285c.mo107a();
            }
        }
    }

    /* renamed from: a */
    public final void mo266a() {
        this.f287e.lock();
        if (this.f288f != null) {
            if (this.f289g == 0) {
                this.f288f.CancelOnPrepare();
            } else if (this.f291i) {
                this.f290h = this.f288f.mo240a();
                if (!this.f290h) {
                    this.f288f.pause();
                }
            }
        }
        this.f287e.unlock();
    }

    /* renamed from: a */
    public final boolean mo267a(Context context, String str, int i, int i2, int i3, boolean z, long j, long j2, C0082a aVar) {
        this.f287e.lock();
        this.f285c = aVar;
        this.f284b = context;
        this.f286d.drainPermits();
        this.f289g = 2;
        final String str2 = str;
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        final boolean z2 = z;
        final long j3 = j;
        final long j4 = j2;
        runOnUiThread(new Runnable() {
            public final void run() {
                if (C0075q.this.f288f != null) {
                    C0057g.Log(5, "Video already playing");
                    int unused = C0075q.this.f289g = 2;
                    C0075q.this.f286d.release();
                    return;
                }
                C0072p unused2 = C0075q.this.f288f = new C0072p(C0075q.this.f284b, str2, i4, i5, i6, z2, j3, j4, new C0072p.C0073a() {
                    /* renamed from: a */
                    public final void mo263a(int i) {
                        C0075q.this.f287e.lock();
                        int unused = C0075q.this.f289g = i;
                        if (i == 3 && C0075q.this.f291i) {
                            C0075q.this.runOnUiThread(new Runnable() {
                                public final void run() {
                                    C0075q.this.m203d();
                                    C0075q.this.f283a.resume();
                                }
                            });
                        }
                        if (i != 0) {
                            C0075q.this.f286d.release();
                        }
                        C0075q.this.f287e.unlock();
                    }
                });
                if (C0075q.this.f288f != null) {
                    C0075q.this.f283a.addView(C0075q.this.f288f);
                }
            }
        });
        boolean z3 = false;
        try {
            this.f287e.unlock();
            this.f286d.acquire();
            this.f287e.lock();
            if (this.f289g != 2) {
                z3 = true;
            }
        } catch (InterruptedException unused) {
        }
        runOnUiThread(new Runnable() {
            public final void run() {
                C0075q.this.f283a.pause();
            }
        });
        runOnUiThread((!z3 || this.f289g == 3) ? new Runnable() {
            public final void run() {
                C0075q.this.m203d();
                C0075q.this.f283a.resume();
            }
        } : new Runnable() {
            public final void run() {
                if (C0075q.this.f288f != null) {
                    C0075q.this.f283a.addViewToPlayer(C0075q.this.f288f, true);
                    boolean unused = C0075q.this.f291i = true;
                    C0075q.this.f288f.requestFocus();
                }
            }
        });
        this.f287e.unlock();
        return z3;
    }

    /* renamed from: b */
    public final void mo268b() {
        this.f287e.lock();
        if (this.f288f != null && this.f291i && !this.f290h) {
            this.f288f.start();
        }
        this.f287e.unlock();
    }

    /* renamed from: c */
    public final void mo269c() {
        this.f287e.lock();
        if (this.f288f != null) {
            this.f288f.updateVideoLayout();
        }
        this.f287e.unlock();
    }

    /* access modifiers changed from: protected */
    public final void runOnUiThread(Runnable runnable) {
        if (this.f284b instanceof Activity) {
            ((Activity) this.f284b).runOnUiThread(runnable);
        } else {
            C0057g.Log(5, "Not running from an Activity; Ignoring execution request...");
        }
    }
}
