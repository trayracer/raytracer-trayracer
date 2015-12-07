package raytracer.geometry;

import raytracer.material.Material;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Ray;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * This class
 *
 * @author Oliver Kniejski
 */
public class ShapeFromFile extends Geometry {

    private final File objFile;
    private List<Geometry> triangles = new LinkedList<>();
    public AxisAlignedBox boundingBox;

    public ShapeFromFile(File objFile, Material material) {
        super(material);
        this.objFile = objFile;
        try {
            parser();
        } catch (IOException e) {
            throw new IllegalArgumentException("IO Probleme !");
        }
    }

    @Override
    public Hit hit(final Ray ray) {
        if (ray == null) throw new IllegalArgumentException("Ray must not be null.");
        if (this.boundingBox.hit(ray) == null) return null;
        if (triangles.size() == 0) return null;
        List<Hit> hits = new LinkedList<>();
        //noinspection Convert2streamapi
        for (Geometry geo : triangles) {
            if (geo.hit(ray) != null) {
                hits.add(geo.hit(ray));
            }
        }
        if (hits.size() == 0) return null;
        Hit smallestHit = hits.get(0);
        if (hits.size() > 1) {
            for (int i = 1; i < hits.size(); i++) {
                if (hits.get(i).t < smallestHit.t) {
                    smallestHit = hits.get(i);
                }
            }
        }
        return smallestHit;
    }

    /**
     * This method parses the given .obj file to triangles and saves them in this object.
     *
     * @throws IOException
     */
    private void parser() throws IOException {
        List<Point3> points = new LinkedList<>();
        List<double[]> textureCoordinates = new LinkedList<>();
        List<Normal3> normals = new LinkedList<>();

        BufferedReader reader = new BufferedReader(new FileReader(objFile));
        String line;

        int vCount = 0;
        int vtCount = 0;
        int vnCount = 0;
        int fCount = 0;

        // vatiables for lbf & run
        double maxX = -1000000000;
        double maxY = -1000000000;
        double maxZ = -1000000000;
        double minX = 1000000000;
        double minY = 1000000000;
        double minZ = 1000000000;


        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }
            if (line.startsWith("#")) {
                System.out.println(line); // # print comments
                continue;
            }

            // remove unnecessary spaces
            line = line.replace("     ", " ").replace("    ", " ").replace("   ", " ").replace("  ", " ").trim(); //TODO: das geht bestimmt schöner
            String[] lineArray = line.split(" ", 5);

            switch (lineArray[0]) {
                case "v":
                    vCount++;
                    Point3 point = new Point3(Double.parseDouble(lineArray[1]), Double.parseDouble(lineArray[2]), Double.parseDouble(lineArray[3]));
                    points.add(point);

                    // find lbf & run
                    if (point.x > maxX) maxX = point.x;
                    if (point.x < minX) minX = point.x;
                    if (point.y > maxY) maxY = point.y;
                    if (point.y < minY) minY = point.y;
                    if (point.z > maxZ) maxZ = point.z;
                    if (point.z < minZ) minZ = point.z;
                    break;

                case "vt":
                    vtCount++;
                    textureCoordinates.add(new double[]{Double.parseDouble(lineArray[1]), Double.parseDouble(lineArray[2])});
                    break;

                case "vn":
                    vnCount++;
                    normals.add(new Normal3(Double.parseDouble(lineArray[1]), Double.parseDouble(lineArray[2]), Double.parseDouble(lineArray[3])));
                    break;

                case "f":
                    fCount++;

                    String[] fa = lineArray[1].split("/", 3);
                    String[] fb = lineArray[2].split("/", 3);
                    String[] fc = lineArray[3].split("/", 3);

                    if (fa.length != fb.length || fb.length != fc.length) throw new RuntimeException("Illegal .obj format.");

                    Point3 a = getVertex(Integer.parseInt(fa[0]), points);
                    Point3 b = getVertex(Integer.parseInt(fb[0]), points);
                    Point3 c = getVertex(Integer.parseInt(fc[0]), points);

                    double[] at;
                    double[] bt;
                    double[] ct;
                    if (fa.length >= 2 && !fa[1].isEmpty()){
                        at = getTexture(Integer.parseInt(fa[1]), textureCoordinates);
                        bt = getTexture(Integer.parseInt(fb[1]), textureCoordinates);
                        ct = getTexture(Integer.parseInt(fc[1]), textureCoordinates);
                    }

                    if (fa.length < 3) {
                        Triangle tri = new Triangle(a, b, c, this.material);
//                        if (triangles.contains(tri)) throw new RuntimeException("Illegal .obj format.");
                        triangles.add(tri);
                        break;
                    }

                    Normal3 na = getNormal(Integer.parseInt(fa[2]), normals);
                    Normal3 nb = getNormal(Integer.parseInt(fb[2]), normals);
                    Normal3 nc = getNormal(Integer.parseInt(fc[2]), normals);

                    Triangle tri = new Triangle(a, b, c, na, nb, nc, this.material);
//                    if (triangles.contains(tri)) throw new RuntimeException("Illegal .obj format.");
                    triangles.add(tri);
                    break;

                default:
                    break;
            }
        }

        System.out.println("Points: " + fCount + " TexCoords: " + vtCount + " Normals: " + vnCount + " Triangles: " + vCount);

        // Bounding Box erstellen
        this.boundingBox = new AxisAlignedBox(new Point3(minX, minY, minZ), new Point3(maxX, maxY, maxZ), this.material);
    }

    /**
     * This method gets the correct vertex Point according to .obj file specifications.
     *
     * @param i      The index of the vertex point.
     * @param points The list containing the vertex points.
     * @return The specified vertex Point.
     */
    private Point3 getVertex(int i, List<Point3> points) {
        if (i > 0) {
            return points.get(i - 1);
        } else if (i < 0) {
            return points.get(points.size() + i);
        } else throw new IllegalArgumentException("vertex index must not be 0.");
    }

    private Normal3 getNormal(int i, List<Normal3> normals) {
        if (i > 0) {
            return normals.get(i - 1);
        } else if (i < 0) {
            return normals.get(normals.size() + i);
        } else throw new IllegalArgumentException("normal index must not be 0.");
    }

    private double[] getTexture(int i, List<double[]> textures) {
        if (i > 0) {
            return textures.get(i - 1);
        } else if (i < 0) {
            return textures.get(textures.size() + i);
        } else throw new IllegalArgumentException("texture index must not be 0.");
    }
}
