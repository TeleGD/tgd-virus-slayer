package games.virusSlayer.menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppFont;
import app.AppLoader;

import games.virusSlayer.World;

public class PauseMenu extends BasicGameState {

	private int ID;

	private Font font1;

	private String nom = "Pause";
	private String[] items = { "Continuer", "Quitter" };

	public int nbrOption = this.items.length;
	private Image background;

	public String[] getItems() {
		return this.items;
	}

	int selection = 0;

	public PauseMenu(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		container.setShowFPS(false);

		background = AppLoader.loadPicture("/images/virusSlayer/background2.png");

		font1 = AppLoader.loadFont("Kalinga", AppFont.BOLD, 26); // TODO: trouver une fonte équivalente
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_UP)) {
			selection = 1-selection;
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			switch (selection) {
				case 0: {
					((World) game.getState(0)).setState(2);
					game.enterState(0 /* World */, new FadeOutTransition(), new FadeInTransition());
					break;
				}
				case 1: {
					((World) game.getState(0)).setState(0);
					game.enterState(2 /* MainMenu */, new FadeOutTransition(), new FadeInTransition());
					break;
				}
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			((World) game.getState(0)).setState(0);
			game.enterState(2 /* MainMenu */, new FadeOutTransition(), new FadeInTransition());
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g2) {
		g2.drawImage(background, 0, 0);

		g2.setColor(Color.blue);
		g2.setFont(font1);

		g2.drawString(this.nom, 200, 200);

		g2.setColor(Color.white);

		for (int i = 0; i < nbrOption; i++) {
			g2.drawString(this.items[i], 300, 280 + 50 * i);
		}

		g2.drawString(">>", 230, 280 + 50 * selection);
	}

}
