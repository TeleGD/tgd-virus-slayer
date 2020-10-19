package fr.gengame.util;

import org.newdawn.slick.Graphics;

import fr.gengame.util.Vec;

public class Seg {

	public Vec org;
	public Vec dir;

	public Seg(Vec pos, Vec dir) {
		this.org = pos;
		this.dir = dir;
	}

	public double length() {
		return this.dir.length();
	}

	public boolean isVertical() {
		return this.dir.x == 0.0;
	}

	public boolean isHorizontal() {
		return this.dir.y == 0.0;
	}

	public void setDir(Vec dir) {
		this.dir = dir;
	}

	public Vec interVorH(Seg s1, Seg s2) {
		if (Vec.isColin(s1.dir, s2.dir)) {
			return null;
		}
		if (s1.isHorizontal() && s2.isVertical()) {
			return new Vec(s2.org.x, s1.org.y);
		}
		if (s1.isVertical() && s2.isHorizontal()) {
			return new Vec(s1.org.x, s2.org.y);
		}
		return null;
	}

	public static Vec getIntersection(Seg segAM, Seg segBN) {
		double x1 = segAM.getOrigin().x;
		double x2 = segAM.getDest().x;
		double y3 = segBN.getOrigin().y;
		double y4 = segBN.getDest().y;
		double y1 = segAM.getOrigin().y;
		double y2 = segAM.getDest().y;
		double x3 = segBN.getOrigin().x;
		double x4 = segBN.getDest().x;
		double d2 = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (d2 == 0.0) {
			return null;
		}
		double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d2;
		double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d2;
		Vec p = new Vec(xi, yi);
		if (xi < Math.min(x1, x2) || xi > Math.max(x1, x2)) {
			return null;
		}
		if (xi < Math.min(x3, x4) || xi > Math.max(x3, x4)) {
			return null;
		}
		return p;
	}

	public static Vec getIntersectionLine(Seg segAM, Seg segBN) {
		double x1 = segAM.getOrigin().x;
		double x2 = segAM.getDest().x;
		double y3 = segBN.getOrigin().y;
		double y4 = segBN.getDest().y;
		double y1 = segAM.getOrigin().y;
		double y2 = segAM.getDest().y;
		double x3 = segBN.getOrigin().x;
		double x4 = segBN.getDest().x;
		double d2 = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (d2 == 0.0) {
			return null;
		}
		double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d2;
		double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d2;
		Vec p = new Vec(xi, yi);
		if (Vec.scal(Vec.sub(p, segAM.org), segAM.dir) < 0.0) {
			return null;
		}
		if (Vec.scal(Vec.sub(p, segBN.org), segBN.dir) < 0.0 || Vec.scal(Vec.sub(p, segBN.getDest()), segBN.dir) > 0.0) {
			return null;
		}
		return p;
	}

	public Vec proj(Vec M) {
		return Vec.sum(this.org, this.dir.proj(Vec.sub(M, this.org)));
	}

	public Vec getOrigin() {
		return this.org;
	}

	public Vec getDest() {
		return Vec.sum(this.org, this.dir);
	}

	public void draw(Graphics g2) {
		g2.drawLine((float) this.org.x, (float) this.org.y, (float) this.getDest().x, (float) this.getDest().y);
	}

}
