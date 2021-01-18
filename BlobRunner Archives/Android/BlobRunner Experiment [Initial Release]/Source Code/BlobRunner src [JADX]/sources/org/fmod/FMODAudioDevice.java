package org.fmod;

import android.media.AudioTrack;
import android.util.Log;
import java.nio.ByteBuffer;

public class FMODAudioDevice implements Runnable {

    /* renamed from: h */
    private static int f305h = 0;

    /* renamed from: i */
    private static int f306i = 1;

    /* renamed from: j */
    private static int f307j = 2;

    /* renamed from: k */
    private static int f308k = 3;

    /* renamed from: a */
    private volatile Thread f309a = null;

    /* renamed from: b */
    private volatile boolean f310b = false;

    /* renamed from: c */
    private AudioTrack f311c = null;

    /* renamed from: d */
    private boolean f312d = false;

    /* renamed from: e */
    private ByteBuffer f313e = null;

    /* renamed from: f */
    private byte[] f314f = null;

    /* renamed from: g */
    private volatile C0084a f315g;

    private native int fmodGetInfo(int i);

    private native int fmodProcess(ByteBuffer byteBuffer);

    private void releaseAudioTrack() {
        if (this.f311c != null) {
            if (this.f311c.getState() == 1) {
                this.f311c.stop();
            }
            this.f311c.release();
            this.f311c = null;
        }
        this.f313e = null;
        this.f314f = null;
        this.f312d = false;
    }

    public synchronized void close() {
        stop();
    }

    /* access modifiers changed from: package-private */
    public native int fmodProcessMicData(ByteBuffer byteBuffer, int i);

    public boolean isRunning() {
        return this.f309a != null && this.f309a.isAlive();
    }

    public void run() {
        int i = 3;
        while (this.f310b) {
            if (!this.f312d && i > 0) {
                releaseAudioTrack();
                int fmodGetInfo = fmodGetInfo(f305h);
                int round = Math.round(((float) AudioTrack.getMinBufferSize(fmodGetInfo, 3, 2)) * 1.1f) & -4;
                int fmodGetInfo2 = fmodGetInfo(f306i);
                int fmodGetInfo3 = fmodGetInfo(f307j) * fmodGetInfo2 * 4;
                this.f311c = new AudioTrack(3, fmodGetInfo, 3, 2, fmodGetInfo3 > round ? fmodGetInfo3 : round, 1);
                this.f312d = this.f311c.getState() == 1;
                if (this.f312d) {
                    this.f313e = ByteBuffer.allocateDirect(fmodGetInfo2 * 2 * 2);
                    this.f314f = new byte[this.f313e.capacity()];
                    this.f311c.play();
                    i = 3;
                } else {
                    Log.e("FMOD", "AudioTrack failed to initialize (status " + this.f311c.getState() + ")");
                    releaseAudioTrack();
                    i += -1;
                }
            }
            if (this.f312d) {
                if (fmodGetInfo(f308k) == 1) {
                    fmodProcess(this.f313e);
                    this.f313e.get(this.f314f, 0, this.f313e.capacity());
                    this.f311c.write(this.f314f, 0, this.f313e.capacity());
                    this.f313e.position(0);
                } else {
                    releaseAudioTrack();
                }
            }
        }
        releaseAudioTrack();
    }

    public synchronized void start() {
        if (this.f309a != null) {
            stop();
        }
        this.f309a = new Thread(this, "FMODAudioDevice");
        this.f309a.setPriority(10);
        this.f310b = true;
        this.f309a.start();
        if (this.f315g != null) {
            this.f315g.mo285b();
        }
    }

    public synchronized int startAudioRecord(int i, int i2, int i3) {
        if (this.f315g == null) {
            this.f315g = new C0084a(this, i, i2);
            this.f315g.mo285b();
        }
        return this.f315g.mo284a();
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:1:0x0001 */
    /* JADX WARNING: Removed duplicated region for block: B:1:0x0001 A[LOOP:0: B:1:0x0001->B:16:0x0001, LOOP_START, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void stop() {
        /*
            r1 = this;
            monitor-enter(r1)
        L_0x0001:
            java.lang.Thread r0 = r1.f309a     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x0011
            r0 = 0
            r1.f310b = r0     // Catch:{ all -> 0x001c }
            java.lang.Thread r0 = r1.f309a     // Catch:{ InterruptedException -> 0x0001 }
            r0.join()     // Catch:{ InterruptedException -> 0x0001 }
            r0 = 0
            r1.f309a = r0     // Catch:{ InterruptedException -> 0x0001 }
            goto L_0x0001
        L_0x0011:
            org.fmod.a r0 = r1.f315g     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x001a
            org.fmod.a r0 = r1.f315g     // Catch:{ all -> 0x001c }
            r0.mo286c()     // Catch:{ all -> 0x001c }
        L_0x001a:
            monitor-exit(r1)
            return
        L_0x001c:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.fmod.FMODAudioDevice.stop():void");
    }

    public synchronized void stopAudioRecord() {
        if (this.f315g != null) {
            this.f315g.mo286c();
            this.f315g = null;
        }
    }
}
