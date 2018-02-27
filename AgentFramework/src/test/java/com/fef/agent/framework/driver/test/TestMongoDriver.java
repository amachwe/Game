package com.fef.agent.framework.driver.test;

import com.fef.agent.framework.drawable.Drawable;
import com.fef.agent.framework.drawable.Rectangle;
import com.fef.agent.framework.driver.MongoDriver;
import com.fef.agent.framework.driver.TargetDriver;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestMongoDriver {

    private final TargetDriver mDriver = new MongoDriver("localhost", 27017l, "game");

    @Test
    public void testMongoDriver()
    {
        mDriver.removeLayer("layer0");

        Rectangle rect = Rectangle.makeRectangle("layer0", 100, 100, 100, 50, 100, 100, 100);
        rect.draw(mDriver);

        assertTrue(rect.getId() != 0);

        Drawable resp = mDriver.getObject("layer0", rect.getId());

        assertTrue(resp instanceof Rectangle);

    }
}
