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

public class MainMenu extends BasicGameState {

	private int ID;

	private Font font1;

	private String nom = "Menu Principal";
	private String[] items = { "Jouer", "Scores (Prochaine MàJ)", "Aide (DLC)", "Quitter" };

	public int nbrOption = this.items.length;

	public String[] getItems() {
		return this.items;
	}

	private Image background;

	private int selection = 0;

	public MainMenu(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		background = AppLoader.loadPicture("/images/virusSlayer/background2.png");
		container.setShowFPS(false);

		font1 = AppLoader.loadFont("Kalinga", AppFont.BOLD, 26); // TODO: trouver une fonte équivalente

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			if (selection < nbrOption - 1)
				selection++;
			else
				selection = 0;
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			if (selection > 0)
				selection--;
			else
				selection = nbrOption - 1;
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			switch (selection) {
				case 0: {
					game.enterState(0 /* World */, new FadeOutTransition(), new FadeInTransition());
					break;
				}
				case 3: {
					game.enterState(1 /* ConfirmMenu */, new FadeOutTransition(), new FadeInTransition());
					break;
				}
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.enterState(1 /* ConfirmMenu */, new FadeOutTransition(), new FadeInTransition());
		}
		if (input.isKeyPressed(Input.KEY_C)) {
			game.enterState(6 /* CreditsMenu */, new FadeOutTransition(), new FadeInTransition());
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g2)
			{
		g2.drawImage(background, 0, 0);

		g2.setColor(Color.red);
		g2.setFont(font1);
		g2.drawString(this.nom, 200, 220);

		g2.setColor(Color.white);

		for (int i = 0; i < nbrOption; i++) {
			g2.drawString(this.items[i], 300, 280 + 50 * i);
		}
		g2.drawString(">>", 230, 280 + 50 * selection);
	}

}
