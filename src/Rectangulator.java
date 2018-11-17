public class Rectangulator {
  
  public static void main(String[] args) {
    int length = Integer.parseInt(args[0]);
    int width = Integere.parseInt(args[1]);

    Rectangle myRectangle = new Rectangle(length, width);

    String output = String.format("My rectangle has length %d, width %d, area %d and perimeter %d", myRectangle.length, myRectangle.width, myRectangle.getArea(), myRectangle.getPerimeter);
    System.out.println(output);			
  }

}
