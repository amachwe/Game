package com.fef.agent.framework.driver;

import com.fef.agent.framework.drawable.Drawable;

public interface TargetDriver {

    public Long removeObject(String layer, Long id);
    public Drawable getObject(String layer, Long id);
    public void updateObjectPosition(String layer, Long id, Integer x, Integer y);
    public void addRectangle(String layer, Long id, Integer x, Integer y, Integer w, Integer h, Integer r, Integer g, Integer b);
    public void addLabel(String layer, Long id, Integer x, Integer y, String label);
    public void removeLayer(String layer);
    public void removeAllLayers();

}
