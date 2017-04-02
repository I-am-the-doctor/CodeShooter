package kgr.engine.graph;

import static kgr.cubeshooter.Constants.MOUSE_SENSITIVITY;
import kgr.cubeshooter.world.Physics;
import kgr.cubeshooter.world.Player;
import kgr.cubeshooter.world.entities.ITickable;
import kgr.engine.Input;
import org.joml.Vector3f;


/**
 * Helper class for the camera.
 *
 * @author Val
 */
public class Camera implements ITickable {
	
	protected final Player player;
	
	protected float xRotation;

    public Camera(Player player, float xRotation) {
		this.player = player;
		this.xRotation = xRotation;
    }

	@Override
	public void tick(Physics physics, Input input, float milliseconds) {
        if (input.isRightButtonPressed()) {
			xRotation += input.getDisplVec().x * MOUSE_SENSITIVITY;
        }
	}

    public Vector3f getPosition() {
		return player.getPosition();
    }

    public Vector3f getRotation() {
		Vector3f rotation = new Vector3f(player.getRotation());
		rotation.x = xRotation;
		return rotation;
    }
}