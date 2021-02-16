package games.virusSlayer.character;

import org.newdawn.slick.Graphics;

import app.AppLoader;

import games.virusSlayer.character.Entity;
import games.virusSlayer.util.Circle;
import games.virusSlayer.util.Seg;
import games.virusSlayer.util.Vec;

public class Personnage extends Entity implements Circle {

	private float angle;

	public Personnage(Seg character, int vie) {
		super(character, vie);
		this.directionX = 0;
		this.directionY = 0;
		this.skin = AppLoader.loadPicture("/images/virusSlayer/character.png");
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
		return 32;
	}

	public void update(int delta) {
		this.move(delta);
		if (this.character.org.x > 768.0) {
			this.character.org.x = 768.0;
		}
		if (this.character.org.x < 32.0) {
			this.character.org.x = 32.0;
		}
		if (this.character.org.y > 568.0) {
			this.character.org.y = 568.0;
		}
		if (this.character.org.y < 92.0) {
			this.character.org.y = 92.0;
		}
	}

	public void render(Graphics g2, Vec mouse) {
		this.angle = (float) (Math.asin((mouse.y - this.character.org.y) / Vec.sub(mouse, this.character.org).length()) * 180.0 / Math.PI);
		if (mouse.x < this.character.org.x) {
			this.skin.setRotation(180.0f - this.angle);
		} else {
			this.skin.setRotation(this.angle);
		}
		g2.drawImage(this.skin, (float) (this.character.org.x - 43.0), (float) (this.character.org.y - 32.0));
	}

}
