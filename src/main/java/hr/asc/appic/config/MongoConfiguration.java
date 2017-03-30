package hr.asc.appic.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "appic-wishout";
    }

    @Override
    public Mongo mongo() throws Exception {
        //MongoClientURI uri = new MongoClientURI("mongodb://appic:appicjezakon123@ds147377.mlab.com:47377/appic-wishout");
        //return new MongoClient(uri);
        return new MongoClient("127.0.0.1", 27017);
    }
}

