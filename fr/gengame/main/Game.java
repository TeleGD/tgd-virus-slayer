package fr.gengame.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.gengame.main.WorldGenGame;
import fr.gengame.menus.ConfirmMenu;
import fr.gengame.menus.CreditsMenu;
import fr.gengame.menus.GOMenu;
import fr.gengame.menus.MainMenu;
import fr.gengame.menus.PauseMenu;
import fr.gengame.menus.WelcomeMenu;

public class Game extends StateBasedGame {

	public static void main(String[] args) throws SlickException {
		new AppGameContainer(new Game(), 800, 600, true).start();
	}

	public Game() {
		super("GeneralGame");
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new WelcomeMenu());
		this.addState(new MainMenu());
		this.addState(new WorldGenGame());
		this.addState(new GOMenu());
		this.addState(new PauseMenu());
		this.addState(new ConfirmMenu());
		this.addState(new CreditsMenu());
	}

}
