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

public class CreditsMenu extends BasicGameState {

	public static int ID = 9;
	static TrueTypeFont font5;
	static TrueTypeFont font6;
	private String nom = "Credits :";
	private String[] items = new String[]{"Hverdung", "saliwan", "doubi125", "yoruhi", "BusyAnt", "Fidilare", "GizL", "", "Club TeleGame Design,	Telecom Nancy", "", "Retour Menu"};
	public int nbrOption = this.items.length;
	private Image background;
	static GameContainer container;
	static StateBasedGame game;
	int selection = this.nbrOption - 1;

	public String[] getItems() {
		return this.items;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		CreditsMenu.container = container;
		container.setShowFPS(false);
		CreditsMenu.game = game;
		this.background = new Image("Images/background2.png");
		Font titre5Font = new Font("Courant", 1, 26);
		font5 = new TrueTypeFont(titre5Font, false);
		Font titre6Font = new Font("Courant", 1, 16);
		font6 = new TrueTypeFont(titre6Font, false);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g2) throws SlickException {
		g2.drawImage(this.background, 0.0f, 0.0f);
		g2.setColor(Color.blue);
		g2.setFont(font5);
		g2.drawString(this.nom, 200.0f, 80.0f);
		g2.setColor(Color.white);
		g2.setFont(font6);
		for (int i2 = 0; i2 < this.nbrOption; ++i2) {
			g2.drawString(this.items[i2], 150.0f, 180 + 30 * i2);
		}
		g2.drawString(">>", 80.0f, 180 + 30 * this.selection);
	}

	@Override
	public void keyPressed(int key, char c2) {
		switch (key) {
			case 28:
			case 57: {
				game.enterState(MainMenu.ID);
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
