package fr.gengame.util;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import fr.gengame.character.Entity;
import fr.gengame.main.WorldGenGame;
import fr.gengame.util.Circle;
import fr.gengame.util.Seg;
import fr.gengame.util.Vec;

public class Projectile extends Entity implements Circle {

	static int idTotal = 0;
	public int id = idTotal++;
	private Vec nxtPoint;
	private int pointActuel = 1;
	private ArrayList<Vec> points;
	private double speedP = 0.33;

	public Projectile(Seg character, int vie, ArrayList<Vec> pts) {
		super(character, vie);
		this.points = pts;
		this.nextPoint();
		this.setDir();
		this.character.org.x = WorldGenGame.perso.character.org.x;
		this.character.org.y = WorldGenGame.perso.character.org.y;
		for (int i2 = 0; i2 < this.points.size(); ++i2) {}
	}

	public void update(GameContainer container, int delta) {
		if (Math.abs(this.nxtPoint.x - this.character.org.x) <= 5.0 && Math.abs(this.nxtPoint.y - this.character.org.y) <= 5.0) {
			this.nextPoint();
			this.setDir();
		}
		this.move(delta);
	}

	public void nextPoint() {
		try {
			this.nxtPoint = this.points.get(this.pointActuel);
		} catch (IndexOutOfBoundsException e2) {
			WorldGenGame.projectiles.remove(this);
		}
		++this.pointActuel;
	}

	public void setDir() {
		this.speed.x = Math.abs((this.nxtPoint.x - this.character.org.x) * this.speedP / Vec.sub(this.nxtPoint, this.character.org).length());
		this.speed.y = Math.abs((this.nxtPoint.y - this.character.org.y) * this.speedP / Vec.sub(this.nxtPoint, this.character.org).length());
		if (this.nxtPoint.x - this.character.org.x > 0.0) {
			this.directionX = 1;
			this.directionY = this.nxtPoint.y - this.character.org.y > 0.0 ? 1 : -1;
		} else {
			this.directionX = -1;
			this.directionY = this.nxtPoint.y - this.character.org.y > 0.0 ? 1 : -1;
		}
	}

	public void render(GameContainer container, Graphics g2) {
		g2.setColor(Color.red);
		g2.fillOval((float) this.character.org.x, (float) this.character.org.y, 10.0f, 10.0f);
	}

	@Override
	public double getX() {
		return this.getCharacter().getOrigin().x;
	}

	@Override
	public double getY() {
		return this.getCharacter().getOrigin().y;
	}

	@Override
	public int getRadius() {
		return 5;
	}

}
