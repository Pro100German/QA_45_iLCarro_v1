package utils;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeScreenShot {
    public static void main(String[] args){
        System.out.println(createFileCarName());
    }
    public static void takeScreenShot(TakesScreenshot screenshot){
        String fileName = createFileCarName();
        File scrFile = screenshot.getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(scrFile, new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createFileCarName() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formater.format(date);
        String fileName = "test_logs/screenshots/" + currentDate + ".png";
        return fileName;
    }
}
