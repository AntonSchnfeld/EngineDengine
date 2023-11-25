package engine.dengine.io;

import java.io.*;
import java.util.Objects;

public class FileLoader
{
    public static String readFile (String filePath)
    {
        try
        {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            Objects.requireNonNull(is);

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                result.append(line);
            }

            reader.close();
            is.close();
            return result.toString();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
