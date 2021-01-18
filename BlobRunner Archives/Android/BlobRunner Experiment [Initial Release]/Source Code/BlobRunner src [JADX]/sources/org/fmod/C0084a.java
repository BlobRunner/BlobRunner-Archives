package org.fmod;

import android.media.AudioRecord;
import android.util.Log;
import java.nio.ByteBuffer;

/* renamed from: org.fmod.a */
final class C0084a implements Runnable {

    /* renamed from: a */
    private final FMODAudioDevice f316a;

    /* renamed from: b */
    private final ByteBuffer f317b;

    /* renamed from: c */
    private final int f318c;

    /* renamed from: d */
    private final int f319d;

    /* renamed from: e */
    private final int f320e = 2;

    /* renamed from: f */
    private volatile Thread f321f;

    /* renamed from: g */
    private volatile boolean f322g;

    /* renamed from: h */
    private AudioRecord f323h;

    /* renamed from: i */
    private boolean f324i;

    C0084a(FMODAudioDevice fMODAudioDevice, int i, int i2) {
        this.f316a = fMODAudioDevice;
        this.f318c = i;
        this.f319d = i2;
        this.f317b = ByteBuffer.allocateDirect(AudioRecord.getMinBufferSize(i, i2, 2));
    }

    /* renamed from: d */
    private void m214d() {
        if (this.f323h != null) {
            if (this.f323h.getState() == 1) {
                this.f323h.stop();
            }
            this.f323h.release();
            this.f323h = null;
        }
        this.f317b.position(0);
        this.f324i = false;
    }

    /* renamed from: a */
    public final int mo284a() {
        return this.f317b.capacity();
    }

    /* renamed from: b */
    public final void mo285b() {
        if (this.f321f != null) {
            mo286c();
        }
        this.f322g = true;
        this.f321f = new Thread(this);
        this.f321f.start();
    }

    /* renamed from: c */
    public final void mo286c() {
        while (this.f321f != null) {
            this.f322g = false;
            try {
                this.f321f.join();
                this.f321f = null;
            } catch (InterruptedException unused) {
            }
        }
    }

    public final void run() {
        int i = 3;
        while (this.f322g) {
            if (!this.f324i && i > 0) {
                m214d();
                this.f323h = new AudioRecord(1, this.f318c, this.f319d, this.f320e, this.f317b.capacity());
                boolean z = true;
                if (this.f323h.getState() != 1) {
                    z = false;
                }
                this.f324i = z;
                if (this.f324i) {
                    this.f317b.position(0);
                    this.f323h.startRecording();
                    i = 3;
                } else {
                    Log.e("FMOD", "AudioRecord failed to initialize (status " + this.f323h.getState() + ")");
                    i += -1;
                    m214d();
                }
            }
            if (this.f324i && this.f323h.getRecordingState() == 3) {
                this.f316a.fmodProcessMicData(this.f317b, this.f323h.read(this.f317b, this.f317b.capacity()));
                this.f317b.position(0);
            }
        }
        m214d();
    }
}
