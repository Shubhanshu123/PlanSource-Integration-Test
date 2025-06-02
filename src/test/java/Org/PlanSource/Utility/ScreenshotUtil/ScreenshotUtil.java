package Org.PlanSource.Utility.ScreenshotUtil;

import BaseClass.BaseClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil extends BaseClass {

    public static String captureScreenshot(String screenshotName) throws IOException {
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + File.separator +"screenshots" + File.separator + screenshotName + ".png";
        FileUtils.copyFile(source, new File(destination));
        return destination;
    }
}

