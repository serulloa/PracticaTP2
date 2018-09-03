package tp.pr6.model;

public class SimulatorError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorMsg;
	
	public SimulatorError(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	@Override
	public String toString() {
		return "ERROR: " + errorMsg;
	}

}
