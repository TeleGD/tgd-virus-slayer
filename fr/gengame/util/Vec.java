package fr.gengame.util;

public class Vec {

	public double x;
	public double y;

	public Vec(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double length() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public double lengthSquared() {
		return this.x * this.x + this.y * this.y;
	}

	public Vec getVecWithNorm(double length) {
		return this.mul(length / this.length());
	}

	public Vec mul(double f2) {
		return new Vec(this.x * f2, this.y * f2);
	}

	public static double scal(Vec v1, Vec v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}

	public static Vec sum(Vec v1, Vec v2) {
		return new Vec(v1.x + v2.x, v1.y + v2.y);
	}

	public Vec proj(Vec AM) {
		double f2 = Vec.scal(this, AM) / this.lengthSquared();
		return this.mul(f2);
	}

	public Vec normalize() {
		return this.mul(1.0 / this.length());
	}

	public static boolean isColin(Vec v1, Vec v2) {
		if (v1.y == 0.0) {
			return v2.y == 0.0;
		}
		if (v2.y == 0.0) {
			return false;
		}
		if (v1.x == 0.0) {
			return v2.x == 0.0;
		}
		if (v2.x == 0.0) {
			return false;
		}
		return v1.y / v2.y == v1.x / v2.x;
	}

	public static Vec sub(Vec v1, Vec v2) {
		return new Vec(v1.x - v2.x, v1.y - v2.y);
	}

	public static double dist(Vec A, Vec B) {
		return Vec.sub(B, A).length();
	}

}
