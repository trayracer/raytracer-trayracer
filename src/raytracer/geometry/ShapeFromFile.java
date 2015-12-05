package raytracer.geometry;

import raytracer.material.LambertMaterial;
import raytracer.material.Material;
import raytracer.math.Point3;
import raytracer.math.Ray;
import raytracer.texture.Color;

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
        List<int[]> rawTriangles = new LinkedList<>();

        BufferedReader reader = new BufferedReader(new FileReader(objFile));
        String line;
        int vCount = 0;
        int fCount = 0;


        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()){
                line = line.replace("  ", " ").trim();
                if (line.startsWith("\u0023")) System.out.println(line); // Kommentare ausgeben
                String[] lineArray = line.split(" ", 2);
                switch(lineArray[0]){

                    case "v":   vCount++;
                        String[] coords = lineArray[1].split(" ", 3);
                        points.add(new Point3(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
                        break;

                    case "f":   fCount++;
                        String[] tri = lineArray[1].split(" ", 3);
                        rawTriangles.add(new int[]{Integer.parseInt(tri[0]), Integer.parseInt(tri[1]), Integer.parseInt(tri[2])});
                    default:    break;
                }
            }

        }

        System.out.println("Points: " + fCount + " Triangles: " + vCount);

        for (int [] tri : rawTriangles){
            triangles.add(new Triangle(points.get(tri[0]-1), points.get(tri[1]-1), points.get(tri[2]-1), this.material));
            //Random coloring
//            triangles.add(new Triangle(points.get(tri[0]-1), points.get(tri[1]-1), points.get(tri[2]-1), new LambertMaterial(new Color(Math.random(), Math.random(), Math.random()))));
        }

        //lbf und run finden
        double maxX = points.get(0).x;
        double maxY = points.get(0).y;
        double maxZ = points.get(0).z;
        double minX = points.get(0).x;
        double minY = points.get(0).y;
        double minZ = points.get(0).z;

        for (int i = 1; i < points.size(); i++){
            if (points.get(i).x > maxX) maxX = points.get(i).x;
            if (points.get(i).x < minX) minX = points.get(i).x;
            if (points.get(i).y > maxY) maxY = points.get(i).y;
            if (points.get(i).y < minY) minY = points.get(i).y;
            if (points.get(i).z > maxZ) maxZ = points.get(i).z;
            if (points.get(i).z < minZ) minZ = points.get(i).z;
        }
        // Bounding Box erstellen
        this.boundingBox = new AxisAlignedBox(new Point3(minX, minY, minZ), new Point3(maxX, maxY, maxZ), this.material);

    }
}
