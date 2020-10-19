package fr.gengame.main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.gengame.character.Personnage;
import fr.gengame.ennemies.Boss;
import fr.gengame.ennemies.Sbires;
import fr.gengame.menus.GOMenu;
import fr.gengame.menus.PauseMenu;
import fr.gengame.util.Collisions;
import fr.gengame.util.Projectile;
import fr.gengame.util.RayTracing;
import fr.gengame.util.Seg;
import fr.gengame.util.Vec;
import java.util.ArrayList;

public class WorldGenGame extends BasicGameState {

	public static int ID = 1;
	public static Personnage perso;
	public static ArrayList<Sbires> ennemiesTab;
	public static ArrayList<Sbires> ennemiesInit;
	public static Sbires ennemie1;
	public static Sbires ennemie2;
	public static Boss bosse;
	private GameContainer container;
	public static StateBasedGame game;
	private RayTracing ray = new RayTracing();
	private Vec mouse;
	private Image background;
	ArrayList<Seg> obstacles = new ArrayList();
	int timerReset = 0;
	int reset = 360;
	int round;
	public static ArrayList<Projectile> projectiles;
	public static Integer[] scoreList;
	Seg seg;
	public static int heightW;
	public static int widthW;
	public static boolean[] buttons;

	static {
		ennemiesTab = new ArrayList();
		ennemiesInit = new ArrayList();
		projectiles = new ArrayList();
		scoreList = new Integer[]{0, 0, 0, 0, 0};
		heightW = 600;
		widthW = 800;
		buttons = new boolean[4];
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		container.setShowFPS(false);
		container.setTargetFrameRate(60);
		perso = new Personnage(new Seg(new Vec(400.0, 550.0), new Vec(0.0, 100.0)), 1);
		new Sbires(new Seg(new Vec(0.0, 0.0), new Vec(0.0, 100.0)), 1, 32);
		new Sbires(new Seg(new Vec(779.0, 0.0), new Vec(0.0, 100.0)), 1, 32);
		new Sbires(new Seg(new Vec(0.0, 300.0), new Vec(0.0, 100.0)), 1, 32);
		new Sbires(new Seg(new Vec(779.0, 300.0), new Vec(0.0, 100.0)), 1, 32);
		bosse = new Boss(new Seg(new Vec(255.0, 60.0), new Vec(0.0, 100.0)), 75, 231, 290);
		this.mouse = new Vec(0.0, 0.0);
		WorldGenGame.game = game;
		this.background = new Image("Images/background.png");
		this.obstacles.add(new Seg(new Vec(0.0, 60.0), new Vec(0.0, 600.0)));
		this.obstacles.add(new Seg(new Vec(600.0, 300.0), new Vec(120.0, -150.0)));
		this.obstacles.add(new Seg(new Vec(200.0, 300.0), new Vec(-120.0, -150.0)));
		this.obstacles.add(new Seg(new Vec(0.0, 60.0), new Vec(800.0, 0.0)));
		this.obstacles.add(new Seg(new Vec(0.0, 600.0), new Vec(800.0, 0.0)));
		this.obstacles.add(new Seg(new Vec(800.0, 60.0), new Vec(0.0, 600.0)));
		this.obstacles.add(new Seg(new Vec(300.0, 400.0), new Vec(200.0, 0.0)));
	}

