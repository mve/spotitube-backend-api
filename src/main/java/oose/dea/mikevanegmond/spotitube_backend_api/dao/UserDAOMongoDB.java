package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.exceptions.InvalidTokenException;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@Default
public class UserDAOMongoDB implements IUserDAO {

    /**
     * Get a user by username from MongoDB database.
     *
     * @param username
     * @return User
     */
    @Override
    public User getUserByUsername(String username) {
        User user = new User();

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("spotitube");
        MongoCollection<Document> collection = database.getCollection("users");

        Document query = new Document("username", username);
        MongoCursor result = collection.find(query).iterator();

        if (!result.hasNext()) {
            // No user with that username found.
            return user;
        }

        Document firstResult = (Document) result.next();

        user.setId(firstResult.getInteger("id"));
        user.setUsername(firstResult.getString("username"));
        user.setPassword(firstResult.getString("password"));
        user.setToken(firstResult.getString("token"));

        return user;
    }

    @Override
    public User getUserByToken(String token) {
        User user = new User();

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("spotitube");
        MongoCollection<Document> collection = database.getCollection("users");

        Document query = new Document("token", token);
        MongoCursor result = collection.find(query).iterator();

        if (!result.hasNext()) {
            // No user with that username found.
            return user;
        }

        Document firstResult = (Document) result.next();

        user.setId(firstResult.getInteger("id"));
        user.setUsername(firstResult.getString("username"));
        user.setPassword(firstResult.getString("password"));
        user.setToken(firstResult.getString("token"));

        return user;
    }

    @Override
    public boolean update(User user) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("spotitube");
        MongoCollection<Document> collection = database.getCollection("users");

        Bson filter = eq("id", user.getId());
        Bson updateOperation = set("token", user.getToken());
        UpdateResult updateResult = collection.updateOne(filter, updateOperation);

        return updateResult.getModifiedCount() > 0;
    }

}
