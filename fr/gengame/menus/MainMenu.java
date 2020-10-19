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
import fr.gengame.menus.ConfirmMenu;
import fr.gengame.menus.CreditsMenu;

public class MainMenu extends BasicGameState {

	public static int ID = 2;
	static TrueTypeFont font1;
	private String nom = "Menu Principal";
	private String[] items = new String[]{"Jouer", "Scores (Prochaine MàJ)", "Aide (DLC)", "Quitter"};
	public int nbrOption = this.items.length;
	private Image background;
	static GameContainer container;
	static StateBasedGame game;
	int selection = 0;

	public String[] getItems() {
		return this.items;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		MainMenu.container = container;
		container.setShowFPS(false);
		MainMenu.game = game;
		this.background = new Image("Images/background2.png");
		Font titre1Font = new Font("Kalinga", 1, 26);
		font1 = new TrueTypeFont(titre1Font, false);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g2) throws SlickException {
		g2.drawImage(this.background, 0.0f, 0.0f);
		g2.setColor(Color.red);
		g2.setFont(font1);
		g2.drawString(this.nom, 200.0f, 220.0f);
		g2.setColor(Color.white);
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
				game.enterState(ConfirmMenu.ID);
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
			case 3: {
				game.enterState(ConfirmMenu.ID);
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
