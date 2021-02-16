package games.virusSlayer;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.virusSlayer.character.Personnage;
import games.virusSlayer.ennemies.Boss;
import games.virusSlayer.ennemies.Sbires;
import games.virusSlayer.util.Collisions;
import games.virusSlayer.util.Projectile;
import games.virusSlayer.util.RayTracing;
import games.virusSlayer.util.Seg;
import games.virusSlayer.util.Vec;

public class World extends BasicGameState {

	private static Image background;
	public static int heightW;
	public static int widthW;

	private int ID;
	private int state;

	public Personnage perso;
	public ArrayList<Sbires> ennemiesTab;
	public ArrayList<Sbires> ennemiesInit;
	public Sbires ennemie1;
	public Sbires ennemie2;
	public Boss bosse;
	private RayTracing ray;
	private Vec mouse;
	ArrayList<Seg> obstacles;
	int timerReset = 0;
	int reset = 360;
	int round;
	public ArrayList<Projectile> projectiles;
	public Integer[] scoreList;
	Seg seg;
	public boolean[] buttons;

	static {
		World.background = AppLoader.loadPicture("/images/virusSlayer/background.png");
		heightW = 600;
		widthW = 800;
	}

	public World(int ID) {
		this.ID = ID;
		this.state = 0;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
		if (this.state == 0) {
			this.play(container, game);
		} else if (this.state == 2) {
			this.resume(container, game);
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			this.pause(container, game);
		} else if (this.state == 3) {
			this.stop(container, game);
			this.state = 0; // TODO: remove
		}
	}

