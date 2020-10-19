package fr.gengame.ennemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import fr.gengame.character.Entity;
import fr.gengame.util.Seg;

public class Ennemies extends Entity {

	private boolean init = true;

	public Ennemies(Seg character, int vie) {
		super(character, vie);
	}

	public void update(int delta) {
		if (this.init) {
			this.setDirectionX(1);
			this.setDirectionY(1);
			this.init = false;
		}
		if (this.getCharacter().getOrigin().x > 768.0) {
			this.setDirectionX(-1);
		}
		if (this.getCharacter().getOrigin().y > 568.0) {
			this.setDirectionY(-1);
		}
		if (this.getCharacter().getOrigin().y < 92.0) {
			this.setDirectionY(1);
		}
		if (this.getCharacter().getOrigin().x < 32.0) {
			this.setDirectionX(1);
		}
		this.move(delta);
	}

	public void render(Graphics g2) {
		g2.setColor(Color.red);
		g2.fillOval((float) this.character.org.x - 32.0f, (float) this.character.org.y - 32.0f, 42.0f, 42.0f);
	}

}
