package fr.gengame.util;

import java.util.ArrayList;

import fr.gengame.util.Seg;
import fr.gengame.util.Vec;

public class RayTracing {

	public Seg rayTrace(Seg seg, ArrayList<Seg> obstacles) {
		int count = 0;
		Vec result = null;
		Seg resultObstacle = null;
		double minDist = 10000.0;
		for (Seg obstacle: obstacles) {
			Vec intersect = Seg.getIntersectionLine(seg, obstacle);
			if (intersect != null) {
				++count;
			}
			if (result == null) {
				result = intersect;
				resultObstacle = obstacle;
				if (intersect == null) continue;
				minDist = Vec.dist(intersect, seg.org);
				continue;
			}
			if (intersect == null || !(Vec.dist(intersect, seg.org) < minDist)) continue;
			minDist = Vec.dist(intersect, seg.org);
			result = intersect;
			resultObstacle = obstacle;
		}
		if (result == null) {
			return null;
		}
		Vec newDir = new Vec(-resultObstacle.dir.y, resultObstacle.dir.x);
		if (Vec.scal(newDir, seg.dir) > 0.0) {
			newDir = newDir.mul(-1.0);
		}
		Seg resultSeg = new Seg(Vec.sum(result, newDir.getVecWithNorm(0.01)), seg.dir);
		return this.getBounceDir(resultSeg, newDir);
	}

	public Seg getBounceDir(Seg collision, Vec normal) {
		Vec proj = normal.proj(collision.dir);
		Vec normProj = Vec.sub(collision.dir, proj);
		Vec BounceDir = Vec.sub(normProj, proj);
		collision.setDir(BounceDir);
		return collision;
	}

	public ArrayList<Vec> rayTraceBounces(Seg seg, ArrayList<Seg> obstacles, int maxBounces) {
		ArrayList<Vec> RTB = new ArrayList<Vec>();
		RTB.add(seg.org);
		Seg currentPos = seg;
		for (int i2 = 1; i2 <= maxBounces; ++i2) {
			Seg colPos = this.rayTrace(currentPos, obstacles);
			if (colPos == null) {
				return RTB;
			}
			RTB.add(colPos.org);
			currentPos = colPos;
		}
		return RTB;
	}

}
