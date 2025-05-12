package fuzzy.app;
import fuzzy.app.InputVariable;
import fuzzy.app.FuzzyLogicSystem;
import fuzzy.app.UserInterface;

public class Main {
    public static void main(String[] args) {
		InputVariable[] inputs;

		InputVariable downloadSpeed = new InputVariable("download_speed", 4);
		InputVariable packetNotLoss = new InputVariable("packet_not_loss", 100);
		inputs = new InputVariable[]{ downloadSpeed, packetNotLoss }; 

		FuzzyLogicSystem downloadPacketsFLS = new FuzzyLogicSystem("download_packets", inputs, "throughput_quality");

		InputVariable throughputQuality = new InputVariable("throughput_quality", downloadPacketsFLS.getResult());
		InputVariable bufferSize = new InputVariable("buffer_size", 20);
		inputs = new InputVariable[]{ throughputQuality, bufferSize }; 

		FuzzyLogicSystem throughputBufferFLS = new FuzzyLogicSystem("throughput_buffer", inputs, "internet_performance");
		
		InputVariable internetPerformance = new InputVariable("internet_performance", throughputBufferFLS.getResult());
		InputVariable viewportSize = new InputVariable("viewport_size", 1000);
		inputs = new InputVariable[]{ internetPerformance, viewportSize }; 

		FuzzyLogicSystem resolutionFLS = new FuzzyLogicSystem("internet_viewport", inputs, "video_resolution");

		System.out.println("Video resolution: " + resolutionFLS.getResult());

		downloadPacketsFLS.getCharts();
		throughputBufferFLS.getCharts();
		resolutionFLS.getCharts();

		UserInterface ui = new UserInterface();
		ui.setVisible(true);
    }
}
