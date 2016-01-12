package raytracer.ui;

import raytracer.sampling.SamplingPattern;

/**
 * @author Oliver Kniejski
 */
public class GlobalConfig {
    public static final boolean DEBUG_MODE = true;

    public static final boolean THREADED_RENDERING = true;
    public static final int RENDER_THREADS = 4;//Runtime.getRuntime().availableProcessors() - 2;
    public static final boolean LINEAR_RENDERING = false; //always true for non threaded rendering as of now

    // UI USE / true = FX Image; false = SWT Image
    public static final boolean FX_IMAGE = false;

    public static final int TODO_COLOR = new java.awt.Color(255, 0, 255, 255).getRGB();
    public static final SamplingPattern CAMERA_SAMPLING_PATTERN = new SamplingPattern().randomPattern(20);
    public static final int RECURSION_DEPTH = 100;
}
