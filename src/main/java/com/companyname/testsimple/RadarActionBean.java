package com.companyname.testsimple;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;


/**
 * Created by Alexandra on 3/10/2017.
 */
public class RadarActionBean implements ActionBean {
    private ActionBeanContext context;
    public ActionBeanContext getContext() { return context; }
    public void setContext(ActionBeanContext context) { this.context = context; }

    @DefaultHandler
    public void findInvader() {
//        File inFile = new File("C:\\radarPage.txt");
//        FileProcessor processor = new ImageFileProcessor();


    }

    private FileBean radarImage;

    public FileBean getRadarImage() {
        return radarImage;
    }

    public void setRadarImage(FileBean radarImage) {
        this.radarImage = radarImage;
    }
}