	public void reset() {
		int i2;
		WorldGenGame.perso.character.org = new Vec(400.0, 550.0);
		WorldGenGame.bosse.skin = WorldGenGame.bosse.skinFort;
		++this.round;
		for (i2 = 0; i2 < ennemiesInit.size(); ++i2) {
			if (ennemiesTab.contains(ennemiesInit.get(i2))) continue;
			ennemiesTab.add(ennemiesInit.get(i2));
		}
		for (i2 = 0; i2 < ennemiesTab.size(); ++i2) {
			WorldGenGame.ennemiesTab.get((int) i2).character.org.x = WorldGenGame.ennemiesTab.get((int) i2).posIX;
			WorldGenGame.ennemiesTab.get((int) i2).character.org.y = WorldGenGame.ennemiesTab.get((int) i2).posIY;
			WorldGenGame.ennemiesTab.get((int) i2).speed = new Vec(0.2 + 0.04 * (double) this.round, 0.2 + 0.04 * (double) this.round);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g2) throws SlickException {
		int i2;
		g2.drawImage(this.background, 0.0f, 0.0f);
		perso.render(g2, this.mouse);
		this.seg = new Seg(WorldGenGame.perso.getPosAndDir().org, Vec.sub(this.mouse, WorldGenGame.perso.getPosAndDir().org));
		g2.setColor(Color.green);
		for (int i3 = 0; i3 < this.obstacles.size(); ++i3) {
			g2.drawLine((float) this.obstacles.get((int) i3).org.x, (float) this.obstacles.get((int) i3).org.y, (float) this.obstacles.get((int) i3).getDest().x, (float) this.obstacles.get((int) i3).getDest().y);
		}
		g2.setColor(Color.red);
		ArrayList<Vec> points = this.ray.rayTraceBounces(this.seg, this.obstacles, 3);
		for (i2 = 0; i2 < points.size() - 1; ++i2) {
			g2.drawLine((float) points.get((int) i2).x, (float) points.get((int) i2).y, (float) points.get((int) (i2 + 1)).x, (float) points.get((int) (i2 + 1)).y);
		}
		bosse.render(g2);
		for (i2 = 0; i2 < projectiles.size(); ++i2) {
			projectiles.get(i2).render(container, g2);
		}
		for (i2 = 0; i2 < ennemiesTab.size(); ++i2) {
			ennemiesTab.get(i2).render(g2);
		}
		g2.setColor(Color.white);
		g2.drawString("Boss: " + bosse.getVie(), 380.0f, 27.0f);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		try {
			int j;
			int i2;
			perso.update(delta);
			bosse.update(1);
			for (i2 = 0; i2 < projectiles.size(); ++i2) {
				projectiles.get(i2).update(container, delta);
				for (j = 0; j < ennemiesTab.size(); ++j) {
					if (!Collisions.collisionCircleCircle(projectiles.get(i2), ennemiesTab.get(j))) continue;
					try {
						ennemiesTab.remove(j);
						if (ennemiesTab.size() != 0) continue;
						WorldGenGame.bosse.skin = WorldGenGame.bosse.skinFaible;
						WorldGenGame.bosse.vieFaible = bosse.getVie();
						continue;
					} catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
				}
				if (!Collisions.collisionCircleRect(projectiles.get(i2), bosse)) continue;
				if (ennemiesTab.size() == 0) {
					bosse.loseLife();
				}
				projectiles.remove(i2);
			}
			for (i2 = 0; i2 < ennemiesTab.size(); ++i2) {
				ennemiesTab.get(i2).update(delta);
				if (!Collisions.collisionCircleCircle(perso, ennemiesTab.get(i2))) continue;
				this.reset();
				for (j = 0; j < ennemiesTab.size(); ++j) {
					WorldGenGame.ennemiesTab.get((int) j).speed = new Vec(0.2, 0.2);
				}
				this.round = 0;
				bosse.setVie(75);
				game.enterState(GOMenu.ID);
			}
			if (Collisions.collisionCircleRect(perso, bosse)) {
				this.reset();
				for (int j2 = 0; j2 < ennemiesTab.size(); ++j2) {
					WorldGenGame.ennemiesTab.get((int) j2).speed = new Vec(0.2, 0.2);
				}
				this.round = 0;
				bosse.setVie(75);
				game.enterState(GOMenu.ID);
			}
			if (bosse.getVie() <= WorldGenGame.bosse.vieFaible - 20) {
				this.timerReset = 0;
				this.reset();
				this.reset();
				WorldGenGame.bosse.vieFaible = bosse.getVie();
			}
		} catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
	}

	@Override
	public void keyReleased(int key, char c2) {
		switch (key) {
			case 44: {
				WorldGenGame.buttons[0] = false;
				if (perso.getDirectionY() != -1) break;
				if (!buttons[1]) {
					perso.setDirectionY(0);
					break;
				}
				perso.setDirectionY(1);
				break;
			}
			case 31: {
				WorldGenGame.buttons[1] = false;
				if (perso.getDirectionY() != 1) break;
				if (!buttons[0]) {
					perso.setDirectionY(0);
					break;
				}
				perso.setDirectionY(-1);
				break;
			}
			case 16: {
				WorldGenGame.buttons[2] = false;
				if (perso.getDirectionX() != -1) break;
				if (!buttons[3]) {
					perso.setDirectionX(0);
					break;
				}
				perso.setDirectionX(1);
				break;
			}
			case 32: {
				WorldGenGame.buttons[3] = false;
				if (perso.getDirectionX() != 1) break;
				if (!buttons[2]) {
					perso.setDirectionX(0);
					break;
				}
				perso.setDirectionX(-1);
				break;
			}
		}
	}

	@Override
	public void keyPressed(int key, char c2) {
		switch (key) {
			case 44: {
				WorldGenGame.buttons[0] = true;
				perso.setDirectionY(-1);
				break;
			}
			case 31: {
				WorldGenGame.buttons[1] = true;
				perso.setDirectionY(1);
				break;
			}
			case 16: {
				WorldGenGame.buttons[2] = true;
				perso.setDirectionX(-1);
				break;
			}
			case 32: {
				WorldGenGame.buttons[3] = true;
				perso.setDirectionX(1);
				break;
			}
			case 42: {
				break;
			}
			case 25: {
				game.enterState(PauseMenu.ID);
				break;
			}
			case 1: {
				game.enterState(PauseMenu.ID);
			}
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		if (button == 0) {
			projectiles.add(new Projectile(this.seg, 1, this.ray.rayTraceBounces(this.seg, this.obstacles, 3)));
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		this.mouse.x = newx;
		this.mouse.y = newy;
	}

	@Override
	public int getID() {
		return ID;
	}

}
