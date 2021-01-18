package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import edu.wit.BlobRunner.BuildConfig;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* renamed from: com.unity3d.player.c */
class C0052c {

    /* renamed from: a */
    protected C0070o f211a = null;

    /* renamed from: b */
    protected C0056f f212b = null;

    /* renamed from: c */
    protected Context f213c = null;

    /* renamed from: d */
    protected String f214d = null;

    /* renamed from: e */
    protected String f215e = BuildConfig.FLAVOR;

    C0052c(String str, C0056f fVar) {
        this.f215e = str;
        this.f212b = fVar;
    }

    /* access modifiers changed from: protected */
    public void reportError(String str) {
        if (this.f212b != null) {
            C0056f fVar = this.f212b;
            fVar.reportError(this.f215e + " Error [" + this.f214d + "]", str);
            return;
        }
        C0057g.Log(6, this.f215e + " Error [" + this.f214d + "]: " + str);
    }

    /* access modifiers changed from: protected */
    public void runOnUiThread(Runnable runnable) {
        if (this.f213c instanceof Activity) {
            ((Activity) this.f213c).runOnUiThread(runnable);
            return;
        }
        C0057g.Log(5, "Not running " + this.f215e + " from an Activity; Ignoring execution request...");
    }

    /* access modifiers changed from: protected */
    public boolean runOnUiThreadWithSync(final Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
            return true;
        }
        final Semaphore semaphore = new Semaphore(0);
        runOnUiThread(new Runnable() {
            public final void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    C0052c cVar = C0052c.this;
                    cVar.reportError("Exception unloading Google VR on UI Thread. " + e.getLocalizedMessage());
                } catch (Throwable th) {
                    semaphore.release();
                    throw th;
                }
                semaphore.release();
            }
        });
        try {
            if (semaphore.tryAcquire(4, TimeUnit.SECONDS)) {
                return true;
            }
            reportError("Timeout waiting for vr state change!");
            return false;
        } catch (InterruptedException e) {
            reportError("Interrupted while trying to acquire sync lock. " + e.getLocalizedMessage());
            return false;
        }
    }
}
