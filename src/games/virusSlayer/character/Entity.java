package games.virusSlayer.character;

import org.newdawn.slick.Image;

import games.virusSlayer.util.Seg;
import games.virusSlayer.util.Vec;

public abstract class Entity {

	public Seg character;
	public Vec speed;
	public int vie;
	protected int directionX;
	protected int directionY;
	public Image skin;
	public double posIX;
	public double posIY;

	public Seg getCharacter() {
		return this.character;
	}

	public void setCharacter(Seg character) {
		this.character = character;
	}

	public Vec getSpeed() {
		return this.speed;
	}

	public void setSpeed(Vec speed) {
		this.speed = speed;
	}

	public int getVie() {
		return this.vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public int getDirectionX() {
		return this.directionX;
	}

	public void setDirectionX(int directionX) {
		this.directionX = directionX;
	}

	public int getDirectionY() {
		return this.directionY;
	}

	public void setDirectionY(int directionY) {
		this.directionY = directionY;
	}

	public Entity(Seg character, int vie) {
		this.character = character;
		this.vie = vie;
		this.speed = new Vec(0.33, 0.33);
		this.posIX = character.org.x;
		this.posIY = character.org.y;
	}

	public void move(int delta) {
		this.character.org = Vec.sum(this.character.org, new Vec(this.speed.x * (double) delta * (double) this.directionX, this.speed.y * (double) delta * (double) this.directionY));
	}

	public void loseLife() {
		if (this.vie > 0) {
			--this.vie;
		}
	}

	public Seg getPosAndDir() {
		return this.character;
	}

}
