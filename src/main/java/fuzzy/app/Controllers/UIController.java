package fuzzy.app.Controllers;
import fuzzy.app.Views.UserInterface;
import fuzzy.app.Model.RESOLUTIONS;
import fuzzy.app.Model.ResolutionFLS;

public class UIController {
    private UserInterface ui;
    private ResolutionFLS resolutionFLS;

    public UIController(double downloadSpeed, double packetNotLoss, double bufferSize, int viewportSize) {
        resolutionFLS = new ResolutionFLS();
        ui = new UserInterface(RESOLUTIONS.values().length, downloadSpeed, packetNotLoss, bufferSize, viewportSize);
        ui.setLocationRelativeTo(null);
        updateResolution(downloadSpeed, packetNotLoss, bufferSize, viewportSize);
        ui.setVisible(true);
    }

    private void updateResolution(double downloadSpeed, double packetNotLoss, double bufferSize, int viewportSize) {
        RESOLUTIONS resolution = resolutionFLS.run(downloadSpeed, packetNotLoss, bufferSize, viewportSize);
        ui.updateResolution(resolution.toString(), resolution.ordinal());
    }
}