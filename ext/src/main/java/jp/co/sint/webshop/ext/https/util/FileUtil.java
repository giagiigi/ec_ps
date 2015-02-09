package jp.co.sint.webshop.ext.https.util;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {

    private static final Log logger = LogFactory.getLog(FileUtil.class);

    public static String DEFAULT_CHARSET = "UTF-8";

    public static String loadFileAsString(String file) {
        return loadFileAsString(file, DEFAULT_CHARSET);
    }

    public static String loadFileAsString(String file, String charset) {
        return loadFileAsString(new File(file), charset);
    }

    public static String loadFileAsString(URL url) {
        try {
            return loadFileAsString(new File(url.toURI()), DEFAULT_CHARSET);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String loadFileAsString(URL url, String charset) {
        try {
            return loadFileAsString(new File(url.toURI()), charset);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String loadFileAsString(File file, String charset) {
        try {
            RandomAccessFile r = new RandomAccessFile(file, "r");

            byte[] b = new byte[(int) r.length()];
            r.read(b);

            return new String(b, charset);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> loadFileAsLines(String file) {
        return loadFileAsLines(file, DEFAULT_CHARSET);
    }

    public static List<String> loadFileAsLines(File file) {
        return loadFileAsLines(file, DEFAULT_CHARSET);
    }

    public static List<String> loadFileAsLines(URL url) {
        try {
            return loadFileAsLines(new File(url.toURI()), DEFAULT_CHARSET);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> loadFileAsLines(String file, String charset) {
        return loadFileAsLines(new File(file), charset);
    }

    public static List<String> loadFileAsLines(File file, String charset) {

        List<String> lines = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static byte[] loadFileAsByteArray(URL url) {
        try {
            return loadFileAsByteArray(new File(url.toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] loadFileAsByteArray(String file) {
        return loadFileAsByteArray(new File(file));
    }

    public static byte[] loadFileAsByteArray(File file) {
        try {
            RandomAccessFile r = new RandomAccessFile(file, "r");

            byte[] b = new byte[(int) r.length()];
            r.read(b);

            return b;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Properties loadFileAsProperties(String file) {
        Properties p = new Properties();
        boolean success = loadFileIntoProperties(p, file);
        return success ? p : null;
    }

    public static Properties loadFileAsProperties(File file) {
        Properties p = new Properties();
        boolean success = loadFileIntoProperties(p, file);
        return success ? p : null;
    }

    public static boolean loadFileIntoProperties(Properties p, String file) {
        return loadFileIntoProperties(p, new File(file));
    }

    public static boolean loadFileIntoProperties(Properties p, File file) {
        try {
            InputStream is = new FileInputStream(file);
//            p.load(new InputStreamReader(is, DEFAULT_CHARSET));
            p.load(is);
            is.close();
            return true;
        } catch (Exception e) {
            logger.warn("Cannot load file[" + file + "]! <- " + e);
            return false;
        }
    }

    public static Properties loadFileAsProperties(String file, String charset) {
        Properties p = new Properties();
        boolean success = loadFileIntoProperties(p, file, charset);
        return success ? p : null;
    }

    public static Properties loadFileAsProperties(File file, String charset) {
        Properties p = new Properties();
        boolean success = loadFileIntoProperties(p, file, charset);
        return success ? p : null;
    }

    public static boolean loadFileIntoProperties(Properties p, String file, String charset) {
        return loadFileIntoProperties(p, new File(file), charset);
    }

    public static boolean loadFileIntoProperties(Properties p, File file, String charset) {
        try {
            InputStream is = new FileInputStream(file);
//            p.load(new InputStreamReader(is, DEFAULT_CHARSET));
            p.load(is);
            is.close();
            return true;
        } catch (Exception e) {
            logger.warn("Cannot load file[" + file + "]! <- " + e);
            return false;
        }
    }

    public static void writeBytesAsFile(String file, byte[] content) {
        try {
            RandomAccessFile w = new RandomAccessFile(file, "rws");
            w.setLength(0);
            w.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendBytesToFile(String file, byte[] content) {
        try {
            RandomAccessFile w = new RandomAccessFile(file, "rws");
            w.seek(w.length());
            w.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeStringAsFile(String file, String content) {
        try {
            RandomAccessFile w = new RandomAccessFile(file, "rws");
            w.setLength(0);
            w.write(content.getBytes(DEFAULT_CHARSET));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeStringAsFile(String file, String content, String charset) {
        try {
            RandomAccessFile w = new RandomAccessFile(file, "rws");
            w.setLength(0);
            w.write(content.getBytes(charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesAsFile(String file, String[] content) {
        writeLinesAsFile(new File(file), content, DEFAULT_CHARSET);
    }

    public static void writeLinesAsFile(String file, String[] content, String charset) {
        writeLinesAsFile(new File(file), content, charset);
    }

    public static void writeLinesAsFile(File file, String[] content, String charset) {
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), charset));
            for (String line : content)
                writer.println(line);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeLineAsFile(String file, String content) {
        writeLineAsFile(new File(file), content, DEFAULT_CHARSET);
    }

    public static void writeLineAsFile(File file, String content) {
        writeLineAsFile(file, content, DEFAULT_CHARSET);
    }

    public static void writeLineAsFile(String file, String content, String charset) {
        writeLineAsFile(new File(file), content, charset);
    }

    public static void writeLineAsFile(File file, String content, String charset) {
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), charset));
            writer.println(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendStringToFile(String file, String content) {
        try {
            RandomAccessFile w = new RandomAccessFile(file, "rws");
            w.seek(w.length());
            w.write(content.getBytes(DEFAULT_CHARSET));
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendStringToFile(String file, String content, String charset) {
        try {
            RandomAccessFile w = new RandomAccessFile(file, "rws");
            w.seek(w.length());
            w.write(content.getBytes(charset));
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendLineToFile(File file, String content, String charset) {
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), charset));
            writer.println(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendLinesToFile(String file, String[] content) {
        appendLinesToFile(new File(file), content, DEFAULT_CHARSET);
    }

    public static void appendLinesToFile(File file, String[] content) {
        appendLinesToFile(file, content, DEFAULT_CHARSET);
    }

    public static void appendLinesToFile(String file, String[] content, String charset) {
        appendLinesToFile(new File(file), content, charset);
    }

    public static void appendLinesToFile(File file, String[] content, String charset) {
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), charset));
            for (String line : content)
                writer.println(line);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean clearFile(File file) {
        try {
            RandomAccessFile w = new RandomAccessFile(file, "rws");
            w.setLength(0);
            w.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static File getFile(URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
