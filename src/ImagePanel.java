import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class ImagePanel extends JPanel {

    private Image image;
    public ArrayList<Image> images = new ArrayList<>();
    public ArrayList<String> imageNames = new ArrayList<>();

    public void  initImagePanel(){
        try
        {
            File folder = new File("assets/");
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    //System.out.println(file.getName());
                    image = ImageIO.read(new File("assets/" + file.getName()));
                    images.add(image);
                    imageNames.add(file.getName());
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imageX = 0, imageY = 50, index = 0;

        for(int i = 0; i < images.size(); i++){
            if(imageX >= 840){
                imageY += 120;
                imageX = 0;
            }
            imageX += 120;
            image = images.get(index);
            g.drawImage(image, imageX, imageY, 100, 100, null);
            index++;
        }
    }
}