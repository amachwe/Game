package com.fef.agent.framework;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {

    private static final String DEFAULT_HOST = "localhost";
    private static final Long DEFAULT_PORT = 27017L;
    private static final String DB_NAME = "game";
    private static final String URL = DEFAULT_HOST+":"+DEFAULT_PORT;
    public static void main(String...args)
    {
        MongoClient mc = new MongoClient(URL);
        MongoDatabase db = mc.getDatabase(DB_NAME);
        MongoCollection<Document> coll = db.getCollection("layers");

        for(Document d : coll.find())
        {
            System.out.println(d);
        }


    }
}
