package com.unity3d.player;

/* renamed from: com.unity3d.player.n */
final class C0069n {

    /* renamed from: a */
    private static boolean f243a = false;

    /* renamed from: b */
    private boolean f244b = false;

    /* renamed from: c */
    private boolean f245c = false;

    /* renamed from: d */
    private boolean f246d = true;

    /* renamed from: e */
    private boolean f247e = false;

    C0069n() {
    }

    /* renamed from: a */
    static void m174a() {
        f243a = true;
    }

    /* renamed from: b */
    static void m175b() {
        f243a = false;
    }

    /* renamed from: c */
    static boolean m176c() {
        return f243a;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo228a(boolean z) {
        this.f244b = z;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo229b(boolean z) {
        this.f246d = z;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final void mo230c(boolean z) {
        this.f247e = z;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final void mo231d(boolean z) {
        this.f245c = z;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final boolean mo232d() {
        return this.f246d;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final boolean mo233e() {
        return this.f247e;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final boolean mo234f() {
        return f243a && this.f244b && !this.f246d && !this.f245c;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: g */
    public final boolean mo235g() {
        return this.f245c;
    }

    public final String toString() {
        return super.toString();
    }
}
