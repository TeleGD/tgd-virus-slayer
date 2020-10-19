package fr.gengame.menus;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.gengame.menus.MainMenu;

public class ConfirmMenu extends BasicGameState {

	static TrueTypeFont font9;
	static TrueTypeFont font6;
	public static int ID;
	private String nom = "Etes-vous sûr(e)?";
	private String[] items = new String[]{"Non", "Oui"};
	public int nbrOption = this.items.length;
	private Image background;
	static StateBasedGame game;
	static GameContainer container;
	int selection = 0;

	static {
		ID = 8;
	}

	public String[] getItems() {
		return this.items;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		ConfirmMenu.game = game;
		ConfirmMenu.container = container;
		container.setShowFPS(false);
		this.background = new Image("Images/background2.png");
		Font titre9Font = new Font("Courant", 1, 22);
		font9 = new TrueTypeFont(titre9Font, false);
		Font titre6Font = new Font("Courant", 1, 16);
		font6 = new TrueTypeFont(titre6Font, false);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g2) throws SlickException {
		g2.drawImage(this.background, 0.0f, 0.0f);
		g2.setColor(Color.white);
		g2.setFont(font9);
		g2.drawString(this.nom, 200.0f, 200.0f);
		g2.setFont(font6);
		for (int i2 = 0; i2 < this.nbrOption; ++i2) {
			g2.drawString(this.items[i2], 300.0f, 280 + 50 * i2);
		}
		g2.drawString(">>", 230.0f, 280 + 50 * this.selection);
	}

	@Override
	public void keyPressed(int key, char c2) {
		switch (key) {
			case 31:
			case 208: {
				if (this.selection < this.nbrOption - 1) {
					++this.selection;
					break;
				}
				this.selection = 0;
				break;
			}
			case 44:
			case 200: {
				if (this.selection > 0) {
					--this.selection;
					break;
				}
				this.selection = this.nbrOption - 1;
				break;
			}
			case 28:
			case 57: {
				this.execOption();
				break;
			}
			case 1: {
				game.enterState(MainMenu.ID);
			}
		}
	}

	public void execOption() {
		switch (this.selection) {
			case 0: {
				game.enterState(MainMenu.ID);
				break;
			}
			case 1: {
				container.exit();
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
