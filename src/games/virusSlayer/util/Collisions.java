package games.virusSlayer.util;

import games.virusSlayer.util.Circle;
import games.virusSlayer.util.Rectangle;

public class Collisions {

	public static double distanceCircleRect(Circle circle, Rectangle rectangle) {
		if (circle.getY() < (double) rectangle.getY()) {
			if (circle.getX() < (double) rectangle.getX()) {
				return Collisions.distancePointPoint(circle.getX(), circle.getY(), rectangle.getX(), rectangle.getY());
			}
			if (circle.getX() > (double) (rectangle.getX() + rectangle.getWidth())) {
				return Collisions.distancePointPoint(circle.getX(), circle.getY(), rectangle.getX() + rectangle.getWidth(), rectangle.getY());
			}
			return (double) rectangle.getY() - circle.getY();
		}
		if (circle.getY() > (double) (rectangle.getY() + rectangle.getHight())) {
			if (circle.getX() < (double) rectangle.getX()) {
				return Collisions.distancePointPoint(circle.getX(), circle.getY(), rectangle.getX(), rectangle.getY() + rectangle.getHight());
			}
			if (circle.getX() > (double) (rectangle.getX() + rectangle.getWidth())) {
				return Collisions.distancePointPoint(circle.getX(), circle.getY(), rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHight());
			}
			return circle.getY() - (double) (rectangle.getY() + rectangle.getHight());
		}
		if (circle.getX() < (double) rectangle.getX()) {
			return (double) rectangle.getX() - circle.getX();
		}
		if (circle.getX() > (double) (rectangle.getX() + rectangle.getWidth())) {
			return circle.getX() - (double) (rectangle.getX() + rectangle.getWidth());
		}
		return 0.0;
	}

	public static double distancePointPoint(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
	}

	public static double distanceCircleCircle(Circle circle1, Circle circle2) {
		double dist = Math.sqrt(Math.pow(circle1.getY() - circle2.getY(), 2.0) + Math.pow(circle1.getX() - circle2.getX(), 2.0));
		if (dist <= (double) (circle1.getRadius() + circle2.getRadius())) {
			return 0.0;
		}
		return dist;
	}

	public static boolean collisionCircleRect(Circle circle, Rectangle rectangle) {
		return Collisions.distanceCircleRect(circle, rectangle) <= (double)circle.getRadius();
	}

	public static boolean collisionCircleCircle(Circle circle1, Circle circle2) {
		return Collisions.distanceCircleCircle(circle1, circle2) <= 0.0;
	}

}
