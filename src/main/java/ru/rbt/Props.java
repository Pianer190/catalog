package ru.rbt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Props {
    private static final Logger LOG = LoggerFactory.getLogger(Props.class);
    private static Props instance;
    private static Properties properties;

    private Props() {
        initProperties();
    }

    private static void initProperties() {
        properties = new Properties();

        try {
            InputStream is = settings();
            Throwable var1 = null;

            try {
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                Throwable var3 = null;

                try {
                    properties.load(isr);
                } catch (Throwable var28) {
                    var3 = var28;
                    throw var28;
                } finally {
                    if (var3 != null) {
                        try {
                            isr.close();
                        } catch (Throwable var27) {
                            var3.addSuppressed(var27);
                        }
                    } else {
                        isr.close();
                    }
                }
            } catch (Throwable var30) {
                var1 = var30;
                throw var30;
            } finally {
                    if (var1 != null) {
                        try {
                            is.close();
                        } catch (Throwable var26) {
                            var1.addSuppressed(var26);
                        }
                    } else {
                        is.close();
                    }
            }

        } catch (IOException var32) {
            throw new PropsRuntimeException("Failed to access properties file", var32);
        }
    }

    private static InputStream settings() {
        String configFile = System.getProperty("TagConfigFile", "application.properties");

        LOG.debug("Loading properties from {}", configFile);
        InputStream streamFromResource = Props.class.getClassLoader().getResourceAsStream(configFile);
        if (streamFromResource == null) {
            throw new PropsRuntimeException("File with properties not found");
        } else {
            return streamFromResource;
        }
    }

    public static synchronized Props getInstance() {
        if (instance == null) {
            instance = new Props();
        }

        return instance;
    }

    private String getProp(String name) {
        String val = getProps().getProperty(name, "");
        if (val.isEmpty()) {
            LOG.debug("Property {} was not found in properties file", name);
        }

        return val.trim();
    }

    public static Properties getProps() {
        return getInstance().getProperties();
    }

    private Properties getProperties() {
        return properties;
    }

    public static String get(String prop) {
        return getInstance().getProp(prop);
    }

    public static String get(String prop, String defaultValue) {
        String value = getInstance().getProp(prop);
        return value.isEmpty() ? defaultValue : value;
    }
}