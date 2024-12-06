package database.model;

import database.model.DatabaseConnection;

import java.sql.Connection;

public abstract class BaseModel {
    protected Connection connection;

    public BaseModel(){
        connection= DatabaseConnection.getInstance().getConnection();
    }
}
