package games.virusSlayer.menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import app.AppFont;
import app.AppLoader;

public class CreditsMenu extends BasicGameState {

	private int ID;

	private Font font5;
	private Font font6;

	private String nom = "Credits :";
	private String[] items = {
			"Hverdung",
			"saliwan",
			"doubi125",
			"yoruhi",
			"BusyAnt",
			"Fidilare",
			"GizL",
			"",
			"Club TeleGame Design,      Telecom Nancy",
			"",
			"Retour Menu" };

	public int nbrOption = items.length;

	public String[] getItems() {
		return this.items;
	}

	private Image background;

	int selection = nbrOption - 1;

	public CreditsMenu(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	public void init(GameContainer container, StateBasedGame game) {
		container.setShowFPS(false);
		background = AppLoader.loadPicture("/images/virusSlayer/background2.png");

		font5 = AppLoader.loadFont("Courant", AppFont.BOLD, 26); // TODO: trouver une fonte équivalente

		font6 = AppLoader.loadFont("Courant", AppFont.BOLD, 16); // TODO: trouver une fonte équivalente
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_SPACE) || input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.enterState(2 /* MainMenu */);
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.drawImage(background, 0, 0);

		g.setColor(Color.blue);
		g.setFont(font5);

		g.drawString(this.nom, 200, 80);

		g.setColor(Color.white);
		g.setFont(font6);

		for (int i = 0; i < nbrOption; i++) {
			g.drawString(this.items[i], 150, 180 + 30 * i);
		}

		g.drawString(">>", 80, 180 + 30 * selection);
	}

}
