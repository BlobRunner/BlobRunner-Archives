package com.unity3d.player;

import java.lang.reflect.Method;
import java.util.HashMap;

/* renamed from: com.unity3d.player.o */
final class C0070o {

    /* renamed from: a */
    private HashMap f248a = new HashMap();

    /* renamed from: b */
    private Class f249b = null;

    /* renamed from: c */
    private Object f250c = null;

    /* renamed from: com.unity3d.player.o$a */
    class C0071a {

        /* renamed from: a */
        public Class[] f251a;

        /* renamed from: b */
        public Method f252b = null;

        public C0071a(Class[] clsArr) {
            this.f251a = clsArr;
        }
    }

    public C0070o(Class cls, Object obj) {
        this.f249b = cls;
        this.f250c = obj;
    }

    /* renamed from: a */
    private void m185a(String str, C0071a aVar) {
        try {
            aVar.f252b = this.f249b.getMethod(str, aVar.f251a);
        } catch (Exception e) {
            C0057g.Log(6, "Exception while trying to get method " + str + ". " + e.getLocalizedMessage());
            aVar.f252b = null;
        }
    }

    /* renamed from: a */
    public final Object mo237a(String str, Object... objArr) {
        StringBuilder sb;
        if (!this.f248a.containsKey(str)) {
            sb = new StringBuilder("No definition for method ");
            sb.append(str);
            str = " can be found";
        } else {
            C0071a aVar = (C0071a) this.f248a.get(str);
            if (aVar.f252b == null) {
                m185a(str, aVar);
            }
            if (aVar.f252b == null) {
                sb = new StringBuilder("Unable to create method: ");
            } else {
                try {
                    return objArr.length == 0 ? aVar.f252b.invoke(this.f250c, new Object[0]) : aVar.f252b.invoke(this.f250c, objArr);
                } catch (Exception e) {
                    C0057g.Log(6, "Error trying to call delegated method " + str + ". " + e.getLocalizedMessage());
                    return null;
                }
            }
        }
        sb.append(str);
        C0057g.Log(6, sb.toString());
        return null;
    }

    /* renamed from: a */
    public final void mo238a(String str, Class[] clsArr) {
        this.f248a.put(str, new C0071a(clsArr));
    }
}
