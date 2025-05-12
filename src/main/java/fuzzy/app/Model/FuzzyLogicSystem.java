package fuzzy.app.Model;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.*;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class FuzzyLogicSystem {

	private String fileName;
	private InputVariable[] inputs;
	private String outputName;
	private FunctionBlock functionBlock; 

    public FuzzyLogicSystem(String fileNameWithoutExtension, InputVariable[] inputs, String outputName) {
        // Validar el nombre del archivo
        if (fileNameWithoutExtension == null || fileNameWithoutExtension.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede estar vacío.");
        }

        // Validar las entradas
        if (inputs == null || inputs.length != 2) {
            throw new IllegalArgumentException("Debe haber exactamente dos entradas.");
        }

        // Validar la salida
        if (outputName == null || outputName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la salida no puede estar vacío.");
        }

        this.fileName = fileName;
        this.inputs = inputs;
        this.outputName = outputName;


        FIS fis = FIS.load("fcl/" + fileNameWithoutExtension + ".fcl", true);
        // Error while loading?
        if( fis == null ) {
            throw new IllegalArgumentException("Can't load file: '" + fileNameWithoutExtension + "'");
        }
        // Access the function block
        this.functionBlock = fis.getFunctionBlock(fileNameWithoutExtension + "_fls");

        for (InputVariable input : this.inputs) {
			fis.setVariable(input.getName(), input.getValue());
        }
        // Evaluate
        fis.evaluate();
    }

	public void getCharts(){
		// Show inputs' charts
        JFuzzyChart.get().chart(functionBlock);
        // Show output variable's chart
        Variable output = functionBlock.getVariable(outputName);
        JFuzzyChart.get().chart(output, output.getDefuzzifier(), true);
	}

	public double getResult(){
		Variable output = functionBlock.getVariable(outputName);	
		return output.getValue();
	}
}
