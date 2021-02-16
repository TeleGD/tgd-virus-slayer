package games.virusSlayer.ennemies;

import org.newdawn.slick.Graphics;

import app.AppLoader;

import games.virusSlayer.World;
import games.virusSlayer.ennemies.Ennemies;
import games.virusSlayer.util.Circle;
import games.virusSlayer.util.Collisions;
import games.virusSlayer.util.Seg;
import games.virusSlayer.util.Vec;

public class Sbires extends Ennemies implements Circle {

	private World world;
	private boolean dejaCol;
	private int radius;
	private int angle;
	private int timeAngle;

	public Sbires(World world, Seg character, int vie, int radius) {
		super(character, vie);
		this.world = world;
		this.radius = radius;
		this.world.ennemiesTab.add(this);
		this.world.ennemiesInit.add(this);
		this.skin = AppLoader.loadPicture("/images/virusSlayer/enemy3.png");
		this.speed = new Vec(0.2, 0.2);
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
		return this.radius;
	}

	public void nextCollision() {
		if (this.getDirectionX() == this.getDirectionY()) {
			this.setDirectionX(-1 * this.getDirectionX());
		} else {
			this.setDirectionY(-1 * this.getDirectionY());
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if (Collisions.collisionCircleRect(this, this.world.bosse)) {
			if (this.dejaCol) {
				this.setDirectionX(-1 * this.getDirectionX());
				this.setDirectionY(-1 * this.getDirectionY());
				this.dejaCol = false;
			} else {
				this.nextCollision();
				this.dejaCol = true;
			}
		} else {
			this.dejaCol = false;
		}
	}

	@Override
	public void render(Graphics g2) {
		if (this.timeAngle >= 1) {
			this.angle += 2;
			this.timeAngle = 0;
		} else {
			++this.timeAngle;
		}
		this.skin.setRotation(this.angle);
		g2.drawImage(this.skin, (float) (this.character.org.x - 32.0), (float) (this.character.org.y - 32.0));
	}

}