	public void reset() {
		int i2;
		this.perso.character.org = new Vec(400.0, 550.0);
		this.bosse.skin = this.bosse.skinFort;
		++this.round;
		for (i2 = 0; i2 < ennemiesInit.size(); ++i2) {
			if (ennemiesTab.contains(ennemiesInit.get(i2))) continue;
			ennemiesTab.add(ennemiesInit.get(i2));
		}
		for (i2 = 0; i2 < ennemiesTab.size(); ++i2) {
			this.ennemiesTab.get((int) i2).character.org.x = this.ennemiesTab.get((int) i2).posIX;
			this.ennemiesTab.get((int) i2).character.org.y = this.ennemiesTab.get((int) i2).posIY;
			this.ennemiesTab.get((int) i2).speed = new Vec(0.2 + 0.04 * (double) this.round, 0.2 + 0.04 * (double) this.round);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g2) {
		/* Méthode exécutée environ 60 fois par seconde */
		int i2;
		g2.drawImage(World.background, 0.0f, 0.0f);
		perso.render(g2, this.mouse);
		this.seg = new Seg(this.perso.getPosAndDir().org, Vec.sub(this.mouse, this.perso.getPosAndDir().org));
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
	public void update(GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			this.setState(1);
			game.enterState(4 /* PauseMenu */);
		}
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
						this.bosse.skin = this.bosse.skinFaible;
						this.bosse.vieFaible = bosse.getVie();
						continue;
					} catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
				}
				if (!Collisions.collisionCircleRect(projectiles.get(i2), bosse)) continue;
				if (ennemiesTab.size() == 0) {
					bosse.loseLife();
					if (bosse.getVie() == 0) {
						game.enterState(6 /* CreditsMenu */);
					}
				}
				projectiles.remove(i2);
			}
			for (i2 = 0; i2 < ennemiesTab.size(); ++i2) {
				ennemiesTab.get(i2).update(delta);
				if (!Collisions.collisionCircleCircle(perso, ennemiesTab.get(i2))) continue;
				this.reset();
				for (j = 0; j < ennemiesTab.size(); ++j) {
					this.ennemiesTab.get((int) j).speed = new Vec(0.2, 0.2);
				}
				this.round = 0;
				bosse.setVie(75);
				game.enterState(3 /* GOMenu */);
			}
			if (Collisions.collisionCircleRect(perso, bosse)) {
				this.reset();
				for (int j2 = 0; j2 < ennemiesTab.size(); ++j2) {
					this.ennemiesTab.get((int) j2).speed = new Vec(0.2, 0.2);
				}
				this.round = 0;
				bosse.setVie(75);
				game.enterState(3 /* GOMenu */);
			}
			if (bosse.getVie() <= this.bosse.vieFaible - 20) {
				this.timerReset = 0;
				this.reset();
				this.reset();
				this.bosse.vieFaible = bosse.getVie();
			}
		} catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
	}

	public void play(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		this.ray = new RayTracing();
		perso = new Personnage(new Seg(new Vec(400.0, 550.0), new Vec(0.0, 100.0)), 1);
		ennemiesTab = new ArrayList<Sbires>();
		ennemiesInit = new ArrayList<Sbires>();
		projectiles = new ArrayList<Projectile>();
		scoreList = new Integer[]{0, 0, 0, 0, 0};
		new Sbires(this, new Seg(new Vec(0.0, 0.0), new Vec(0.0, 100.0)), 1, 32);
		new Sbires(this, new Seg(new Vec(779.0, 0.0), new Vec(0.0, 100.0)), 1, 32);
		new Sbires(this, new Seg(new Vec(0.0, 300.0), new Vec(0.0, 100.0)), 1, 32);
		new Sbires(this, new Seg(new Vec(779.0, 300.0), new Vec(0.0, 100.0)), 1, 32);
		bosse = new Boss(new Seg(new Vec(255.0, 60.0), new Vec(0.0, 100.0)), 75, 231, 290);
		this.mouse = new Vec(0.0, 0.0);
		buttons = new boolean[4];
		this.obstacles = new ArrayList<Seg>();
		this.obstacles.add(new Seg(new Vec(0.0, 60.0), new Vec(0.0, 600.0)));
		this.obstacles.add(new Seg(new Vec(600.0, 300.0), new Vec(120.0, -150.0)));
		this.obstacles.add(new Seg(new Vec(200.0, 300.0), new Vec(-120.0, -150.0)));
		this.obstacles.add(new Seg(new Vec(0.0, 60.0), new Vec(800.0, 0.0)));
		this.obstacles.add(new Seg(new Vec(0.0, 600.0), new Vec(800.0, 0.0)));
		this.obstacles.add(new Seg(new Vec(800.0, 60.0), new Vec(0.0, 600.0)));
		this.obstacles.add(new Seg(new Vec(300.0, 400.0), new Vec(200.0, 0.0)));
	}

	public void pause(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
	}

	public void resume(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
	}

	public void stop(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	@Override
	public void keyReleased(int key, char c2) {
		switch (key) {
			case 44: {
				this.buttons[0] = false;
				if (perso.getDirectionY() != -1) break;
				if (!buttons[1]) {
					perso.setDirectionY(0);
					break;
				}
				perso.setDirectionY(1);
				break;
			}
			case 31: {
				this.buttons[1] = false;
				if (perso.getDirectionY() != 1) break;
				if (!buttons[0]) {
					perso.setDirectionY(0);
					break;
				}
				perso.setDirectionY(-1);
				break;
			}
			case 16: {
				this.buttons[2] = false;
				if (perso.getDirectionX() != -1) break;
				if (!buttons[3]) {
					perso.setDirectionX(0);
					break;
				}
				perso.setDirectionX(1);
				break;
			}
			case 32: {
				this.buttons[3] = false;
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
				this.buttons[0] = true;
				perso.setDirectionY(-1);
				break;
			}
			case 31: {
				this.buttons[1] = true;
				perso.setDirectionY(1);
				break;
			}
			case 16: {
				this.buttons[2] = true;
				perso.setDirectionX(-1);
				break;
			}
			case 32: {
				this.buttons[3] = true;
				perso.setDirectionX(1);
				break;
			}
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		if (button == 0) {
			projectiles.add(new Projectile(this, this.seg, 1, this.ray.rayTraceBounces(this.seg, this.obstacles, 3)));
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		this.mouse.x = newx;
		this.mouse.y = newy;
	}

}
