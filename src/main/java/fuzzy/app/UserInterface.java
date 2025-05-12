package fuzzy.app;

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
    private ImageIcon veryLowResolution;
    private ImageIcon lowResolution;
    private ImageIcon mediumResolution;
    private ImageIcon highResolution;
    private ImageIcon veryHighResolution;

    public UserInterface() {
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
        downloadSpeedSlider = createSlider("Download Speed (Mbps)", 0, 10, 4);
        packetNotLossSlider = createSlider("Packet Not Loss (%)", 0, 100, 100);
        bufferSizeSlider = createSlider("Buffer Size (s)", 0, 40, 20);
        viewportSizeSlider = createSlider("Viewport Size (px)", 0, 2000, 1000);

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
        downloadSpeedLabel = new JLabel("Download Speed: 4 Mbps", SwingConstants.CENTER);
        packetNotLossLabel = new JLabel("Packet Not Loss: 100%", SwingConstants.CENTER);
        bufferSizeLabel = new JLabel("Buffer Size: 20 s", SwingConstants.CENTER);
        viewportSizeLabel = new JLabel("Viewport Size: 1000 px", SwingConstants.CENTER);

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
        loadImages();

        // Add change listener
        ChangeListener listener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateLabel((JSlider) e.getSource());
                updateResolution();
            }
        };

        downloadSpeedSlider.addChangeListener(listener);
        packetNotLossSlider.addChangeListener(listener);
        bufferSizeSlider.addChangeListener(listener);
        viewportSizeSlider.addChangeListener(listener);

        // Initial calculation
        updateResolution();
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

    private void loadImages() {
        // Load images from resources (you'll need to add these images to your resources)
        veryLowResolution = new ImageIcon(getClass().getResource("/images/very_low_resolution.png"));
        veryLowResolution = new ImageIcon(veryLowResolution.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH));
        lowResolution = new ImageIcon(getClass().getResource("/images/low_resolution.png"));
        lowResolution = new ImageIcon(lowResolution.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH));
        mediumResolution = new ImageIcon(getClass().getResource("/images/medium_resolution.png"));
        mediumResolution = new ImageIcon(mediumResolution.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH));
        highResolution = new ImageIcon(getClass().getResource("/images/high_resolution.png"));
        highResolution = new ImageIcon(highResolution.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH));
        veryHighResolution = new ImageIcon(getClass().getResource("/images/very_high_resolution.png"));
        veryHighResolution = new ImageIcon(veryHighResolution.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH));
    }

    private void updateResolution() {
        InputVariable[] inputs;

        // First FLS - Download Packets
        InputVariable downloadSpeed = new InputVariable("download_speed", downloadSpeedSlider.getValue());
        InputVariable packetNotLoss = new InputVariable("packet_not_loss", packetNotLossSlider.getValue());
        inputs = new InputVariable[]{ downloadSpeed, packetNotLoss };
        FuzzyLogicSystem downloadPacketsFLS = new FuzzyLogicSystem("download_packets", inputs, "throughput_quality");

        // Second FLS - Throughput Buffer
        InputVariable throughputQuality = new InputVariable("throughput_quality", downloadPacketsFLS.getResult());
        InputVariable bufferSize = new InputVariable("buffer_size", bufferSizeSlider.getValue());
        inputs = new InputVariable[]{ throughputQuality, bufferSize };
        FuzzyLogicSystem throughputBufferFLS = new FuzzyLogicSystem("throughput_buffer", inputs, "internet_performance");

        // Third FLS - Resolution
        InputVariable internetPerformance = new InputVariable("internet_performance", throughputBufferFLS.getResult());
        InputVariable viewportSize = new InputVariable("viewport_size", viewportSizeSlider.getValue());
        inputs = new InputVariable[]{ internetPerformance, viewportSize };
        FuzzyLogicSystem resolutionFLS = new FuzzyLogicSystem("internet_viewport", inputs, "video_resolution");

        double result = resolutionFLS.getResult();
        resolutionLabel.setText(String.format("Current Resolution: %.2f", result));

        // Update image based on result
        if (result < 4.0) {
            resolutionImageLabel.setIcon(lowResolution);
        } else if (result < 7.0) {
            resolutionImageLabel.setIcon(mediumResolution);
        } else {
            resolutionImageLabel.setIcon(highResolution);
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserInterface ui = new UserInterface();
            ui.setVisible(true);
        });
    }
}
