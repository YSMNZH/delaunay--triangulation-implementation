import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    public static void main(String[] args) {
//        If I Want To Measure Elapsed Time With Given Numbers
//        Scanner scanner = new Scanner(System.in);
//        long startTime = System.nanoTime();
//        int n = scanner.nextInt();
//        List<Point> pointList = new ArrayList<>();
//        ArrayList<Integer> pointNumber = new ArrayList<>();
//        ArrayList<Long> elapsedTime = new ArrayList<>();
//        for(int i = 0;i<n;i++){
//            int number = scanner.nextInt();
//            pointNumber.add(number);
//            pointList = generateRandomPoints(number);
//            long finishTime = System.nanoTime();
//            System.out.println(finishTime - startTime);
//            elapsedTime.add(finishTime-startTime);
//        }
//        System.out.println(pointNumber);
//        System.out.println(elapsedTime);
//        If I Want To Measure Elapsed Time With Given Number One By One
//        List<Point> pointList = generateRandomPoints(50);
        List<Point> pointList = new ArrayList<>();
        String filePath = "C:/Users/Jasmine/IdeaProjects/AD_Project/input_sample/test.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] coordinate = line.split(" ");
                pointList.add(new Point(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1])));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        DelaunayTriangulation delaunayTriangulation = new DelaunayTriangulation(WIDTH, HEIGHT);

        for (Point point : pointList) {
            delaunayTriangulation.triangulate(point);
        }

        delaunayTriangulation.removeSuperTriangles();

        List<List<Double>> exportedVertices = delaunayTriangulation.getResult();
        List<Double> VerticesX = exportedVertices.get(0);
        List<Double> VerticesY = exportedVertices.get(1);
//         long finishTime = System.nanoTime();
//       System.out.println(finishTime - startTime);
        plotTriangulation(VerticesX, VerticesY);
    }

    private static List<Point> generateRandomPoints(int n) {
        List<Point> points = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            double x = random.nextDouble(1, WIDTH - 1);
            double y = random.nextDouble(1, HEIGHT - 1);
            points.add(new Point(x, y));
        }
        return points;
    }

    private static void plotTriangulation(List<Double> verticesX, List<Double> verticesY) {
        int i = 0;
        ArrayList<ArrayList<Point>> triangleList = new ArrayList<>();
        ArrayList<Point> points = new ArrayList<>();
        while (i < verticesX.size()) {
            if (i % 3 == 0 && i > 0) {
                triangleList.add(points);
                points = new ArrayList<>();
            }
            points.add(new Point(verticesX.get(i), verticesY.get(i)));
            i++;
        }
        triangleList.add(points);
        System.out.println(ANSI_CYAN + "X Coordinates Of Triangles Vertices: " + verticesX + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Y Coordinates Of Triangles Vertices: " + verticesY + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "Triangle Vertices Coordinates: " + triangleList + ANSI_RESET);
        drawTrianglesEdges(triangleList);
    }

    public static void drawTrianglesEdges(ArrayList<ArrayList<Point>> triangles) {
        Frame frame = new Frame("Triangle Edges Drawer") {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.BLUE);
                for (ArrayList<Point> triangle : triangles) {
                    for (int i = 0; i < triangle.size(); i++) {
                        Point start = triangle.get(i);
                        Point end = triangle.get((i + 1) % triangle.size());
                        g.drawLine((int) (start.x * 500 + 620), (int) (-start.y * 500 + 620), (int) (end.x * 500 + 620), (int) (-end.y * 500 + 620));
                    }
                }
            }
        };

        frame.setSize(2000, 1000);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}

