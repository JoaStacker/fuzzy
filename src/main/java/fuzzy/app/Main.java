package fuzzy.app;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.*;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Load from 'FCL' file
        String fileName = "fcl/video_quality_regulator.fcl";
        FIS fis = FIS.load(fileName,true);

        // Error while loading?
        if( fis == null ) {
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }

        // Access the function block
        FunctionBlock functionBlock = fis.getFunctionBlock("video_quality_regulator");

        // Show
        JFuzzyChart.get().chart(functionBlock);

        // Set inputs
        fis.setVariable("buffer_size", 900);      // Valor en KB
        fis.setVariable("download_speed", 15);    // Valor en Mbps
        fis.setVariable("latency", 40);          // Valor en ms
        fis.setVariable("packet_loss", 2);        // Valor en %


        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        Variable video_resolution = functionBlock.getVariable("video_resolution");
        System.out.println("Video resolution: " + video_resolution.getValue());
        JFuzzyChart.get().chart(video_resolution, video_resolution.getDefuzzifier(), true);

    }
}
