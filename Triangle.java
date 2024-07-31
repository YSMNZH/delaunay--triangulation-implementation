import java.util.ArrayList;

class Triangle {
    public Point[] vertices;
    public ArrayList<Point[]> edges;

    public Triangle(Point a, Point b, Point c) {
        vertices = new Point[3];
        vertices[0] = a;
        vertices[1] = b;
        vertices[2] = c;

        edges = new ArrayList<>();
        edges.add(new Point[]{vertices[0], vertices[1]});
        edges.add(new Point[]{vertices[1], vertices[2]});
        edges.add(new Point[]{vertices[2], vertices[0]});

    }

    public boolean hasEqualVertex(Point point) {
        return vertices[0].equals(point) || vertices[1].equals(point) || vertices[2].equals(point);
    }

    @Override
    public String toString() {
        return "( " + vertices[0] + ", " + vertices[1] + ", " + vertices[2] + " )";
    }
}
