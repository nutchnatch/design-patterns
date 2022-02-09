package com.activemesa.creational.facade;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

public class Buffer {

    private char[] characters;
    private int lineWidth;

    public Buffer(int lineHeight, int lineWidth) {
        this.characters = new char[lineHeight];
        this.lineWidth = lineWidth;
    }

    public char charAt(int x, int y) {
        return characters[y*lineWidth + x];
    }
}

class ViewPort {

    private final Buffer buffer;
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;

    public ViewPort(Buffer buffer, int width, int height, int offsetX, int offsetY) {

        this.buffer = buffer;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public char charAt(int x, int y) {
        return buffer.charAt(x + offsetX, y + offsetY);
    }
}

class Console {
    private List<ViewPort> viewPorts = new ArrayList<>();
    int width, height;

    public Console(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addViewPort(ViewPort viewPort) {
        viewPorts.add(viewPort);
    }

    public static Console newConsole(int width, int height) {
        final Buffer buffer = new Buffer(width, height);
        final ViewPort viewPort = new ViewPort(buffer, width, height, 0, 0);
        final Console console = new Console(width, height);
        console.addViewPort(viewPort);
        return console;
    }

    public void render() {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                for(ViewPort vp : viewPorts) {
                    System.out.println(vp.charAt(x, y));
                }
                System.out.println();
            }
        }
    }
}

/**
 * Facade actually provides a simple api over a set of subsystem
 * Here the set of subsystem is composed by Buffer, ViewPort, Console
 * We just want a simple api to solve these components
 * */
class Demo {
    public static void main(String[] args) {
        final Buffer buffer = new Buffer(30, 20);
        final ViewPort viewPort = new ViewPort(buffer, 30, 20, 0, 0);
        final Console console = new Console(30, 20);
        console.addViewPort(viewPort);
        console.render();

        /**
         * second version of console with a simple interface
         */
        final Console console2 = Console.newConsole(30, 20);
        console2.render();
    }
}
