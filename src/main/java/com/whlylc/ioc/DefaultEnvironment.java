package com.whlylc.ioc;

import com.whlylc.util.ClassPathUtils;
import com.whlylc.util.NumberUtils;
import com.whlylc.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Default environment implementation based on property file
 *
 * @author Zeal
 */
public class DefaultEnvironment implements Environment {

    /**
     * Default configuration directory key in system environment
     */
    public static final String DEFAULT_SYS_ENV_CONFIG_DIR = "APP_CONFIG_DIR";

    /**
     * Default configuration file
     */
    public static final String DEFAULT_CONFIG_FILE_NAME = "app.properties";

    /**
     * Default config dir under current working directory
     */
    private static final String CWD_CONFIG_DIR_NAME = "config";

    /**
     * Configuration file content
     */
    private Properties properties = null;

    /**
     * System environment name represents the config dir
     */
    private String sysEnvConfigDir = null;

    /**
     * Configuration file name
     */
    private String configFileName = null;

    /**
     * Reserve the configuration dir
     */
    private File configDir = null;

    public DefaultEnvironment() {
        initPropertySource(DEFAULT_CONFIG_FILE_NAME, null, DEFAULT_SYS_ENV_CONFIG_DIR);
    }

    public DefaultEnvironment(Properties properties) {
        initPropertySource(DEFAULT_CONFIG_FILE_NAME, null, DEFAULT_SYS_ENV_CONFIG_DIR);
        this.properties.putAll(properties);
    }

    public DefaultEnvironment(File configDirOrFile) {
        if (configDirOrFile == null || configDirOrFile.exists()) {
            throw new IllegalStateException("Parameter configDirOrFile is invalid, make sure it's not null and existed");
        }
        if (configDirOrFile.isDirectory()) {
            initPropertySource(DEFAULT_CONFIG_FILE_NAME, configDirOrFile, DEFAULT_SYS_ENV_CONFIG_DIR);
        } else {
            initPropertySource(configDirOrFile.getName(), configDirOrFile.getParentFile(), DEFAULT_SYS_ENV_CONFIG_DIR);
        }
    }

    public DefaultEnvironment(File configDir, String configFileName) {
        if (StringUtils.isEmpty(configFileName)) {
            throw new IllegalStateException("Parameter configFileName is required");
        }
        if (configDir == null || !configDir.exists() || !configDir.isDirectory()) {
            throw new IllegalStateException("Parameter configDirOrFile is invalid, make sure it's not null and existed");
        }
        initPropertySource(configFileName, configDir, DEFAULT_SYS_ENV_CONFIG_DIR);
    }

    public DefaultEnvironment(String sysEnvConfigDir, String configFileName) {
        if (StringUtils.isEmpty(sysEnvConfigDir) || StringUtils.isEmpty(configFileName)) {
            throw new IllegalStateException("Parameter sysEnvConfigDir and configFileName are required");
        }
        initPropertySource(configFileName, null, sysEnvConfigDir);
    }

    public DefaultEnvironment(String configDirOrFile) {
        if (StringUtils.isEmpty(configDirOrFile)) {
            throw new IllegalStateException("Parameter configDirOrFile is required");
        }
        File file = new File(configDirOrFile);
        if (file.exists()) {
            if (file.isDirectory()) {
                initPropertySource(DEFAULT_CONFIG_FILE_NAME, file, DEFAULT_SYS_ENV_CONFIG_DIR);
            } else {
                initPropertySource(file.getName(), file.getParentFile(), DEFAULT_SYS_ENV_CONFIG_DIR);
            }
        }
        //File name only
        else {
            String value = System.getenv(configDirOrFile);
            //System environment path
            if (value != null) {
                initPropertySource(DEFAULT_CONFIG_FILE_NAME, null, configDirOrFile);
            } else {
                initPropertySource(configDirOrFile, null, DEFAULT_SYS_ENV_CONFIG_DIR);
            }
        }
    }

    private void initPropertySource(String configFileName, File configDir, String sysEnvConfigDir) {
        this.configFileName = configFileName;
        this.configDir = configDir;
        this.sysEnvConfigDir = sysEnvConfigDir;
        //Load from dir
        if (this.configDir != null) {
            this.properties = this.loadProperties(this.configDir, this.configFileName);
            if (this.properties != null) {
                return;
            }
        }
        //Load from system environment path
        if (StringUtils.isNotEmpty(this.sysEnvConfigDir)) {
            String dirString = System.getenv(this.sysEnvConfigDir);
            if (StringUtils.isNotEmpty(dirString)) {
                File _configDir = new File(dirString);
                this.properties = this.loadProperties(_configDir, this.configFileName);
                if (this.properties != null) {
                    return;
                }
            }
        }
        //Load from current working directory
        File cwd = new File(System.getProperty("user.dir"));
        if (cwd.exists() && cwd.isDirectory()) {
            this.properties = this.loadProperties(cwd, this.configFileName);
            if (this.properties != null) {
                return;
            }
            File _configDir = new File(cwd, CWD_CONFIG_DIR_NAME);
            this.properties = this.loadProperties(_configDir, this.configFileName);
            if (properties != null) {
                return;
            }
        }
        //Load from class path
        //FIXME The property source can be in jar file
        try {
            File _configDir = ClassPathUtils.getClassPath(DefaultEnvironment.class);
            this.properties = this.loadProperties(_configDir, this.configFileName);
            if (properties != null) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Cannot find any property source
        this.properties = new Properties();
    }

    private Properties loadProperties(File configDir, String configFileName) {
        if (configDir.exists() && configDir.isDirectory()) {
            File configFile = new File(configDir, configFileName);
            //this.properties = this.loadProperties(configFile);
            if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
                Properties prop = new Properties();
                try (FileInputStream fis = new FileInputStream(configFile)) {
                    prop.load(fis);
                    return prop;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getProperty(String key, String defaultValue) {
        return this.properties.getProperty(key, defaultValue);
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = this.properties.getProperty(key);
        return NumberUtils.toInt(value, defaultValue);
    }

    @Override
    public long getLongProperty(String key, long defaultValue) {
        String value = this.properties.getProperty(key);
        return NumberUtils.toLong(value, defaultValue);
    }

}
