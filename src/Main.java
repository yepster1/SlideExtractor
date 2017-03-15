import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;

public class Main {

    LinkedList<BufferedImage> slides;
    String folderName;

    public Main(String folder){
        slides = new LinkedList<>();
        folderName = folder;
    }
    public static void main(String[] args) throws IOException {
        Main main = new Main(args[1]);
        LinkedList<BufferedImage> allSlides = main.getFrames(args[0],Integer.parseInt(args[2]));
        main.filter(allSlides);
        main.writeSlidesToFile();
    }

    public void writeSlidesToFile() throws IOException {
        int counter = 0;
        File folder = new File(folderName);
        if(!folder.mkdir()){
            throw new RuntimeException("Folder was not able to be created");
        }
        for (BufferedImage img: slides){
            File file = new File(folderName+"/"+counter+++".png");
            ImageIO.write(img,"png",file);
        }
    }

    public void filter(LinkedList<BufferedImage> oldImages) throws IOException {
        BufferedImage previous = null;
        for(BufferedImage i: oldImages) {
            if(previous == null){
                previous = i;
                slides.add(i);
                continue;
            }
            if(!Main.compareImages(previous,i)){
                slides.add(i);
                previous = i;
            }
        };
        System.out.println(slides.size());
        //BufferedImage newImage =

    }

    public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // The images must be the same size.
        if (imgA.getWidth() == imgB.getWidth() && imgA.getHeight() == imgB.getHeight()) {
            int width = imgA.getWidth();
            int height = imgA.getHeight();
            int firstWeights = 0;
            int SecondWeights = 0;

            // Loop over every pixel.
            for (int y = 5; y < height; y+=height/10) {
                for (int x = 5; x < width; x += width / 10) {
                    firstWeights += Math.abs(imgA.getRGB(x, y));
                    SecondWeights += Math.abs(imgB.getRGB(x, y));
                }
            }
            if(Math.abs(firstWeights-SecondWeights) < firstWeights/50){
                return true;
            }else{
                return false;
            }
        } else {
            return false;
        }
    }


    public LinkedList<BufferedImage> getFrames(String videoFile,int timeDifference){
        DecodeAndCaptureFrames decode = new DecodeAndCaptureFrames(videoFile,timeDifference);
        return decode.getFrames();
    }
}
