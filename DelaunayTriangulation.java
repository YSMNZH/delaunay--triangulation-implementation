import java.util.ArrayList;
import java.util.List;

class DelaunayTriangulation {
    List<Triangle> triangles = new ArrayList<>();
    Point superTriangleVertexA;
    Point superTriangleVertexB;
    Point superTriangleVertexC;

    public DelaunayTriangulation(double WIDTH, double HEIGHT) {
        superTriangleVertexA = new Point(-100, -100);
        superTriangleVertexB = new Point(2 * WIDTH + 100, -100);
        superTriangleVertexC = new Point(-100, 2 * HEIGHT + 100);
        Triangle superTriangle = new Triangle(superTriangleVertexA, superTriangleVertexB, superTriangleVertexC);
        triangles.add(superTriangle);
    }

    public void triangulate(Point point) {
        List<Triangle> badTriangles = new ArrayList<>();
        for (Triangle triangle : triangles) {
            if (isInCircumcircle(triangle, point)) {
                badTriangles.add(triangle);
            }
        }
        List<Point[]> polygon = new ArrayList<>();
        for (Triangle triangle : badTriangles) {
            for (Point[] edge : triangle.edges) {
                boolean isCommon = false;
                for (Triangle otherTriangle : badTriangles) {
                    if (triangle == otherTriangle) {
                        continue;
                    }
                    for (Point[] otherEdge : otherTriangle.edges) {
                        if (sharedEdge(edge, otherEdge)) {
                            isCommon = true;
                        }
                    }
                }
                if (!isCommon) {
                    polygon.add(edge);
                }
            }
        }
        triangles.removeAll(badTriangles);
        for (Point[] edge : polygon) {
            Triangle newTriangle = new Triangle(edge[0], edge[1], point);
            triangles.add(newTriangle);
        }
    }

    public boolean isInCircumcircle(Triangle triangle, Point point) {
        double aX = triangle.vertices[0].x;
        double aY = triangle.vertices[0].y;

        double bX = triangle.vertices[1].x;
        double bY = triangle.vertices[1].y;

        double cX = triangle.vertices[2].x;
        double cY = triangle.vertices[2].y;

        double[][] inCircumcircle = {
                {aX - point.x, aY - point.y, Math.pow(aX - point.x, 2) + Math.pow(aY - point.y, 2)},
                {bX - point.x, bY - point.y, Math.pow(bX - point.x, 2) + Math.pow(bY - point.y, 2)},
                {cX - point.x, cY - point.y, Math.pow(cX - point.x, 2) + Math.pow(cY - point.y, 2)}
        };

        return calculateDeterminant(inCircumcircle) > 0;
    }

    private double calculateDeterminant(double[][] matrix) {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[2][1] * matrix[1][2]) -
                matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[2][0] * matrix[1][2]) +
                matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[2][0] * matrix[1][1]);
    }

    private boolean sharedEdge(Point[] edge1, Point[] edge2) {
        return (edge1[0].equals(edge2[0]) && edge1[1].equals(edge2[1])) ||
                (edge1[0].equals(edge2[1]) && edge1[1].equals(edge2[0]));
    }

    public void removeSuperTriangles() {
        triangles.removeIf(triangle -> triangle.hasEqualVertex(superTriangleVertexA) ||
                triangle.hasEqualVertex(superTriangleVertexB) ||
                triangle.hasEqualVertex(superTriangleVertexC));
    }

    public List<List<Double>> getResult() {
        List<List<Double>> result = new ArrayList<>();
        List<Point> points = new ArrayList<>();
        for (Triangle triangle : triangles) {
            for (Point vertex : triangle.vertices) {
                points.add(vertex);
            }
        }
        List<Double> pointX = new ArrayList<>();
        for (Point point : points) {
            pointX.add(point.x);
        }
        List<Double> pointY = new ArrayList<>();
        for (Point point : points) {
            pointY.add(point.y);
        }
        result.add(pointX);
        result.add(pointY);
        return result;
    }
}