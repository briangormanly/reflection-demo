package com.gormanly.demo.mscs722;

import com.fdflib.persistence.database.DatabaseUtil;
import com.fdflib.service.FdfServices;
import com.fdflib.service.impl.FdfCommonServices;
import com.fdflib.util.FdfSettings;
import com.gormanly.demo.mscs722.model.Car;
import com.gormanly.demo.mscs722.model.CarMake;
import com.gormanly.demo.mscs722.model.Driver;

import java.lang.reflect.Field;
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

        // create another car
        Car pilot = FdfCommonServices.getEntityCurrentById(Car.class, 2);
        if(pilot == null) {
            pilot = new Car();
            pilot.make = CarMake.HONDA;
            pilot.model = "Pilot";
            pilot.year = 2019;
            pilot.name = "Family Hauler";
            pilot.color = "Maroon";
            pilot.currentDriverId = brian.id;
            FdfCommonServices.save(Car.class, pilot);
        }

        // get all the cars
        List<Car> allCars = FdfCommonServices.getAllCurrent(Car.class);

        // use reflection to decompose the Car class and the objects at runtime
        if(allCars != null && allCars.size() > 0) {

            Class workingClass1 = allCars.getClass();
            System.out.println("[Reflection: get class] -> " + workingClass1.getName());

            Class workingClass2 = allCars.get(0).getClass();
            System.out.println("[Reflection: get class] -> " + workingClass2.getName());

            for (Field field : workingClass2.getFields()) {
                System.out.println("[Reflection: field] Name -> " + field.getName());
                System.out.println("[Reflection: field] Type -> " + field.getType());
                System.out.println("[Reflection: field] Number of Annotations -> " + field.getAnnotations().length);
            }


        }




    }


    private static void setupFdfLib() {

        // get the 4dflib settings singleton
        FdfSettings fdfSettings = FdfSettings.getInstance();

        // PostgreSQL settings
        fdfSettings.PERSISTENCE = DatabaseUtil.DatabaseType.POSTGRES;
        fdfSettings.DB_PROTOCOL = DatabaseUtil.DatabaseProtocol.JDBC_POSTGRES;

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
        fdfSettings.DB_ROOT_USER = "postgres";
        fdfSettings.DB_ROOT_PASSWORD = "postgres";



        // Create a array that will hold the classes that make up our 4df data model
        List<Class> myModel = new ArrayList<>();

        // Add our 2 classes
        myModel.add(Driver.class);
        myModel.add(Car.class);

        // call the initialization of library!
        FdfServices.initializeFdfDataModel(myModel);

    }
}
