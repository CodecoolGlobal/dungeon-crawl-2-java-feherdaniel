package com.codecool.dungeoncrawl.model;

import java.io.FileNotFoundException;
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

    public void saveObjectAsJSON (Object object, String filePath) {
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            out.write(stringifyObject(object).getBytes());
        } catch (FileNotFoundException fnf) {
            LogManager.enqueueMessage(String.format("Failed to open file '%s'", filePath));
            if (fnf.getMessage() != null) LogManager.enqueueMessage(fnf.getMessage());
        } catch (IOException ioe) {
            LogManager.enqueueMessage(String.format("IO exception occurred while handling file '%s'", filePath));
            if (ioe.getMessage() != null) LogManager.enqueueMessage(ioe.getMessage());
        } finally {
            LogManager.dumpQueue();
        }
    }

    private String stringifyObject (Object object) {
        StringBuilder stringify = new StringBuilder();

        boolean initializer = true;

        stringify.append('{');

        for (Field field : object.getClass().getFields()) {
            try {
                stringify.append(String.format("%s\"%s\": ", initializer ? "" : ", ", field.getName()));
                if (initializer) initializer = false;

                if (field.get(object) instanceof List) {
                    stringify.append('[');
                    for (Object o : (List)field.get(object)) stringify.append(o.toString());
                    stringify.append(']');
                }
                else if (stringableClasses.contains(field.getType()))
                    stringify.append(String.format("\"%s\"", field.get(object)));
                else if (field.get(object).getClass().getPackageName().matches("com.codecool.dungeoncrawl*"))
                    stringify.append(new OutputManager().stringifyObject(field.get(object)));
                else
                    stringify.append(field.get(object).toString());

            } catch (IllegalAccessException | IllegalArgumentException iae) {
                LogManager.enqueueMessage(String.format("Write of field '%s' failed", field.getName()));
                if (iae.getMessage() != null) LogManager.enqueueMessage(iae.getMessage());

            }
        }

        for (Method method : object.getClass().getMethods()) {
            try {
                String methodName = method.getName();
                if (!methodName.matches("get[a-zA-Z]*")) continue;
                if (methodName.equals("getClass") || methodName.equals("getId")) continue;
                methodName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);

                stringify.append(String.format("%s\"%s\": ", initializer ? "" : ", ", methodName));
                if (initializer) initializer = false;

                if (method.invoke(object) instanceof List) {
                    stringify.append('[');
                    for (Object o : (List)method.invoke(object)) stringify.append(o.toString());
                    stringify.append(']');
                }
                else if (stringableClasses.contains(method.getReturnType()))
                    stringify.append(String.format("\"%s\"", method.invoke(object)));
                else if (method.invoke(object).getClass().getPackageName().matches("com.codecool.dungeoncrawl*"))
                    stringify.append(new OutputManager().stringifyObject(method.invoke(object)));
                else
                    stringify.append(method.invoke(object));

            } catch (IllegalAccessException | InvocationTargetException ite) {
                LogManager.enqueueMessage(String.format("Invocation of method '%s' failed", method.getName()));
                if (ite.getMessage() != null) LogManager.enqueueMessage(ite.getMessage());

            }
        }

        stringify.append('}');
        LogManager.enqueueMessage(String.format("Object with hash %s successfully encoded", object.hashCode()));

        LogManager.dumpQueue();

        return stringify.toString();
    }
}
