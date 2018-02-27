package com.fef.agent.framework.driver;

import com.fef.agent.framework.drawable.Drawable;
import com.fef.agent.framework.drawable.Rectangle;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDriver implements TargetDriver {

    private static final String KEY_X = "X", KEY_Y = "Y", KEY_W = "W", KEY_H = "H", KEY_COLOR_B = "B", KEY_COLOR_G = "G", KEY_COLOR_R = "R", KEY_TYPE = "type", KEY_NAME = "name";

    public enum TYPES {RECT, IMAGE, LABEL};

    private final MongoClient mc;
    private final MongoDatabase db;

    public MongoDriver(String host, Long port, String dbName) {
        mc = new MongoClient(host + ":" + port);
        db = mc.getDatabase(dbName);
    }

    private static final Document makeId(Long id) {
        Document doc = new Document();
        doc.put("_id", id);
        return doc;
    }

    private final MongoCollection<Document> getCollection(String collName) {
        MongoCollection<Document> coll = db.getCollection(collName);
        if (coll != null) {
            return coll;
        } else {
            throw new IllegalStateException("Layer not found.");
        }
    }

    public void removeLayer(String layer)
    {
        getCollection(layer).drop();
    }

    public void removeAllLayers()
    {
        for(Document doc : getCollection("layers").find())
        {
            removeLayer(doc.getString("_id"));
        }
    }
    public Long removeObject(String layer, Long id) {
        getCollection(layer).deleteOne(makeId(id));
        return id;
    }

    public Drawable getObject(String layer, Long id) {
        Document doc = getCollection(layer).find(makeId(id)).first();
        if(doc.getString(KEY_TYPE).equals(TYPES.RECT.toString()))
        {
            return Rectangle.makeRectangle(layer, doc.getLong("_id"), doc.getInteger(KEY_X),doc.getInteger(KEY_Y), doc.getInteger(KEY_W), doc.getInteger(KEY_H), doc.getInteger(KEY_COLOR_R), doc.getInteger(KEY_COLOR_G), doc.getInteger(KEY_COLOR_B));
        }
        else
        {
            throw new IllegalStateException("Illegal object type.");
        }
    }

    public void updateObjectPosition(String layer, Long id, Integer x, Integer y) {
        Bson idDoc = makeId(id);
        MongoCollection<Document> coll = getCollection(layer);
        Document doc = coll.find(idDoc).first();
        doc.put(KEY_X, x);
        doc.put(KEY_Y, y);

        coll.updateOne(idDoc, doc);
    }

    public void addRectangle(String layer, Long id, Integer x, Integer y, Integer w, Integer h, Integer r, Integer g, Integer b) {
        Document doc = makeId(id);
        doc.put(KEY_X, x);
        doc.put(KEY_Y, y);
        doc.put(KEY_H, h);
        doc.put(KEY_W, w);
        doc.put(KEY_TYPE, TYPES.RECT.toString());
        doc.put(KEY_COLOR_R, r);
        doc.put(KEY_COLOR_G, g);
        doc.put(KEY_COLOR_B, b);
        getCollection(layer).insertOne(doc);
    }
    public void addLabel(String layer, Long id, Integer x, Integer y, String label) {
        Document doc = makeId(id);
        doc.put(KEY_X, x);
        doc.put(KEY_Y, y);
        doc.put(KEY_NAME, label);
        doc.put(KEY_TYPE, TYPES.LABEL);
        getCollection(layer).insertOne(doc);
    }
}
