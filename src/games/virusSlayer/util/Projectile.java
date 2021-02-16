package games.virusSlayer.util;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import games.virusSlayer.character.Entity;
import games.virusSlayer.World;
import games.virusSlayer.util.Circle;
import games.virusSlayer.util.Seg;
import games.virusSlayer.util.Vec;

public class Projectile extends Entity implements Circle {

	private World world;
	private Vec nxtPoint;
	private int pointActuel = 1;
	private ArrayList<Vec> points;
	private double speedP = 0.33;

	public Projectile(World world, Seg character, int vie, ArrayList<Vec> pts) {
		super(character, vie);
		this.world = world;
		this.points = pts;
		this.nextPoint();
		this.setDir();
		this.character.org.x = this.world.perso.character.org.x;
		this.character.org.y = this.world.perso.character.org.y;
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
			this.world.projectiles.remove(this);
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
