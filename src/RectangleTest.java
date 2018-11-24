import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class RectangleTest {

  private Rectangle rectangle;

  @BeforeClass
  public void setUp()  {
    rectangle = new Rectangle(3, 4);
  }
    

  @Test
  public void checkArea() {
    assertEquals(12, rectangle.getArea());
  }

  @Test 
  public void checkPerimeter() {
    assertEquals(14, rectangle.getPerimeter());
  }
}
