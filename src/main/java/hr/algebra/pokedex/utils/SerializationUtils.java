package hr.algebra.pokedex.utils;

import hr.algebra.pokedex.security.DeserializationWhitelist;

import java.io.*;

public class SerializationUtils {
    public static <T> void serialize(T o, final String fileName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        try (final ObjectOutputStream output = new ObjectOutputStream(fileOutputStream)) {
            output.writeObject(o);
            output.flush();
        }
    }

    public static <T> T deserialize(final String fileName) throws IOException, ClassNotFoundException {
        T object;
        FileInputStream fileInputStream = new FileInputStream(fileName);
        try (final ObjectInputStream input = new ObjectInputStream(fileInputStream)) {
            object = (T) input.readObject();
            final String className = object.getClass().getName();
            if (!DeserializationWhitelist.isClassAllowed(className)) {
                throw new SecurityException("Deserialization of this class is forbidden...");
            }
        }
        return object;
    }
}
