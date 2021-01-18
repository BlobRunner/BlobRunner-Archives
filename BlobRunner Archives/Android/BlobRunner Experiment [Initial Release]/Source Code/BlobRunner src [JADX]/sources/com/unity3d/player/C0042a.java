package com.unity3d.player;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* renamed from: com.unity3d.player.a */
public final class C0042a {

    /* renamed from: b */
    private static CameraManager f164b;

    /* renamed from: c */
    private static String[] f165c;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public static Semaphore f166e = new Semaphore(1);

    /* renamed from: A */
    private CameraCaptureSession.CaptureCallback f167A = new CameraCaptureSession.CaptureCallback() {
        public final void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
            C0042a.this.m118a(captureRequest.getTag());
        }

        public final void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            C0057g.Log(5, "Camera2: Capture session failed " + captureRequest.getTag() + " reason " + captureFailure.getReason());
            C0042a.this.m118a(captureRequest.getTag());
        }

        public final void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int i) {
        }

        public final void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int i, long j) {
        }
    };

    /* renamed from: B */
    private final CameraDevice.StateCallback f168B = new CameraDevice.StateCallback() {
        public final void onClosed(CameraDevice cameraDevice) {
            C0042a.f166e.release();
        }

        public final void onDisconnected(CameraDevice cameraDevice) {
            C0057g.Log(5, "Camera2: CameraDevice disconnected.");
            C0042a.this.m116a(cameraDevice);
            C0042a.f166e.release();
        }

        public final void onError(CameraDevice cameraDevice, int i) {
            C0057g.Log(6, "Camera2: Error opeining CameraDevice " + i);
            C0042a.this.m116a(cameraDevice);
            C0042a.f166e.release();
        }

        public final void onOpened(CameraDevice cameraDevice) {
            CameraDevice unused = C0042a.this.f172d = cameraDevice;
            C0042a.f166e.release();
        }
    };

    /* renamed from: C */
    private final ImageReader.OnImageAvailableListener f169C = new ImageReader.OnImageAvailableListener() {
        public final void onImageAvailable(ImageReader imageReader) {
            if (C0042a.f166e.tryAcquire()) {
                Image acquireNextImage = imageReader.acquireNextImage();
                if (acquireNextImage != null) {
                    Image.Plane[] planes = acquireNextImage.getPlanes();
                    if (acquireNextImage.getFormat() == 35 && planes != null && planes.length == 3) {
                        C0054d h = C0042a.this.f171a;
                        ByteBuffer buffer = planes[0].getBuffer();
                        ByteBuffer buffer2 = planes[1].getBuffer();
                        ByteBuffer buffer3 = planes[2].getBuffer();
                        h.mo6a(buffer, buffer2, buffer3, planes[0].getRowStride(), planes[1].getRowStride(), planes[1].getPixelStride());
                    } else {
                        C0057g.Log(6, "Camera2: Wrong image format.");
                    }
                    if (C0042a.this.f186s != null) {
                        C0042a.this.f186s.close();
                    }
                    Image unused = C0042a.this.f186s = acquireNextImage;
                }
                C0042a.f166e.release();
            }
        }
    };

    /* renamed from: D */
    private final SurfaceTexture.OnFrameAvailableListener f170D = new SurfaceTexture.OnFrameAvailableListener() {
        public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
            C0042a.this.f171a.mo5a(surfaceTexture);
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: a */
    public C0054d f171a = null;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public CameraDevice f172d;

    /* renamed from: f */
    private HandlerThread f173f;

    /* renamed from: g */
    private Handler f174g;

    /* renamed from: h */
    private Rect f175h;

    /* renamed from: i */
    private Rect f176i;

    /* renamed from: j */
    private int f177j;

    /* renamed from: k */
    private int f178k;

    /* renamed from: l */
    private float f179l = -1.0f;

    /* renamed from: m */
    private float f180m = -1.0f;

    /* renamed from: n */
    private int f181n;

    /* renamed from: o */
    private int f182o;

    /* renamed from: p */
    private boolean f183p = false;
    /* access modifiers changed from: private */

    /* renamed from: q */
    public Range f184q;
    /* access modifiers changed from: private */

    /* renamed from: r */
    public ImageReader f185r = null;
    /* access modifiers changed from: private */

    /* renamed from: s */
    public Image f186s;
    /* access modifiers changed from: private */

    /* renamed from: t */
    public CaptureRequest.Builder f187t;
    /* access modifiers changed from: private */

    /* renamed from: u */
    public CameraCaptureSession f188u = null;
    /* access modifiers changed from: private */

    /* renamed from: v */
    public Object f189v = new Object();

    /* renamed from: w */
    private int f190w;

    /* renamed from: x */
    private SurfaceTexture f191x;
    /* access modifiers changed from: private */

    /* renamed from: y */
    public Surface f192y = null;

    /* renamed from: z */
    private int f193z = C0048a.f201c;

    /* renamed from: com.unity3d.player.a$a */
    private enum C0048a {
        ;

        static {
            f202d = new int[]{f199a, f200b, f201c};
        }
    }

    protected C0042a(C0054d dVar) {
        this.f171a = dVar;
        m133g();
    }

    /* renamed from: a */
    public static int m107a(Context context) {
        return m127c(context).length;
    }

    /* renamed from: a */
    public static int m108a(Context context, int i) {
        try {
            return ((Integer) m120b(context).getCameraCharacteristics(m127c(context)[i]).get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
        } catch (CameraAccessException e) {
            C0057g.Log(6, "Camera2: CameraAccessException " + e);
            return 0;
        }
    }

    /* renamed from: a */
    private static int m109a(Range[] rangeArr, int i) {
        int i2 = -1;
        double d = Double.MAX_VALUE;
        for (int i3 = 0; i3 < rangeArr.length; i3++) {
            int intValue = ((Integer) rangeArr[i3].getLower()).intValue();
            int intValue2 = ((Integer) rangeArr[i3].getUpper()).intValue();
            float f = (float) i;
            if (f + 0.1f > ((float) intValue) && f - 0.1f < ((float) intValue2)) {
                return i;
            }
            double min = (double) ((float) Math.min(Math.abs(i - intValue), Math.abs(i - intValue2)));
            if (min < d) {
                i2 = i3;
                d = min;
            }
        }
        return ((Integer) (i > ((Integer) rangeArr[i2].getUpper()).intValue() ? rangeArr[i2].getUpper() : rangeArr[i2].getLower())).intValue();
    }

    /* renamed from: a */
    private static Rect m110a(Size[] sizeArr, double d, double d2) {
        Size[] sizeArr2 = sizeArr;
        double d3 = Double.MAX_VALUE;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < sizeArr2.length; i3++) {
            int width = sizeArr2[i3].getWidth();
            int height = sizeArr2[i3].getHeight();
            double d4 = (double) width;
            Double.isNaN(d4);
            double abs = Math.abs(Math.log(d / d4));
            double d5 = (double) height;
            Double.isNaN(d5);
            double abs2 = abs + Math.abs(Math.log(d2 / d5));
            if (abs2 < d3) {
                i = width;
                i2 = height;
                d3 = abs2;
            }
        }
        return new Rect(0, 0, i, i2);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m116a(CameraDevice cameraDevice) {
        synchronized (this.f189v) {
            this.f188u = null;
        }
        cameraDevice.close();
        this.f172d = null;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m118a(Object obj) {
        if (obj == "Focus") {
            this.f183p = false;
            synchronized (this.f189v) {
                if (this.f188u != null) {
                    try {
                        this.f187t.set(CaptureRequest.CONTROL_AF_TRIGGER, 0);
                        this.f187t.setTag("Regular");
                        this.f188u.setRepeatingRequest(this.f187t.build(), this.f167A, this.f174g);
                    } catch (CameraAccessException e) {
                        C0057g.Log(6, "Camera2: CameraAccessException " + e);
                    }
                }
            }
        } else if (obj == "Cancel focus") {
            synchronized (this.f189v) {
                if (this.f188u != null) {
                    m139j();
                }
            }
        }
    }

    /* renamed from: a */
    private static Size[] m119a(CameraCharacteristics cameraCharacteristics) {
        String str;
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap == null) {
            str = "Camera2: configuration map is not available.";
        } else {
            Size[] outputSizes = streamConfigurationMap.getOutputSizes(35);
            if (outputSizes != null && outputSizes.length != 0) {
                return outputSizes;
            }
            str = "Camera2: output sizes for YUV_420_888 format are not avialable.";
        }
        C0057g.Log(6, str);
        return null;
    }

    /* renamed from: b */
    private static CameraManager m120b(Context context) {
        if (f164b == null) {
            f164b = (CameraManager) context.getSystemService("camera");
        }
        return f164b;
    }

    /* renamed from: b */
    private void m122b(CameraCharacteristics cameraCharacteristics) {
        this.f178k = ((Integer) cameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue();
        if (this.f178k > 0) {
            this.f176i = (Rect) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            float width = ((float) this.f176i.width()) / ((float) this.f176i.height());
            float width2 = ((float) this.f175h.width()) / ((float) this.f175h.height());
            if (width2 > width) {
                this.f181n = 0;
                this.f182o = (int) ((((float) this.f176i.height()) - (((float) this.f176i.width()) / width2)) / 2.0f);
            } else {
                this.f182o = 0;
                this.f181n = (int) ((((float) this.f176i.width()) - (((float) this.f176i.height()) * width2)) / 2.0f);
            }
            this.f177j = Math.min(this.f176i.width(), this.f176i.height()) / 20;
        }
    }

    /* renamed from: b */
    public static boolean m124b(Context context, int i) {
        try {
            return ((Integer) m120b(context).getCameraCharacteristics(m127c(context)[i]).get(CameraCharacteristics.LENS_FACING)).intValue() == 0;
        } catch (CameraAccessException e) {
            C0057g.Log(6, "Camera2: CameraAccessException " + e);
            return false;
        }
    }

    /* renamed from: c */
    public static boolean m126c(Context context, int i) {
        try {
            return ((Integer) m120b(context).getCameraCharacteristics(m127c(context)[i]).get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue() > 0;
        } catch (CameraAccessException e) {
            C0057g.Log(6, "Camera2: CameraAccessException " + e);
            return false;
        }
    }

    /* renamed from: c */
    private static String[] m127c(Context context) {
        if (f165c == null) {
            try {
                f165c = m120b(context).getCameraIdList();
            } catch (CameraAccessException e) {
                C0057g.Log(6, "Camera2: CameraAccessException " + e);
                f165c = new String[0];
            }
        }
        return f165c;
    }

    /* renamed from: d */
    public static int[] m129d(Context context, int i) {
        try {
            Size[] a = m119a(m120b(context).getCameraCharacteristics(m127c(context)[i]));
            if (a == null) {
                return null;
            }
            int[] iArr = new int[(a.length * 2)];
            for (int i2 = 0; i2 < a.length; i2++) {
                int i3 = i2 * 2;
                iArr[i3] = a[i2].getWidth();
                iArr[i3 + 1] = a[i2].getHeight();
            }
            return iArr;
        } catch (CameraAccessException e) {
            C0057g.Log(6, "Camera2: CameraAccessException " + e);
            return null;
        }
    }

    /* renamed from: g */
    private void m133g() {
        this.f173f = new HandlerThread("CameraBackground");
        this.f173f.start();
        this.f174g = new Handler(this.f173f.getLooper());
    }

    /* renamed from: h */
    private void m136h() {
        this.f173f.quit();
        try {
            this.f173f.join(4000);
            this.f173f = null;
            this.f174g = null;
        } catch (InterruptedException e) {
            this.f173f.interrupt();
            C0057g.Log(6, "Camera2: Interrupted while waiting for the background thread to finish " + e);
        }
    }

    /* renamed from: i */
    private void m138i() {
        try {
            if (!f166e.tryAcquire(4, TimeUnit.SECONDS)) {
                C0057g.Log(5, "Camera2: Timeout waiting to lock camera for closing.");
                return;
            }
            this.f172d.close();
            try {
                if (!f166e.tryAcquire(4, TimeUnit.SECONDS)) {
                    C0057g.Log(5, "Camera2: Timeout waiting to close camera.");
                }
            } catch (InterruptedException e) {
                C0057g.Log(6, "Camera2: Interrupted while waiting to close camera " + e);
            }
            this.f172d = null;
            f166e.release();
        } catch (InterruptedException e2) {
            C0057g.Log(6, "Camera2: Interrupted while trying to lock camera for closing " + e2);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public void m139j() {
        try {
            if (this.f178k != 0 && this.f179l >= 0.0f && this.f179l <= 1.0f && this.f180m >= 0.0f) {
                if (this.f180m <= 1.0f) {
                    this.f183p = true;
                    int width = (int) ((((float) (this.f176i.width() - (this.f181n * 2))) * this.f179l) + ((float) this.f181n));
                    double height = (double) (this.f176i.height() - (this.f182o * 2));
                    double d = (double) this.f180m;
                    Double.isNaN(d);
                    Double.isNaN(height);
                    double d2 = height * (1.0d - d);
                    double d3 = (double) this.f182o;
                    Double.isNaN(d3);
                    int i = (int) (d2 + d3);
                    int max = Math.max(this.f177j + 1, Math.min(width, (this.f176i.width() - this.f177j) - 1));
                    int max2 = Math.max(this.f177j + 1, Math.min(i, (this.f176i.height() - this.f177j) - 1));
                    this.f187t.set(CaptureRequest.CONTROL_AF_REGIONS, new MeteringRectangle[]{new MeteringRectangle(max - this.f177j, max2 - this.f177j, this.f177j * 2, this.f177j * 2, 999)});
                    this.f187t.set(CaptureRequest.CONTROL_AF_MODE, 1);
                    this.f187t.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
                    this.f187t.setTag("Focus");
                    this.f188u.capture(this.f187t.build(), this.f167A, this.f174g);
                    return;
                }
            }
            this.f187t.set(CaptureRequest.CONTROL_AF_MODE, 4);
            this.f187t.setTag("Regular");
            if (this.f188u != null) {
                this.f188u.setRepeatingRequest(this.f187t.build(), this.f167A, this.f174g);
            }
        } catch (CameraAccessException e) {
            C0057g.Log(6, "Camera2: CameraAccessException " + e);
        }
    }

    /* renamed from: k */
    private void m140k() {
        try {
            if (this.f188u != null) {
                this.f188u.stopRepeating();
                this.f187t.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
                this.f187t.set(CaptureRequest.CONTROL_AF_MODE, 0);
                this.f187t.setTag("Cancel focus");
                this.f188u.capture(this.f187t.build(), this.f167A, this.f174g);
            }
        } catch (CameraAccessException e) {
            C0057g.Log(6, "Camera2: CameraAccessException " + e);
        }
    }

    /* renamed from: a */
    public final Rect mo171a() {
        return this.f175h;
    }

    /* renamed from: a */
    public final boolean mo172a(float f, float f2) {
        if (this.f178k <= 0) {
            return false;
        }
        if (!this.f183p) {
            this.f179l = f;
            this.f180m = f2;
            synchronized (this.f189v) {
                if (!(this.f188u == null || this.f193z == C0048a.f200b)) {
                    m140k();
                }
            }
            return true;
        }
        C0057g.Log(5, "Camera2: Setting manual focus point already started.");
        return false;
    }

    /* renamed from: a */
    public final boolean mo173a(Context context, int i, int i2, int i3, int i4, int i5) {
        try {
            CameraCharacteristics cameraCharacteristics = f164b.getCameraCharacteristics(m127c(context)[i]);
            if (((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2) {
                C0057g.Log(5, "Camera2: only LEGACY hardware level is supported.");
                return false;
            }
            Size[] a = m119a(cameraCharacteristics);
            if (!(a == null || a.length == 0)) {
                this.f175h = m110a(a, (double) i2, (double) i3);
                Range[] rangeArr = (Range[]) cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
                if (rangeArr == null || rangeArr.length == 0) {
                    C0057g.Log(6, "Camera2: target FPS ranges are not avialable.");
                } else {
                    int a2 = m109a(rangeArr, i4);
                    this.f184q = new Range(Integer.valueOf(a2), Integer.valueOf(a2));
                    try {
                        if (!f166e.tryAcquire(4, TimeUnit.SECONDS)) {
                            C0057g.Log(5, "Camera2: Timeout waiting to lock camera for opening.");
                            return false;
                        }
                        try {
                            f164b.openCamera(m127c(context)[i], this.f168B, this.f174g);
                            try {
                                if (!f166e.tryAcquire(4, TimeUnit.SECONDS)) {
                                    C0057g.Log(5, "Camera2: Timeout waiting to open camera.");
                                    return false;
                                }
                                f166e.release();
                                this.f190w = i5;
                                m122b(cameraCharacteristics);
                                return this.f172d != null;
                            } catch (InterruptedException e) {
                                C0057g.Log(6, "Camera2: Interrupted while waiting to open camera " + e);
                            }
                        } catch (CameraAccessException e2) {
                            C0057g.Log(6, "Camera2: CameraAccessException " + e2);
                            f166e.release();
                            return false;
                        }
                    } catch (InterruptedException e3) {
                        C0057g.Log(6, "Camera2: Interrupted while trying to lock camera for opening " + e3);
                        return false;
                    }
                }
            }
            return false;
        } catch (CameraAccessException e4) {
            C0057g.Log(6, "Camera2: CameraAccessException " + e4);
            return false;
        }
    }

    /* renamed from: b */
    public final void mo174b() {
        if (this.f172d != null) {
            mo177e();
            m138i();
            this.f167A = null;
            this.f192y = null;
            this.f191x = null;
            if (this.f186s != null) {
                this.f186s.close();
                this.f186s = null;
            }
            if (this.f185r != null) {
                this.f185r.close();
                this.f185r = null;
            }
        }
        m136h();
    }

    /* renamed from: c */
    public final void mo175c() {
        if (this.f185r == null) {
            this.f185r = ImageReader.newInstance(this.f175h.width(), this.f175h.height(), 35, 2);
            this.f185r.setOnImageAvailableListener(this.f169C, this.f174g);
            this.f186s = null;
            if (this.f190w != 0) {
                this.f191x = new SurfaceTexture(this.f190w);
                this.f191x.setDefaultBufferSize(this.f175h.width(), this.f175h.height());
                this.f191x.setOnFrameAvailableListener(this.f170D, this.f174g);
                this.f192y = new Surface(this.f191x);
            }
        }
        try {
            if (this.f188u == null) {
                this.f172d.createCaptureSession(Arrays.asList(this.f192y != null ? new Surface[]{this.f192y, this.f185r.getSurface()} : new Surface[]{this.f185r.getSurface()}), new CameraCaptureSession.StateCallback() {
                    public final void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                        C0057g.Log(6, "Camera2: CaptureSession configuration failed.");
                    }

                    public final void onConfigured(CameraCaptureSession cameraCaptureSession) {
                        if (C0042a.this.f172d != null) {
                            synchronized (C0042a.this.f189v) {
                                CameraCaptureSession unused = C0042a.this.f188u = cameraCaptureSession;
                                try {
                                    CaptureRequest.Builder unused2 = C0042a.this.f187t = C0042a.this.f172d.createCaptureRequest(1);
                                    if (C0042a.this.f192y != null) {
                                        C0042a.this.f187t.addTarget(C0042a.this.f192y);
                                    }
                                    C0042a.this.f187t.addTarget(C0042a.this.f185r.getSurface());
                                    C0042a.this.f187t.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, C0042a.this.f184q);
                                    C0042a.this.m139j();
                                } catch (CameraAccessException e) {
                                    C0057g.Log(6, "Camera2: CameraAccessException " + e);
                                }
                            }
                        }
                    }
                }, this.f174g);
            } else if (this.f193z == C0048a.f200b) {
                this.f188u.setRepeatingRequest(this.f187t.build(), this.f167A, this.f174g);
            }
            this.f193z = C0048a.f199a;
        } catch (CameraAccessException e) {
            C0057g.Log(6, "Camera2: CameraAccessException " + e);
        }
    }

    /* renamed from: d */
    public final void mo176d() {
        synchronized (this.f189v) {
            if (this.f188u != null) {
                try {
                    this.f188u.stopRepeating();
                    this.f193z = C0048a.f200b;
                } catch (CameraAccessException e) {
                    C0057g.Log(6, "Camera2: CameraAccessException " + e);
                }
            }
        }
    }

    /* renamed from: e */
    public final void mo177e() {
        synchronized (this.f189v) {
            if (this.f188u != null) {
                try {
                    this.f188u.abortCaptures();
                } catch (CameraAccessException e) {
                    C0057g.Log(6, "Camera2: CameraAccessException " + e);
                }
                this.f188u.close();
                this.f188u = null;
                this.f193z = C0048a.f201c;
            }
        }
    }
}
