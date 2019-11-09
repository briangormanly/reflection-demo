package com.gormanly.demo.mscs722;

import com.fdflib.persistence.database.DatabaseUtil;
import com.fdflib.service.FdfServices;
import com.fdflib.service.impl.FdfCommonServices;
import com.fdflib.util.FdfSettings;
import com.gormanly.demo.mscs722.model.Car;
import com.gormanly.demo.mscs722.model.CarMake;
import com.gormanly.demo.mscs722.model.Driver;
import com.sun.media.sound.FFT;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionDemo {

    public static void main(String[] args) {
        System.out.println("Reflection Demo!"); // Display the string.

        // initialize 4dfLib
        setupFdfLib();

        // create a diver the normal way
        Driver brian = FdfCommonServices.getEntityCurrentById(Driver.class, 1);
        if(brian == null) {
            brian = new Driver();
            brian.firstName = "Brian";
            brian.lastName = "Gormanly";
            brian.phoneNumber = "867-5309";
            brian = FdfCommonServices.save(Driver.class, brian).current;
        }

        // create a car the normal way
        Car boxster = FdfCommonServices.getEntityCurrentById(Car.class, 1);
        if(boxster == null) {
            boxster = new Car();
            boxster.color = "Seal Gray";
            boxster.make = CarMake.PORSCHE;
            boxster.model = "Boxster S";
            boxster.year = 2001;
            boxster.isInNeedOfRepair = false;
            boxster.name = "Lonely in the winter";
            boxster.currentDriverId = brian.id;
            boxster = FdfCommonServices.save(Car.class, boxster).current;
        }

        try {
            Car rCar = Car.class.newInstance();


            /*
            Method[] carActions = rCar.getClass().getMethods();
            for(Method action: carActions) {
                System.out.println("a method: " + action.getName());
                if(action.getName().equals("beep")) {
                    try {
                        action.invoke(boxster, "toot");
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            */

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    private static void setupFdfLib() {

        // get the 4dflib settings singleton
        FdfSettings fdfSettings = FdfSettings.getInstance();

        // MySQL settings
        fdfSettings.PERSISTENCE = DatabaseUtil.DatabaseType.MYSQL;
        fdfSettings.DB_PROTOCOL = DatabaseUtil.DatabaseProtocol.JDBC_MYSQL;

        // Database encoding
        fdfSettings.DB_ENCODING = DatabaseUtil.DatabaseEncoding.UTF8;

        // Application Database name
        fdfSettings.DB_NAME = "reflectionDemo";

        // Database host
        fdfSettings.DB_HOST = "localhost";

        // Database user information
        fdfSettings.DB_USER = "reflectionDemo";
        fdfSettings.DB_PASSWORD = "reflectionDemo";

        // root user settings are only required for initial database creation.  Once the database is created you
        // should remove this information
        fdfSettings.DB_ROOT_USER = "root";
        fdfSettings.DB_ROOT_PASSWORD = "";



        // Create a array that will hold the classes that make up our 4df data model
        List<Class> myModel = new ArrayList<>();

        // Add our 2 classes
        myModel.add(Driver.class);
        myModel.add(Car.class);

        // call the initialization of library!
        FdfServices.initializeFdfDataModel(myModel);

    }
}
