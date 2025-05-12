package fuzzy.app.Model;

public class InputVariable{
	
	private String name;
	private double value;

	public InputVariable(String name, double value){
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Ninguna entrada puede ser nula o vacía.");
		}
		this.name = name;
		this.value = value;
	}

	public String getName(){
		return name;
	}

	public double getValue(){
		return value;
	}
}
