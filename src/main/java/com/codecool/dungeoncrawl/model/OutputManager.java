package com.codecool.dungeoncrawl.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OutputManager {
    private static final List<Class> stringableClasses =
            Arrays.asList(new Class[]{String.class, Class.class, Character.class, Date.class,
            java.sql.Date.class});

    public void saveObjectAsJSON (Object object, String path, boolean append) {

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(path);
        } catch (IOException ioe) {
            LogManager.enqueueMessage(String.format("Write to file '%s' failed", path));
        }

        if (out != null) {
            try {
                out = new FileOutputStream(path, append);
            } catch (IOException ioe) {
                LogManager.enqueueMessage(String.format("Couldn't open file '%s'", path));
            }

            boolean initializer = true;

            try {
                out.write('{');
            } catch (IOException ignored) {}

            for (Field field : object.getClass().getFields()) {
                try {
                    String outString = "";
                    if (!initializer) outString = ", ";
                    else initializer = false;

                    if (field.get(object) instanceof List) {
                        out.write(String.format("%s\"%s\": [", outString, field.getName()).getBytes());
                        for (Object o : (List)field.get(object)) out.write(o.toString().getBytes());
                        out.write(']');
                    }
                    else if (stringableClasses.contains(field.getType()))
                        out.write(String.format("%s\"%s\": \"%s\"", outString, field.getName(), field.get(object)).getBytes());
                    else if (field.get(object).getClass().getPackageName().equals("com.codecool.dungeoncrawl.model")) {
                        out.write(String.format("%s\"%s\": ", outString, field.getName()).getBytes());
                        new OutputManager().saveObjectAsJSON(field.get(object), "debug/temp.json", false);
                        out.write(new FileInputStream("debug/temp.json").readAllBytes());
                    }
                    else
                        out.write(String.format("%s\"%s\": %s", outString, field.getName(), field.get(object)).getBytes());

                } catch (IllegalAccessException | IllegalArgumentException iae) {
                    LogManager.enqueueMessage(String.format("Write of field '%s' failed", field.getName()));
                    if (iae.getMessage() != null) LogManager.enqueueMessage(iae.getMessage());

                } catch (IOException ioe) {
                    LogManager.enqueueMessage(String.format("File '%s' unresponsive to write", path));
                }
            }

            for (Method method : object.getClass().getMethods()) {
                try {
                    String methodName = method.getName();
                    if (!methodName.matches("get[a-zA-Z]*")) continue;
                    if (methodName.equals("getClass") || methodName.equals("getId")) continue;
                    methodName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);

                    String outString = "";
                    if (!initializer) outString = ", ";
                    else initializer = false;


                    if (method.invoke(object) instanceof List) {
                        out.write(String.format("%s\"%s\": [", outString, method.getName()).getBytes());
                        for (Object o : (List)method.invoke(object)) out.write(o.toString().getBytes());
                        out.write(']');
                    }
                    else if (stringableClasses.contains(method.getReturnType()))
                        out.write(String.format("%s\"%s\": \"%s\"", outString, methodName, method.invoke(object)).getBytes());
                    else if (method.invoke(object).getClass().getPackageName().equals("com.codecool.dungeoncrawl.model")) {
                        out.write(String.format("%s\"%s\": ", outString, methodName).getBytes());
                        new OutputManager().saveObjectAsJSON(method.invoke(object), "debug/temp.json", false);
                        out.write(new FileInputStream("debug/temp.json").readAllBytes());
                    }
                    else
                        out.write(String.format("%s\"%s\": %s", outString, methodName, method.invoke(object)).getBytes());

                } catch (IllegalAccessException | InvocationTargetException ite) {
                    LogManager.enqueueMessage(String.format("Invocation of method '%s' failed", method.getName()));
                    if (ite.getMessage() != null) LogManager.enqueueMessage(ite.getMessage());

                } catch (IOException ioe) {
                    LogManager.enqueueMessage(String.format("File '%s' unresponsive to write", path));
                }
            }

            try {
                out.write('}');
                LogManager.enqueueMessage(String.format("Object with hash %s successfully encoded", object.hashCode()));
                out.close();
            } catch (IOException ioe) {
                LogManager.enqueueMessage(String.format("File '%s' couldn't be closed", path));
            }
        }

        LogManager.dumpQueue();
    }
}
