package com.agos.ml;

public class Configuration {

    private static Configuration _instance;

    public boolean withText = true;


    public static Configuration getInstance() {
        if (_instance == null) { //if there is no instance available... create new one
            _instance = new Configuration();
        }

        return _instance;
    }

}
