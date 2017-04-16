import java.util.Scanner;
import java.util.ArrayList;
import java.awt.*; 

class QuickHull
{
    
 public ArrayList<Point> findQuickHull(ArrayList<Point> points) {
    ArrayList<Point> convexHull = new ArrayList<Point>();
    if (points.size() < 3) return (ArrayList)points.clone();
   
    int minPoint = -1, maxPoint = -1;
    int minX = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    for (int i = 0; i < points.size(); i++) {
      if (points.get(i).x < minX) {
        minX = points.get(i).x;
        minPoint = i;
      } 
      if (points.get(i).x > maxX) {
        maxX = points.get(i).x;
        maxPoint = i;       
      }
    }
    Point A = points.get(minPoint);
    Point B = points.get(maxPoint);
    convexHull.add(A);
    convexHull.add(B);
    points.remove(A);
    points.remove(B);
    
    ArrayList<Point> leftSet = new ArrayList<Point>();
    ArrayList<Point> rightSet = new ArrayList<Point>();
    
    for (int i = 0; i < points.size(); i++) {
      Point p = points.get(i);
      if (checkLocation(A,B,p) == -1)
        leftSet.add(p);
      else
        rightSet.add(p);
    }
    setOfHull(A,B,rightSet,convexHull);
    setOfHull(B,A,leftSet,convexHull);
    
    return convexHull;
  }
  
  public int calcDistance(Point A, Point B, Point C) {
    int ABx = B.x-A.x;
    int ABy = B.y-A.y;
    int num = ABx*(A.y-C.y)-ABy*(A.x-C.x);
    if (num < 0) num = -num;
    return num;
  }
  
  public void setOfHull(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull) {
    int insertPos = hull.indexOf(B);
    if (set.size() == 0) return;
    if (set.size() == 1) {
      Point p = set.get(0);
      set.remove(p);
      hull.add(insertPos,p);
      return;
    }
    int dist = Integer.MIN_VALUE;
    int distantPoint = -1;
    for (int i = 0; i < set.size(); i++) {
      Point p = set.get(i);
      int calcDistance  = calcDistance(A,B,p);
      if (calcDistance > dist) {
        dist = calcDistance;
        distantPoint = i;
      }
    }
    Point P = set.get(distantPoint);
    set.remove(distantPoint);
    hull.add(insertPos,P);
    
    ArrayList<Point> leftPointsA = new ArrayList<Point>();
    for (int i = 0; i < set.size(); i++) {
      Point M = set.get(i);
      if (checkLocation(A,P,M)==1) {
        leftPointsA.add(M);
      }
    }
    
    ArrayList<Point> leftPointsB = new ArrayList<Point>();
    for (int i = 0; i < set.size(); i++) {
      Point M = set.get(i);
      if (checkLocation(P,B,M)==1) {
        leftPointsB.add(M);
      }
    }
    setOfHull(A,P,leftPointsA,hull);
    setOfHull(P,B,leftPointsB,hull);
    
  }

  public int checkLocation(Point A, Point B, Point P) {
    int cp1 = (B.x-A.x)*(P.y-A.y) - (B.y-A.y)*(P.x-A.x);
    return (cp1>0)?1:-1;
  }


  public static void main(String args[])
  {
      System.out.println("Quick Hull Test");
      Scanner in = new Scanner(System.in);
      System.out.println("Enter number of points (n) :");
      int N = in.nextInt();

      ArrayList<Point> testPoints = new ArrayList<Point>();
      System.out.println("Enter the coordinates of each points: <x> <y>");
      for (int i = 0; i < N; i++)
      {
          int x = in.nextInt();
          int y = in.nextInt();
          Point e = new Point(x, y);
          testPoints.add(i, e);
      }

      QuickHull quickHull = new QuickHull();
      ArrayList<Point> p = quickHull.findQuickHull(testPoints);
	
	
	   System.out.println("The points in the Convex hull using Quick Hull are: ");
     for (int i = 0; i < p.size(); i++)
        System.out.println("(" + p.get(i).x + ", " + p.get(i).y + ")");
     in.close();
  }

}