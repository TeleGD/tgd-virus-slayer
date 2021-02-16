package games.virusSlayer.menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;

public class WelcomeMenu extends BasicGameState {

	private int ID;

	private String[] items = {};
	private Image welcome;
	public int nbrOption = this.items.length;

	public String[] getItems() {
		return this.items;
	}

	private int selection = 0;

	private int cpt = 0;
	private boolean disp = true;

	public WelcomeMenu(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	public void init(GameContainer container, StateBasedGame game) {
		welcome = AppLoader.loadPicture("/images/virusSlayer/welcome.png");
		container.setShowFPS(false);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			game.enterState(2 /* MainMenu */, new FadeOutTransition(), new FadeInTransition());
		}
		if (cpt > 30) {
			cpt = 0;
			disp = !disp;
		}
		cpt++;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g2) {
		g2.drawImage(welcome, 0, 0);
		g2.setColor(Color.black);
		if (disp)
			g2.drawString(">                                <", 240, 552);
	}

}
