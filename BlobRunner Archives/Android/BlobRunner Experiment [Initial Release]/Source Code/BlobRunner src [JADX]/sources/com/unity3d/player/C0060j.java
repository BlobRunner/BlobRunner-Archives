package com.unity3d.player;

import android.os.Build;

/* renamed from: com.unity3d.player.j */
public final class C0060j {

    /* renamed from: a */
    static final boolean f220a = (Build.VERSION.SDK_INT >= 19);

    /* renamed from: b */
    static final boolean f221b = (Build.VERSION.SDK_INT >= 21);

    /* renamed from: c */
    static final boolean f222c;

    /* renamed from: d */
    static final C0055e f223d;

    static {
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 23) {
            z = true;
        }
        f222c = z;
        f223d = z ? new C0058h() : null;
    }
}
