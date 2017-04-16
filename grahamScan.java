import java.util.Scanner;
import java.util.*;

public class GrahamScan {
    private Stack<Point2D> hullPoints = new Stack<Point2D>();

    public GrahamScan(Point2D[] points) {
        int n = points.length;
        Point2D[] a = new Point2D[n];
        for (int i = 0; i < n; i++) {
            a[i] = points[i];
         }

        Arrays.sort(a);
        Arrays.sort(a, 1, n, a[0].angleDescend());

        hullPoints.push(a[0]);

        int k1;
        for (k1 = 1; k1 < n; k1++)
            if (!a[0].equals(a[k1])) break;
        if (k1 == n) return;

        int k2;
        for (k2 = k1+1; k2 < n; k2++)
            if (Point2D.ccw(a[0], a[k1], a[k2]) != 0) break;
        hullPoints.push(a[k2-1]);

        for (int i = k2; i < n; i++) {
            Point2D top = hullPoints.pop();
            while (Point2D.ccw(hullPoints.peek(), top, a[i]) <= 0) {
                top = hullPoints.pop();
            }
            hullPoints.push(top);
            hullPoints.push(a[i]);
        }

        assert isConvex();
    }

    public Iterable<Point2D> hullPoints() {
        Stack<Point2D> s = new Stack<Point2D>();
        for (Point2D p : hullPoints) s.push(p);
        return s;
    }

    private boolean isConvex() {
        int n = hullPoints.size();
        if (n <= 2) return true;

        Point2D[] points = new Point2D[n];
        int k = 0;
        for (Point2D p : hullPoints()) {
            points[k++] = p;
        }

        for (int i = 0; i < n; i++) {
            if (Point2D.ccw(points[i], points[(i+1) % n], points[(i+2) % n]) <= 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            points[i] = new Point2D(x, y);
        }
        GrahamScan graham = new GrahamScan(points);
        for (Point2D p : graham.hullPoints())
            System.out.println(p);
    }

}