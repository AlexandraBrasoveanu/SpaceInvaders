package com.amsterdam.spaceInvaders.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.amsterdam.services.FileProcessor;
import com.amsterdam.services.imageprocessing.ImageFileProcessor;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra on 3/13/2017.
 */
public class ObjectPatternConfiguration extends AbstractModule {

    private final static String BACKGROUND_PROP = "background";
    private final static String FOREGROUND_PROP = "foreground";
    private final static String SPACE_INVADER_PROP = "spaceInvader";

    /**
     * Loads the space invaders properties from the configuration files.
     */
    @Override
    protected void configure() {
        Parameters params = new Parameters();
        try {
            // load the configuration file
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("spaceInvader.properties"));
            Configuration config = builder.getConfiguration();
            String foreground = config.getString(FOREGROUND_PROP);
            // read the background and foreground configs
            if(StringUtils.isEmpty(foreground)) {
                throw new InvalidParameterException(MessageFormat.format("No value for property {0}!", FOREGROUND_PROP));
            }
            String background = config.getString("background");
            if(StringUtils.isEmpty(background)) {
                throw new InvalidParameterException(MessageFormat.format("Malformed value for property {0}!", BACKGROUND_PROP));
            }
            List<int[][]> valueList = new ArrayList<>();
            String[] configValue = config.getStringArray(SPACE_INVADER_PROP);
            // simplify the matrix containing the space invaders original patterns
            //by replacing the background and foreground elements with
            //1s and 0s
            for(String si: configValue) {
                si = si.replace(background, "0");
                si = si.replace(foreground, "1");
                int[][] siMatrix = fetchArrayFromPropValue(si, SPACE_INVADER_PROP);
                valueList.add(siMatrix);
            }

            //binding configuration values to keys to be injected
            // needs to define new type to inject parameterized list
            bind(new TypeLiteral<List<int[][]>>() {}).annotatedWith(Names.named("SpaceInvaderPattern")).toInstance(valueList);

            bind(String.class).annotatedWith(Names.named("Foreground")).toInstance(foreground);
            bind(String.class).annotatedWith(Names.named("Background")).toInstance(background);
            bind(FileProcessor.class).to(ImageFileProcessor.class);
        } catch(InvalidParameterException|ConfigurationException cex) {
            System.out.println("I/O Exception during loading object configuration \n" + cex);
            System.exit(1);
        }
    }


    /**
     * Creates two dimensional array from delineated string in properties file
     * @param propertyValue value of the property
     * @return two dimensional array
     */
    private int[][] fetchArrayFromPropValue(String propertyValue, String propertyName) throws InvalidParameterException {

        //get array split up by the semicolon
        String[] a = propertyValue.split(";");

        //create the two dimensional array with correct size
        int[][] intArray = new int[a.length][a[0].length()];

        //combine the arrays split by semicolon and transform to int
        for(int i = 0;i < a.length;i++) {
            for(int j = 0;j < a[i].length(); j++) {
                if (!Character.isDigit(a[i].charAt(j))) {
                    throw new InvalidParameterException(MessageFormat.format("Malformed value for property {0}!", propertyName));

                }
                intArray[i][j] = Integer.parseInt(String.valueOf(a[i].charAt(j)));
            }
        }
        return intArray;
    }


}
