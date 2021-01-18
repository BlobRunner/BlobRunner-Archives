package com.unity3d.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

public class HFPStatus {

    /* renamed from: a */
    private Context f33a;

    /* renamed from: b */
    private BroadcastReceiver f34b = null;

    /* renamed from: c */
    private Intent f35c = null;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public boolean f36d = false;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public AudioManager f37e = null;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public int f38f = C0007a.f40a;

    /* renamed from: com.unity3d.player.HFPStatus$a */
    enum C0007a {
        ;

        static {
            f43d = new int[]{f40a, f41b, f42c};
        }
    }

    public HFPStatus(Context context) {
        this.f33a = context;
        this.f37e = (AudioManager) this.f33a.getSystemService("audio");
        initHFPStatusJni();
    }

    private final native void deinitHFPStatusJni();

    private final native void initHFPStatusJni();

    /* renamed from: a */
    public final void mo47a() {
        deinitHFPStatusJni();
    }

    /* access modifiers changed from: protected */
    public boolean getHFPStat() {
        return this.f38f == C0007a.f41b;
    }

    /* access modifiers changed from: protected */
    public void requestHFPStat() {
        this.f34b = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                switch (intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", -1)) {
                    case 0:
                        if (HFPStatus.this.f36d) {
                            HFPStatus.this.f37e.setMode(0);
                        }
                        boolean unused = HFPStatus.this.f36d = false;
                        return;
                    case 1:
                        int unused2 = HFPStatus.this.f38f = C0007a.f41b;
                        if (!HFPStatus.this.f36d) {
                            HFPStatus.this.f37e.stopBluetoothSco();
                            return;
                        } else {
                            HFPStatus.this.f37e.setMode(3);
                            return;
                        }
                    case 2:
                        if (HFPStatus.this.f38f == C0007a.f41b) {
                            boolean unused3 = HFPStatus.this.f36d = true;
                            return;
                        } else {
                            int unused4 = HFPStatus.this.f38f = C0007a.f42c;
                            return;
                        }
                    default:
                        return;
                }
            }
        };
        this.f35c = this.f33a.registerReceiver(this.f34b, new IntentFilter("android.media.ACTION_SCO_AUDIO_STATE_UPDATED"));
        try {
            this.f37e.startBluetoothSco();
        } catch (NullPointerException unused) {
            C0057g.Log(5, "startBluetoothSco() failed. no bluetooth device connected.");
        }
    }
}
