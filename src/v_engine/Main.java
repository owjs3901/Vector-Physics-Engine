package v_engine;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Main {
	public static ArrayList<GameObject> list = new ArrayList<>();
	public static ArrayList<GameObject> floor = new ArrayList<>();

	public Main() {
		try {
			Display.setDisplayMode(new DisplayMode(700, 1000));
			Display.setResizable(true);
			Display.setTitle("氦磐 拱府 浚柳");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		init();

		while (!Display.isCloseRequested()) {
			Display.sync(slow ? 10 : 60);
			pollInput();
			render();
			for (GameObject o : list)
				if (!o.isFloor)
					o.loop();
			Display.update();
			Display.setTitle("氦磐 拱府 浚柳");
		}

		Display.destroy();
	}

	public void pollInput() {
		while (Mouse.next()) {
			if (Mouse.isButtonDown(0)) {
				System.out.println("MOUSE CLICKED: " + Mouse.getEventX() + ", " + Mouse.getEventY());
			}
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				System.out.println(Keyboard.getEventKey());
				int mX = Mouse.getX(), mY = Mouse.getY();
				switch (Keyboard.getEventKey()) {
				case Keyboard.KEY_RETURN:
					if (isClear()) {
						System.out.println("按眉 积己");
						list.add(new GameObject(mX, mY));
					}
					break;
				case Keyboard.KEY_S:
					slow = true;
					break;
				case Keyboard.KEY_1:
					if (isClear()) {
						System.out.println("按眉 积己");
						GameObject ob = new GameObject(mX, mY);
						ob.v.set(-10, 0);
						list.add(ob);
					}
					break;
				case Keyboard.KEY_2:
					if (isClear()) {
						System.out.println("按眉 积己");
						GameObject ob = new GameObject(mX, mY);
						ob.v.set(-20, 0);
						list.add(ob);
					}
					break;
				case Keyboard.KEY_3:
					if (isClear()) {
						System.out.println("按眉 积己");
						GameObject ob = new GameObject(mX, mY);
						ob.v.set(-30, 0);
						list.add(ob);
					}
					break;
				case Keyboard.KEY_4:
					if (isClear()) {
						System.out.println("按眉 积己");
						GameObject ob = new GameObject(mX, mY);
						ob.v.set(10, 0);
						list.add(ob);
					}
					break;
				case Keyboard.KEY_5:
					if (isClear()) {
						System.out.println("按眉 积己");
						GameObject ob = new GameObject(mX, mY);
						ob.v.set(20, 0);
						list.add(ob);
					}
					break;
				case Keyboard.KEY_6:
					if (isClear()) {
						System.out.println("按眉 积己");
						GameObject ob = new GameObject(mX, mY);
						ob.v.set(30, 0);
						list.add(ob);
					}
					break;
				}
			} else
				slow = false;
		}
	}

	private static boolean isClear() {
		int mX = Mouse.getX(), mY = Mouse.getY();
		int xd = mX - GameObject.size / 2;
		int yd = mY - GameObject.size / 2;
		return Main.list.stream()
				.filter(g -> ((g.pos.y <= yd && g.pos.y + GameObject.size >= yd)
						|| (g.pos.y >= yd && g.pos.y - GameObject.size <= yd))
						&& ((g.pos.x <= xd && g.pos.x + GameObject.size >= xd)
								|| (g.pos.x >= xd && g.pos.x - GameObject.size <= xd)))
				.count() == 0;
	}

	private static boolean slow = false;

	public void init() {
		GameObject obj1 = new GameObject(0, 500);
		obj1.v.set(10, 5);
		list.add(obj1);

		GameObject obj2 = new GameObject(700, 500);
		obj2.v.set(-10, 5);
		list.add(obj2);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 700, 0, 1000, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		for (GameObject o : list)
			o.draw();
		// GameObject.FLOOR.draw();
		// GL11.glBegin(GL11.GL_QUADS);
		/*
		 * GL11.glVertex2d(0, 0); GL11.glVertex2d(350, 0); GL11.glVertex2d(350,
		 * 250); GL11.glVertex2d(0, 250);
		 */
		// GL11.glEnd();
	}

	public static void main(String[] args) {
		new Main();
	}

}
