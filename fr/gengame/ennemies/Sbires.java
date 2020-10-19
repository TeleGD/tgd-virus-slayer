package fr.gengame.ennemies;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import fr.gengame.ennemies.Ennemies;
import fr.gengame.main.WorldGenGame;
import fr.gengame.util.Circle;
import fr.gengame.util.Collisions;
import fr.gengame.util.Seg;
import fr.gengame.util.Vec;

public class Sbires extends Ennemies implements Circle {

	private boolean dejaCol;
	int radius;
	int angle;
	int timeAngle;

	public Sbires(Seg character, int vie, int radius) {
		super(character, vie);
		this.radius = radius;
		WorldGenGame.ennemiesTab.add(this);
		WorldGenGame.ennemiesInit.add(this);
		try {
			this.skin = new Image("Images/enemy3.png");
		} catch (SlickException e2) {
			e2.printStackTrace();
		}
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
		if (Collisions.collisionCircleRect(this, WorldGenGame.bosse)) {
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
