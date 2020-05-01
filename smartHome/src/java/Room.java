class Room {
	
	final double targetTemp;
	double currentTemp;
	String name;
	boolean isHeatingRequired;
	boolean hasVoted;
	
	Room(double targetTemp, double currentTemp, String name){
		this.targetTemp = targetTemp;
		this.currentTemp = currentTemp;
		this.name = name;
		this.isHeatingRequired = false;
		this.hasVoted = false;
	}
	
	public void heat(Double temperature) {
		this.currentTemp += temperature;
	}
	
	public void cool(Double temperature) {
		this.currentTemp -= temperature;
	}
	
}