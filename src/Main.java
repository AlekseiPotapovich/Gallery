import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    public void run() {
        JFrame frame = new JFrame("DT Developer Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);


        frame.setResizable(false);
        JFrame imageFrame = new JFrame("Image");
        imageFrame.setLocation(50,50);
        imageFrame.setResizable(false);
        imageFrame.setVisible(false);


        JPanel searchPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        ImagePanel imagePanel = new ImagePanel();

        final Image[] image = {null};

        JButton openGalleryButton = new JButton("Open Gallery");
        openGalleryButton.setVisible(true);
        buttonPanel.add(openGalleryButton);

        JTextField searchTextField = new JTextField(21);
        searchTextField.setVisible(false);
        searchPanel.add(searchTextField);

        JButton searchImageButton = new JButton("Search");
        searchImageButton.setVisible(false);
        searchPanel.add(searchImageButton);


        FlowLayout layout = new FlowLayout();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        frame.add(buttonPanel);//, BorderLayout.NORTH);

        JDialog loading = new JDialog();
        loading.setTitle("Loading...");
        loading.setAlwaysOnTop(true);
        loading.setSize(300,150);
        loading.setLocation(620,300);
        loading.setVisible(false);


        searchImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Image searchImage = null;
                String imageName = searchTextField.getText();
                File folder = new File("assets/");
                File[] listOfFiles = folder.listFiles();

                for (File file : listOfFiles) {
                    if (file.isFile() & file.getName().equals(imageName)) {
                        //System.out.println(file.getName());
                        try {
                            searchImage = ImageIO.read(new File("assets/" + file.getName()));
                            drawImageFrame(imageFrame, searchImage, file.getName());
                            searchTextField.setText("");
                            return;
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                JOptionPane.showMessageDialog(frame, "Image not found!", "Error!", JOptionPane.ERROR_MESSAGE, null);
                searchTextField.setText("");
            }
        });
        openGalleryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.add(searchPanel, BorderLayout.NORTH);

                openGalleryButton.setVisible(false);
                searchImageButton.setVisible(true);
                searchTextField.setVisible(true);
                loading.setVisible(true);

                imagePanel.initImagePanel();
                imagePanel.setPreferredSize(new Dimension(990,(imagePanel.images.size() / 42) * 1000));

                JScrollPane scrollPane = new JScrollPane(imagePanel,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.getVerticalScrollBar().setUnitIncrement(17);
                frame.add(scrollPane);
                frame.revalidate();
                loading.setVisible(false);
                openGalleryButton.removeActionListener((ActionListener)this);

                imagePanel.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent me) {

                        //System.out.println("me " + me.paramString());
                        int index = (int) Math.floor(me.getX() / 120);
                        int row = (int) Math.floor(((me.getY() - 50) / 120));

                        if(row >= 1){
                            index = index + (row * 7);
                        }
                        //System.out.println("Index = " + index + "Row = " + row);
                        if(me.getX() >= 120 & me.getX() <= 940 & me.getY() >= 50){
                            if(index <= imagePanel.images.size()){
                                image[0] = imagePanel.images.get(index-1);
                                String imageName = imagePanel.imageNames.get(index);
                                drawImageFrame(imageFrame, image[0], imageName);
                            }
                        }
                    }
                });
            }
        });
    }

    public void drawImageFrame(JFrame imageFrame, Image image, String imageName){
        if(imageName != null)
            imageFrame.setTitle("Image : " + imageName);
        int wightImageFrame, heightImageFrame;
        wightImageFrame = image.getWidth(null);
        heightImageFrame = (image.getHeight(null)) + 100;
        imageFrame.setVisible(true);
        imageFrame.setSize(new Dimension(wightImageFrame, heightImageFrame));
        imageFrame.repaint();
        drawImage(image, imageFrame);
    }

    JLabel label1 = new JLabel();
    public void drawImage(Image image, JFrame frame){

        label1.setIcon(new ImageIcon(image));
        frame.add(label1, BorderLayout.CENTER);
        frame.revalidate();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main()::run);
    }
}
