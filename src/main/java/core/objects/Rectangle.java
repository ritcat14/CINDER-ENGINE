package core.objects;

public class Rectangle {

    private double x, y;
    private double width, height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle add(double x, double y, double width, double height) {
        return new Rectangle(this.x + x, this.y + y, this.width + width, this.height + height);
    }

    public Rectangle add(Rectangle rectangle) {
        return add(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void scroll(double xScroll, double yScroll) {
        x += xScroll;
        y += yScroll;
    }

    public boolean contains(double x, double y) {
        return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
    }

    public boolean contains(Point point) {
        return contains(point.x, point.y);
    }

    public boolean intersects(double x, double y, double width, double height) {
        Point topRight = new Point(this.x + this.width, this.y);
        Point bottomLeft = new Point(this.x, this.y + this.height);
        Point otherTopRight = new Point(x + width, y);
        Point otherBottomLeft = new Point(x, y + height);

        if (topRight.y > otherBottomLeft.y || bottomLeft.y < otherTopRight.y) return false;

        return !(topRight.x < otherBottomLeft.x) && !(bottomLeft.x > otherTopRight.x);
    }

    public boolean intersects(Rectangle rectangle) {
        return intersects(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void setBounds(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setBounds(Rectangle rectangle) {
        setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Point getLocation() {
        return new Point(x, y);
    }

}
