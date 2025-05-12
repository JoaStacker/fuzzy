package fuzzy.app.Model;

public class ResolutionFLS {
 
	private InputVariable[] inputs;
    private FuzzyLogicSystem downloadPacketsFLS;    
    private FuzzyLogicSystem throughputBufferFLS;
    private FuzzyLogicSystem internetViewportFLS;

	public ResolutionFLS() {
    }

    public RESOLUTIONS run(double downloadSpeed, double packetNotLoss, double bufferSize, int viewportSize) {
        InputVariable downloadSpeedInput = new InputVariable("download_speed", downloadSpeed);
        InputVariable packetNotLossInput = new InputVariable("packet_not_loss", packetNotLoss);
        inputs = new InputVariable[]{ downloadSpeedInput, packetNotLossInput }; 
        downloadPacketsFLS = new FuzzyLogicSystem("download_packets", inputs, "throughput_quality");
        
        InputVariable throughputQualityInput = new InputVariable("throughput_quality", downloadPacketsFLS.getResult());
        InputVariable bufferSizeInput = new InputVariable("buffer_size", bufferSize);
        inputs = new InputVariable[]{ throughputQualityInput, bufferSizeInput }; 
        throughputBufferFLS = new FuzzyLogicSystem("throughput_buffer", inputs, "internet_performance");

        InputVariable internetPerformanceInput = new InputVariable("internet_performance", throughputBufferFLS.getResult());
        InputVariable viewportSizeInput = new InputVariable("viewport_size", viewportSize);
        inputs = new InputVariable[]{ internetPerformanceInput, viewportSizeInput }; 
        internetViewportFLS = new FuzzyLogicSystem("internet_viewport", inputs, "video_resolution");

        double result = internetViewportFLS.getResult();
        RESOLUTIONS resolution = RESOLUTIONS.LOW;
        return resolution;
    }
}
