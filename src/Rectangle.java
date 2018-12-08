public class Rectangle {
  int length;
  int width;

  //Constructor.
  public Rectangle(int length, int width){
    this.length = length;
    this.width = width; 	
  }

  //Return the rectangle area
  public int getArea()  {
    return length*width;
  }

  public int getPerimeter()  {
    return 2*(length + width);
  }  
}
