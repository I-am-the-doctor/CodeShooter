/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kgr.cubeshooter.client;

import static kgr.cubeshooter.Constants.MOUSE_SENSITIVITY;
import kgr.cubeshooter.world.Physics;
import kgr.cubeshooter.world.Player;
import kgr.engine.Input;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 *
 * @author Benjamin
 */
public class ClientPlayer extends Player {
	
	private static ClientPlayer instance;

	/**
	 *
	 * @param position
	 * @param angle
	 */
	public ClientPlayer(Vector3f position, float angle) {
		super(position, angle);
		
		if (instance == null) {
			instance = this;
		} else {
			throw new UnsupportedOperationException("Cannot instanciate two different ClientPlayer's");
		}
	}
    
	public ClientPlayer() {
		super(new Vector3f(), 0);
		
		if (instance == null) {
			instance = this;
		} else {
			throw new UnsupportedOperationException("Cannot instanciate two different ClientPlayer's");
		}
	}
	
	@Override
	public void tick(Physics physics, Input input, float milliseconds) {
        if (input.isRightButtonPressed()) {
            Vector2f rotVec = input.getDisplVec();
			orientation.setRotationAngle(orientation.getRotationAngle() + rotVec.y * MOUSE_SENSITIVITY);
        }
		
		double rotationAngleRadians = Math.toRadians(orientation.getRotationAngle());
		if (input.isKeyPressed(GLFW.GLFW_KEY_W)) {
			boundingBox.getPosition().add((float) Math.sin(-rotationAngleRadians) * -MOVE_SPEED, 
										  0,
										  (float) Math.cos(-rotationAngleRadians) * -MOVE_SPEED);
		}
		if (input.isKeyPressed(GLFW.GLFW_KEY_A)) {
			boundingBox.getPosition().add((float) Math.cos(rotationAngleRadians) * -MOVE_SPEED,
										  0,
										  (float) Math.sin(rotationAngleRadians) * -MOVE_SPEED);
		}
		if (input.isKeyPressed(GLFW.GLFW_KEY_S)) {
			boundingBox.getPosition().add((float) Math.sin(-rotationAngleRadians) * MOVE_SPEED,
										  0,
										  (float) Math.cos(-rotationAngleRadians) * MOVE_SPEED);
		}
		if (input.isKeyPressed(GLFW.GLFW_KEY_D)) {
			boundingBox.getPosition().add((float) Math.cos(rotationAngleRadians) * MOVE_SPEED,
										  0,
										  (float) Math.sin(rotationAngleRadians) * MOVE_SPEED);
		}
		
        if (input.isKeyPressed(GLFW.GLFW_KEY_Z) || input.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            boundingBox.getPosition().y -= MOVE_SPEED;
        }
        if (input.isKeyPressed(GLFW.GLFW_KEY_X) || input.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            boundingBox.getPosition().y += MOVE_SPEED;
        }
	}
	
	/**
	 *
	 * @return
	 */
	public static ClientPlayer instance() {
		return instance;
	}
}
