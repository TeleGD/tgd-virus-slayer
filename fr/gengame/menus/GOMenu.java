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

import fr.gengame.main.WorldGenGame;
import fr.gengame.menus.CreditsMenu;
import fr.gengame.menus.MainMenu;

public class GOMenu extends BasicGameState {

	public static int ID = 3;
	static TrueTypeFont font3;
	static TrueTypeFont font4;
	private String nom = "GAME OVER";
	private String[] items = new String[]{"Rejouer", "Retour au menu"};
	public int nbrOption = this.items.length;
	private Image background;
	static StateBasedGame game;
	static GameContainer container;
	int selection = 0;

	public String[] getItems() {
		return this.items;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		GOMenu.game = game;
		GOMenu.container = container;
		container.setShowFPS(false);
		this.background = new Image("Images/background2.png");
		Font titre3Font = new Font("Goudy Stout", 1, 30);
		font3 = new TrueTypeFont(titre3Font, false);
		Font titre4Font = new Font("Kristen ITC", 1, 20);
		font4 = new TrueTypeFont(titre4Font, false);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g2) throws SlickException {
		g2.drawImage(this.background, 0.0f, 0.0f);
		g2.setColor(Color.red);
		g2.setFont(font3);
		g2.drawString(this.nom, 200.0f, 200.0f);
		g2.setColor(Color.white);
		g2.setFont(font4);
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
				break;
			}
			case 46: {
				game.enterState(CreditsMenu.ID);
			}
		}
	}

	public void execOption() {
		switch (this.selection) {
			case 0: {
				game.enterState(WorldGenGame.ID);
				break;
			}
			case 1: {
				game.enterState(MainMenu.ID);
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
