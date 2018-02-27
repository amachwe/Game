package com.fef.agent.framework.drawable;

import com.fef.agent.framework.driver.TargetDriver;

public class Rectangle implements Drawable {

    private final Integer x;
    private final Integer y;
    private final Integer w;
    private final Integer h;
    private final Integer r, g, b;
    private final String layer;
    private long id;


    private Rectangle(String layer, Integer x, Integer y, Integer w, Integer h, Integer r, Integer g, Integer b)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.r = r;
        this.g = g;
        this.b = b;
        this.layer = layer;
    }

    public static Rectangle makeRectangle(String layer, Integer x, Integer y, Integer w, Integer h)
    {
        Rectangle rect = new Rectangle(layer, x, y, w, h, Drawable.COLOR_BLACK[0], Drawable.COLOR_BLACK[1], Drawable.COLOR_BLACK[2]);
        rect.id = (long) rect.hashCode();

        return rect;
    }

    public static Rectangle makeRectangle(String layer, Integer x, Integer y, Integer w, Integer h, Integer r, Integer g, Integer b)
    {
        Rectangle rect = new Rectangle(layer, x, y, w, h, r,g,b);
        rect.id = (long) rect.hashCode();

        return rect;
    }

    public static Rectangle makeRectangle(String layer, long id, Integer x, Integer y, Integer w, Integer h, Integer r, Integer g, Integer b)
    {
        Rectangle rect = new Rectangle(layer, x, y, w, h, r,g,b);
        rect.id = id;

        return rect;
    }

    @Override
    public int hashCode()
    {
        int hash = 31;
        hash += 7 * this.getLayer().hashCode();
        hash += 7 * this.getX().hashCode();
        hash += 7 * this.getY().hashCode();
        hash += 7 * this.getH().hashCode();
        hash += 7 * this.getW().hashCode();

        return hash;
    }
    public void draw(TargetDriver driver) {
        driver.addRectangle(this.getLayer(), (long)this.hashCode(), this.getX(), this.getY(), this.getW(), this.getH(), this.r, this.g, this.b);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getW() {
        return w;
    }

    public Integer getH() {
        return h;
    }

    public int[] getRgb() {
        return new int[]{this.r, this.g, this.b};
    }

    public String getLayer() {
        return layer;
    }

    public long getId() {
        return id;
    }
}
