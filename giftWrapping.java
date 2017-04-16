import java.util.Scanner;
import java.util.Arrays;
 
class Point
{
    int x, y;
}
 

class GiftWrap
{    
    public void convexHull(Point[] points)
    {
        int n = points.length;
        // If only 3 points (TRIVIAL CASE)
        // Then they form a triangle
        // Hence "return"        
        if (n < 3) 
            return;     
        int[] next = new int[n];
        Arrays.fill(next, -1);
 
        // Start off with the LEFTMOST point
        // Since its surely in CONVEL HULL
        int leftMost = 0;
        for (int i = 1; i < n; i++)
            if (points[i].x < points[leftMost].x)
                leftMost = i;
        int p = leftMost, q;

        // Find the rest of the points of HULL
        do
        {
            q = (p + 1) % n;
            for (int i = 0; i < n; i++)
              if (checkLocation(points[p], points[i], points[q]))
                 q = i;
 
            next[p] = q;  
            p = q; 
        } while (p != leftMost);
 
        /*****     OUTPUT   *****/
        displayResult(points, next);
    }

    public void displayResult(Point[] points, int[] next)
    {
        System.out.println("\nConvex Hull points : ");
        for (int i = 0; i < next.length; i++)
            if (next[i] != -1)
               System.out.println("("+ points[i].x +", "+ points[i].y +")");
    }

    private boolean checkLocation(Point p, Point q, Point r)
    {
        int tempVal = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
 
         if (tempVal >= 0)
             return false;
         return true;
    }


    //*****     Main function     *****//
    public static void main (String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Gift Algorithm Test\n");
        GiftWrap giftWrap = new GiftWrap();        
 
        System.out.println("Enter number of points (n) :");
        int n = scan.nextInt();
        Point[] points = new Point[n];
        System.out.println("Enter the coordinates of each points: <x> <y>");
        for (int i = 0; i < n; i++)
        {
            points[i] = new Point();
            points[i].x = scan.nextInt();
            points[i].y = scan.nextInt();
        }        
        giftWrap.convexHull(points);        
    }
}