package com.example.sqlitedbms;

public class Model {
    int _id;
    String _name;
    String _phone_number;

    public Model() {   }

    public Model(String name, String _phone_number){
        this._name = name;
        this._phone_number = _phone_number;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getPhoneNumber(){
        return this._phone_number;
    }

    public void setPhoneNumber(String number){
        this._phone_number = number;
    }
}