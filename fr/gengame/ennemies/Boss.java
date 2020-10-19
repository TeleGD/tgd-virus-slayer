package fr.gengame.ennemies;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import fr.gengame.ennemies.Ennemies;
import fr.gengame.main.WorldGenGame;
import fr.gengame.menus.CreditsMenu;
import fr.gengame.util.Rectangle;
import fr.gengame.util.Seg;

public class Boss extends Ennemies implements Rectangle {

	private boolean init = true;
	private int hauteur;
	private int largeur;
	public Image skinFaible;
	public Image skinFort;
	public int vieFaible;

	public Boss(Seg character, int vie, int hauteur, int largeur) {
		super(character, vie);
		this.hauteur = hauteur;
		this.largeur = largeur;
		try {
			this.skinFaible = new Image("Images/boss3.png");
			this.skin = this.skinFort = new Image("Images/boss4.png");
		} catch (SlickException e2) {
			e2.printStackTrace();
		}
	}

	@Override
	public void update(int delta) {
		if (this.init) {
			this.setDirectionX(delta);
			this.setDirectionY(delta);
			this.init = false;
		}
	}

	@Override
	public void render(Graphics g2) {
		g2.drawImage(this.skin, (float) this.character.org.x, (float) this.character.org.y);
	}

	@Override
	public void die() {
		WorldGenGame.game.enterState(CreditsMenu.ID);
	}

	@Override
	public int getY() {
		return (int) this.character.org.y;
	}

	@Override
	public int getX() {
		return (int) this.character.org.x;
	}

	@Override
	public int getWidth() {
		return this.largeur;
	}

	@Override
	public int getHight() {
		return this.hauteur;
	}

}
