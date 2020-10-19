package fr.gengame.menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.gengame.menus.MainMenu;

public class WelcomeMenu extends BasicGameState {

	public static int ID = 12;
	private String nom = "Menu Accueil";
	private String[] items = new String[0];
	private Image welcome;
	public int nbrOption = this.items.length;
	static GameContainer container;
	static StateBasedGame game;
	int selection = 0;
	int cpt = 0;
	boolean disp = true;

	public String[] getItems() {
		return this.items;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.welcome = new Image("Images/welcome.png");
		WelcomeMenu.container = container;
		container.setShowFPS(false);
		WelcomeMenu.game = game;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (this.cpt > 30) {
			this.cpt = 0;
			this.disp = !this.disp;
		}
		++this.cpt;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g2) throws SlickException {
		g2.drawImage(this.welcome, 0.0f, 0.0f);
		g2.setColor(Color.black);
		if (this.disp) {
			g2.drawString(">                                <", 240.0f, 552.0f);
		}
	}

	@Override
	public void keyPressed(int key, char c2) {
		switch (key) {
			case 28:
			case 57: {
				game.enterState(MainMenu.ID);
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
