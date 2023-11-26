package engine.dengine.io;

import java.io.*;
import java.util.Objects;

/**
 * @author Anton Schoenfeld
 * @version 1.0
 * @since 1.0
 * <br>
 * <h2>{@link FileLoader}</h2>
 * <br>
 * The {@link FileLoader} class is used to handle <b>I/O</b> operations on ordinary text files, like
 * <b>*.txt</b>, <b>*.vert</b> or <b>*.frag</b>. These operations include writing, reading, deleting
 * and creating (<b>CRUD</b>).
 */
public class FileLoader
{
    /**
     * Reads the file.
     * @param filePath the path to the file originating from /resources
     * @return the file contents as a String
     * @throws IOException if any <b>I/O</b> operation in this method throw a {@link IOException}
     */
    public static String readFile (String filePath)
        throws IOException
    {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        Objects.requireNonNull(is);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = reader.readLine()) != null)
        {
            result.append(line).append("\n");
        }

        reader.close();
        is.close();
        return result.toString();
    }
}
