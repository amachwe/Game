package com.fef.agent.framework.drawable;

import com.fef.agent.framework.driver.TargetDriver;

public interface Drawable {

    public final Integer[] COLOR_BLACK = {0,0,0};
    public void draw(TargetDriver driver);
}
