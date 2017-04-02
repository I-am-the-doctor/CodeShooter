package kgr.engine.graph;

import kgr.cubeshooter.world.Physics;
import kgr.cubeshooter.world.Player;
import kgr.engine.Input;
import org.joml.Vector3f;


/**
 * Camera that orbits, in a certain radius, around a point.
 *
 * @author Val
 */
public class OrbitingCamera extends Camera
{
	private float playerDistance;
	
	private final float minPlayerDistance = 2;
	
	private final float maxPlayerDistance = 50;

    public OrbitingCamera(Player player, float xRotation, float playerDistance) {
		super(player, xRotation);
		
		this.playerDistance = playerDistance;
    }

	@Override
    public Vector3f getPosition() {
		Vector3f position = new Vector3f(player.getPosition());
		
		double xRotationRadians = Math.toRadians(xRotation);
		double yRotationRadians = Math.toRadians(player.getRotation().y);
		float distance = playerDistance * (float) Math.cos(xRotationRadians);
		position.add((float) -Math.sin(yRotationRadians) * distance,
					 (float) Math.sin(xRotationRadians) * playerDistance,
					 (float) Math.cos(yRotationRadians) * distance);
		return position;
    }

	@Override
    public Vector3f getRotation() {
		Vector3f rotation = new Vector3f(player.getRotation());
		rotation.x = xRotation;
		return rotation;
    }
	
	@Override
	public void tick(Physics physics, Input input, float milliseconds) {
		super.tick(physics, input, milliseconds);
		
		playerDistance -= input.getScrollVec().y;
		if (playerDistance < minPlayerDistance) {
			playerDistance = minPlayerDistance;
		}
		if (playerDistance > maxPlayerDistance) {
			playerDistance = maxPlayerDistance;
		}
	}
	
	public float getPlayerDistance() {
		return playerDistance;
	}
}
