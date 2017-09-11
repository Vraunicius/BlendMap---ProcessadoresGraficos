package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class Camera {
    private Vector3f position = new Vector3f(0,0,2);
    private Vector3f up = new Vector3f(0, 1, 0);
    private Vector3f target = new Vector3f(0,0,0);
    private float fov = (float)Math.toRadians(60);
    private float near = 0.1f;
    private float far = 1000.0f;
    private Vector3f direction = new Vector3f(0,0,-1);
    private Vector3f vertical = new Vector3f(1,0,0);

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getUp() {
        return up;
    }

    public Vector3f getTarget() {
        return target;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getNear() {
        return near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float far) {
        this.far = far;
    }

    public Camera strafeLeft(float distance){
        Vector3f aux = new Vector3f();
        up.cross(direction,aux);
        position.add(aux.mul(distance));
        return this;
    }
    public Camera strafeRight(float distance){
        return strafeLeft(distance < 0 ? distance : distance*-1);
    }

    public Camera strafeUp(float distance){
        Vector3f aux = new Vector3f();
        vertical.cross(direction,aux);
        position.add(aux.mul(distance));
        return this;
    }

    public Camera strafeDown(float distance){
        return strafeUp(distance < 0 ? distance : distance*-1);
    }


    private float getAspect() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        long window = glfwGetCurrentContext();
        glfwGetWindowSize(window, w, h);
        return w.get() / (float)h.get();
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(position, target, up);
    }

    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().perspective(fov, getAspect(), near, far);
    }
}
