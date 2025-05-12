package fuzzy.app.Views;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class UserInterface extends JFrame {
    private JPanel slidersPanel;
    private JPanel labelsPanel;
    private JPanel resolutionPanel;
    private JSlider downloadSpeedSlider;
    private JSlider packetNotLossSlider;
    private JSlider bufferSizeSlider;
    private JSlider viewportSizeSlider;
    private JLabel resolutionLabel;
    private JLabel resolutionImageLabel;
    private JLabel downloadSpeedLabel;
    private JLabel packetNotLossLabel;
    private JLabel bufferSizeLabel;
    private JLabel viewportSizeLabel;
    private ImageIcon[] resolutionImages;

    public UserInterface(int numberOfResolutions, double downloadSpeed, double packetNotLoss, double bufferSize, int viewportSize) {
        setTitle("Video Resolution Fuzzy System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(1080, 720);
        setResizable(false);

        // Create main container with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        slidersPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        labelsPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        resolutionPanel = new JPanel(new BorderLayout(0, 10));

        // Initialize sliders
        downloadSpeedSlider = createSlider("Download Speed (Mbps)", 0, 10, (int) downloadSpeed);
        packetNotLossSlider = createSlider("Packet Not Loss (%)", 0, 100, (int) packetNotLoss);
        bufferSizeSlider = createSlider("Buffer Size (s)", 0, 40, (int) bufferSize);
        viewportSizeSlider = createSlider("Viewport Size (px)", 0, 2000, viewportSize);

        //Add sliders to slidersPanel
        slidersPanel.add(downloadSpeedSlider);
        slidersPanel.add(packetNotLossSlider);
        slidersPanel.add(bufferSizeSlider);
        slidersPanel.add(viewportSizeSlider);

        // Initialize resolution display
        resolutionLabel = new JLabel("Current Resolution: Calculating...", SwingConstants.CENTER);
        resolutionImageLabel = new JLabel("", SwingConstants.CENTER);

        //Add resolution display to resolutionPanel
        resolutionPanel.add(resolutionImageLabel, BorderLayout.CENTER);
        resolutionPanel.add(resolutionLabel, BorderLayout.SOUTH);

        //Initialize labels
        downloadSpeedLabel = new JLabel("Download Speed: " + downloadSpeed + " Mbps", SwingConstants.CENTER);
        packetNotLossLabel = new JLabel("Packet Not Loss: " + packetNotLoss + "%", SwingConstants.CENTER);
        bufferSizeLabel = new JLabel("Buffer Size: " + bufferSize + " s", SwingConstants.CENTER);
        viewportSizeLabel = new JLabel("Viewport Size: " + viewportSize + " px", SwingConstants.CENTER);

        //Add labels to labelsPanel
        labelsPanel.add(downloadSpeedLabel);
        labelsPanel.add(packetNotLossLabel);
        labelsPanel.add(bufferSizeLabel);
        labelsPanel.add(viewportSizeLabel);

        //Add panels to main panel
        mainPanel.add(slidersPanel, BorderLayout.NORTH);
        mainPanel.add(labelsPanel, BorderLayout.CENTER);
        mainPanel.add(resolutionPanel, BorderLayout.SOUTH);

        //Add main panel to frame
        add(mainPanel);

        // Load resolution images
        loadImages(numberOfResolutions);
    }

    private JSlider createSlider(String title, int min, int max, int initial) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, initial);
        slider.setName(title);
        slider.setMajorTickSpacing((max - min) / 5);
        slider.setMinorTickSpacing((max - min) / 10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createTitledBorder(title));
        return slider;
    }

    private void loadImages(int numberOfResolutions) {
        // Load images from resources (you'll need to add these images to your resources)
        resolutionImages = new ImageIcon[numberOfResolutions];
        for (int i = 0; i < numberOfResolutions; i++) {
            resolutionImages[i] = new ImageIcon(getClass().getResource("/images/resolution_" + i + ".png"));
            resolutionImages[i] = new ImageIcon(resolutionImages[i].getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH));
        }
    }

    public void updateResolution(String resolutionName, int resolutionIndex) {
        resolutionImageLabel.setIcon(resolutionImages[resolutionIndex]);
        resolutionLabel.setText("Current Resolution: " + resolutionName);
    }

    private void updateLabel(JSlider source) {
        int value = source.getValue();
        String label = source.getName();
        switch (label) {
            case "Download Speed (Mbps)":
                downloadSpeedLabel.setText(String.format("Download Speed: %d Mbps", value));
                break;
            case "Packet Not Loss (%)":
                packetNotLossLabel.setText(String.format("Packet Not Loss: %d%%", value));
                break;
            case "Buffer Size (s)":
                bufferSizeLabel.setText(String.format("Buffer Size: %d s", value));
                break;
            case "Viewport Size (px)":
                viewportSizeLabel.setText(String.format("Viewport Size: %d px", value));
                break;
        }
    }
}
