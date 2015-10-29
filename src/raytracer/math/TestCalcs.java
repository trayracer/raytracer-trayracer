package raytracer.math;

/**
 * Created by ok on 29.10.15.
 */
public class TestCalcs {
    public static void main(String args[]) {


        System.out.println("-- Normale * Konstante ---------------------------------------------------------------------");
        System.out.println("N(1, 2, 3) * 0.5 = N(0.5, 1, 1.5)");
        printMath(new Normal3(1, 2, 3).mul(0.5));
        testRes(new Normal3(1, 2, 3).mul(0.5).equals(new Normal3(0.5, 1, 1.5)));

//        System.out.println("-- Normale + Normale -----------------------------------------------------------------------");
//        System.out.println("N(1, 2, 3) + N(3, 2, 1) = N(4, 4, 4)");
//        printMath(new Normal3(1, 2, 3).add(new Normal3(3, 2, 1)));
//        testRes(new Normal3(1, 2, 3).add(new Normal3(3, 2, 1)).equals(new Normal3(4,4,4)));

//        System.out.println("-- Normale dot Vektor ----------------------------------------------------------------------");
//        System.out.println("N(1, 0, 0) dot V(1, 0, 0) = 1");
//        System.out.println(new Normal3(1, 0, 0).dot(new Vector3(1, 0, 0)));
//        testRes(new Normal3(1, 0, 0).dot(new Vector3(1, 0, 0)) == 1);
//
//        System.out.println("N(1, 0, 0) dot V(0, 1, 0) = 0");
//        System.out.println(new Normal3(1, 0, 0).dot(new Vector3(0, 1, 0)));
//        testRes(new Normal3(1, 0, 0).dot(new Vector3(0, 1, 0))==0);

        System.out.println("-- Vektor dot Normale ----------------------------------------------------------------------");
        System.out.println("V(1, 0, 0) dot N(1, 0, 0) = 1");
        System.out.println(new Vector3(1, 0, 0).dot(new Normal3(1, 0, 0)));
        testRes(new Vector3(1, 0, 0).dot(new Normal3(1, 0, 0))==1);

        System.out.println("V(1, 0, 0) dot N(0, 1, 0) = 0");
        System.out.println(new Vector3(1, 0, 0).dot(new Normal3(0, 1, 0)));
        testRes(new Vector3(1, 0, 0).dot(new Normal3(0, 1, 0))==0);

        System.out.println("-- Vektor dot Vektor -----------------------------------------------------------------------");
        System.out.println("V(1, 0, 0) dot V(1, 0, 0) = 1");
        System.out.println(new Vector3(1, 0, 0).dot(new Vector3(1, 0, 0)));
        testRes(new Vector3(1, 0, 0).dot(new Vector3(1, 0, 0))==1);

        System.out.println("V(1, 0, 0) dot V(0, 1, 0) = 0");
        System.out.println(new Vector3(1, 0, 0).dot(new Vector3(0, 1, 0)));
        testRes(new Vector3(1, 0, 0).dot(new Vector3(0, 1, 0)) == 0);

        System.out.println("-- Punkt - Punkt ---------------------------------------------------------------------------");
        System.out.println("P(1, 1, 1) - P(2, 2, 0) = V(-1, -1, 1)");
        printMath(new Point3(1, 1, 1).sub(new Point3(2, 2, 0)));
        testRes(new Point3(1, 1, 1).sub(new Point3(2, 2, 0)).equals(new Vector3(-1,-1,1)));

//        System.out.println("-- Punkt - Vektor --------------------------------------------------------------------------");
//        System.out.println("P(1, 1, 1) - V(4, 3, 2) = P(-3, -2, -1)");
//        printMath(new Point3(1, 1, 1).sub(new Vector3(4, 3, 2)));
//        testRes(new Point3(1, 1, 1).sub(new Vector3(4, 3, 2)).equals(new Point3(-3,-2,-1)));

//        System.out.println("-- Punkt + Vektor --------------------------------------------------------------------------");
//        System.out.println("P(1, 1, 1) + V(4, 3, 2) = P(5, 4, 3)");
//        printMath(new Point3(1, 1, 1).add(new Vector3(4, 3, 2)));
//        testRes(new Point3(1, 1, 1).add(new Vector3(4, 3, 2)).equals(new Point3(5, 4, 3)));

        System.out.println("-- Vektor Betrag ---------------------------------------------------------------------------");
        System.out.println("|V(1, 1, 1)| = sqrt(3)");
        System.out.println(new Vector3(1, 1, 1).magnitude);
        testRes(new Vector3(1, 1, 1).magnitude == Math.sqrt(3));

        // TODO Vektor add, sub, mul nach genanntem Schema hier zu implementieren

        System.out.println("-- Vektor reflektiert an Normale -----------------------------------------------------------");
        System.out.println("V(-0.707, 0.707, 0) reflectedOn N(0, 1, 0) = V(0.707, 0.707, 0)");
        printMath(new Vector3(-0.707, 0.707, 0).reflectedOn(new Normal3(0, 1, 0)));
        testRes(new Vector3(-0.707, 0.707, 0).reflectedOn(new Normal3(0, 1, 0)).equals(new Vector3(0.707, 0.707, 0)));

        System.out.println("V(0.707, 0.707, 0) reflectedOn N(1, 0, 0) = V(0.707, -0.707, 0)");
        printMath(new Vector3(0.707, 0.707, 0).reflectedOn(new Normal3(1, 0, 0)));
        testRes(new Vector3(0.707, 0.707, 0).reflectedOn(new Normal3(1, 0, 0)).equals(new Vector3(0.707, -0.707, 0)));

        System.out.println("-- Matrix * Punkt --------------------------------------------------------------------------");
        System.out.println("M(1,0,0,  0,1,0,  0,0,1) * P(3, 2, 1) = P(3, 2, 1)");
        printMath(new Mat3x3(1,0,0,  0,1,0,  0,0,1).mul(new Point3(3, 2, 1)));
        testRes(new Mat3x3(1,0,0,  0,1,0,  0,0,1).mul(new Point3(3, 2, 1)).equals(new Point3(3, 2, 1)));

        System.out.println("-- Matrix * Vektor -------------------------------------------------------------------------");
        System.out.println("M(1,0,0,  0,1,0,  0,0,1) * V(3, 2, 1) = V(3, 2, 1)");
        printMath(new Mat3x3(1,0,0,  0,1,0,  0,0,1).mul(new Vector3(3, 2, 1)));
        testRes(new Mat3x3(1,0,0,  0,1,0,  0,0,1).mul(new Vector3(3, 2, 1)).equals(new Vector3(3, 2, 1)));

        System.out.println("-- Matrix * Matrix -------------------------------------------------------------------------");
        System.out.println("M(1,2,3,  4,5,6,  7,8,9) * M(1,0,0,  0,1,0,  0,0,1) = M(1,2,3,  4,5,6,  7,8,9)");
        printMath(new Mat3x3(1,2,3,  4,5,6,  7,8,9).mul(new Mat3x3(1,0,0,  0,1,0,  0,0,1)));
        testRes(new Mat3x3(1,2,3,  4,5,6,  7,8,9).mul(new Mat3x3(1,0,0,  0,1,0,  0,0,1)).equals(new Mat3x3(1,2,3,  4,5,6,  7,8,9)));

        System.out.println("M(1,2,3,  4,5,6,  7,8,9) * M(0,0,1,  0,1,0,  1,0,0) = M(3,2,1,  6,5,4,  9,8,7)");
        printMath(new Mat3x3(1,2,3,  4,5,6,  7,8,9).mul(new Mat3x3(0,0,1,  0,1,0,  1,0,0)));
        testRes(new Mat3x3(1,2,3,  4,5,6,  7,8,9).mul(new Mat3x3(0,0,1,  0,1,0,  1,0,0)).equals(new Mat3x3(3,2,1,  6,5,4,  9,8,7)));

        System.out.println("-- Matrix changeCol ------------------------------------------------------------------------");
        System.out.println("M(1,2,3,  4,5,6,  7,8,9) changeCol1 V(8, 8, 8) = M(8,2,3,  8,5,6,  8,8,9)");
        printMath(new Mat3x3(1,2,3,  4,5,6,  7,8,9).changeCol1(new Vector3(8,8,8)));
        testRes(new Mat3x3(1,2,3,  4,5,6,  7,8,9).changeCol1(new Vector3(8,8,8)).equals(new Mat3x3(8,2,3,  8,5,6,  8,8,9)));

        System.out.println("M(1,2,3,  4,5,6,  7,8,9) changeCol2 V(8, 8, 8) = M(1,8,3,  4,8,6,  7,8,9)");
        printMath(new Mat3x3(1,2,3,  4,5,6,  7,8,9).changeCol2(new Vector3(8, 8, 8)));
        testRes(new Mat3x3(1,2,3,  4,5,6,  7,8,9).changeCol2(new Vector3(8, 8, 8)).equals(new Mat3x3(1,8,3,  4,8,6,  7,8,9)));

        System.out.println("M(1,2,3,  4,5,6,  7,8,9) changeCol3 V(8, 8, 8) = M(1,2,8,  4,5,8,  7,8,8)");
        printMath(new Mat3x3(1,2,3,  4,5,6,  7,8,9).changeCol3(new Vector3(8, 8, 8)));
        testRes(new Mat3x3(1,2,3,  4,5,6,  7,8,9).changeCol3(new Vector3(8, 8, 8)).equals(new Mat3x3(1,2,8,  4,5,8,  7,8,8)));

    }

    public static void printMath(Mat3x3 m) {
        System.out.println("M( " + m.m11 + ", " + m.m12 + ", " + m.m13 + ",   " + m.m21 + ", " + m.m22 + ", " + m.m23 + ",   " + m.m31 + ", " + m.m32 + ", " + m.m33 + " )");
    }

    public static void printMath(Vector3 v) {
        System.out.println("V( " + v.x + ", " + v.y + ", " + v.z + " )");
    }

    public static void printMath(Normal3 n) {
        System.out.println("N( " + n.x + ", " + n.y + ", " + n.z + " )");
    }

    public static void printMath(Point3 p) {
        System.out.println("P( " + p.x + ", " + p.y + ", " + p.z + " )");
    }

    public static void testRes(boolean b){
        if (b)
            System.out.println("                                                                                   bestanden\n");
        else
            System.out.println("                                                                                 F A I L E D !\n");
    }
}
