package v_engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GameObject {
	public static int size = 50;
	boolean isFloor = false;
	// ��ġ
	public Vector pos = new Vector();
	// �ӵ�
	public Vector v = new Vector();
	// ź�����
	public double restitution = 0.5;
	// ����
	public double mass = 1;

	// private VertexArrayObject vao;

	public GameObject(int x, int y) {
		// vao=new VertexArrayObject(vertices, indi);
		// GL11.glScalef(SIZE, SIZE, SIZE);
		pos.x = x - size / 2;
		pos.y = y - size / 2;
		// GL11.glTranslated(x, y, 0);
	}

	/**
	 * ���ӵ� ����
	 */
	Vector a = new Vector(0, -1);

	
	public void loop() {
		Main.list.stream().forEach(obj -> {
			processCollision(obj);
		});

//		processCollisionWithFloor();

		if(isFloor)return;
		if(a.length()==0||pos.y<=0){
			v.set(0,0);
			a.set(0,0);
			if(pos.y<0) pos.y=0;
			isFloor=true;
		}
		else{
			v.add(a);
			pos.add(v);
		}
	}
	
	/*private void positionalCorrection(GameObject other, Vector normal, double p) {
		if (normal.x == 0 && normal.y == -1) {
			Vector v = new Vector(normal);

			pos.add(v.scale(-(p + 1)));
		}
	}*/

	/*private void processCollisionWithFloor() {
		if (pos.y < 0) {
			Vector normalVec = new Vector(0, -1);

			// relativeV(���ӵ�) = other ��ġ���� - this ��ġ����
			Vector relativeV = new Vector(v).negate();
			double normal = relativeV.innerProduct(normalVec);

			if (normal > 0)
				return;

			// j�� �浹�� ũ�⸦ �ǹ�
			double j = -(1 + restitution) * normal;
			j /= 2 / mass;

			// ���� ���� ������ �Ŀ� �����Ͽ� �浹�� ����
			Vector impulse = normalVec.scale(j);
			v.add(-1 / mass * impulse.x, -1 / mass * impulse.y);

//			positionalCorrection(null, normalVec, -pos.y);
		}
	}*/

	private void processCollision(GameObject other) {
		if (isCollision(other)) {
			Vector normalVec;
				
				
			double x_overlap = Math.abs(other.pos.x - pos.x);
			double y_overlap = Math.abs(other.pos.y - pos.y);
			//���� ��ü�� ���� ���(�̹� ������ ���� ����� ���)
			if(other.isFloor){
				if (x_overlap > y_overlap){
					//������ ������ ƨ��
					a.x*=-1;
					v.x*=-1;
					pos.x=pos.x+(v.x>0?15:-15);
					return;
				}
				else{
					//������ ������ ����
					v.set(0,0);
					a.set(0,0);
					int a=(int) (pos.y/size);
					boolean b= (pos.y%size)>0;
					pos.y=a*size+(b?size:0);
					isFloor=true;
					return;
				}
			}
			

			// �浹�� ���� ��������
			if (x_overlap > y_overlap) {
				if (other.pos.x >= pos.x) {
					normalVec = new Vector(1, 0);
				} else {
					normalVec = new Vector(-1, 0);
				}
			} else {
				if (pos.y < other.pos.y) {
					normalVec = new Vector(0, 1);
				} else {
					normalVec = new Vector(0, -1);
				}
			}

			// relativeV(���ӵ�) = other ��ġ���� - this ��ġ����
			Vector relativeV = new Vector(other.v);
			relativeV.add(new Vector(v).negate());

			double normal = relativeV.innerProduct(normalVec);

			if (normal > 0)
				return;

			// �� ��ü �浹�� ź������� ���� ���� ź������� �̿��ؼ� �浹�� ����: �������� ���ؼ���
			double e = Math.min(restitution, other.restitution);

			// j�� �浹�� ũ�⸦ �ǹ�
			double j = -(1 + e) * normal;
			j /= 1 / mass + 1 / other.mass;

			// ���� ���� ������ �Ŀ� �����Ͽ� �浹�� ����
			Vector impulse = normalVec.scale(j);
			v.add(-1 / mass * impulse.x, -1 / mass * impulse.y);
			other.v.add(1 / other.mass * impulse.x, 1 / other.mass * impulse.y);

		}
	}

	public boolean isCollision(GameObject other) {
		if (other == this)
			return false;
		if (pos.x + size < other.pos.x || pos.x > other.pos.x + size)
			return false;
		if (pos.y + size < other.pos.y || pos.y > other.pos.y + size)
			return false;

		return true;
	}

	public void draw() {
		GL11.glColor3f(1f, 1f, 1);
		GL11.glRectd(pos.x, pos.y, pos.x + size, pos.y + size);
	}

	static {
	}
}
