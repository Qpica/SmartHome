class Room {
	
	final double targetTemp;
	double currentTemp;
	String name;
	
	Room(double targetTemp, double currentTemp, String name){
		this.targetTemp = targetTemp;
		this.currentTemp = currentTemp;
		this.name = name;
	}
	
	public void heat(Double temperature) {
		this.currentTemp += temperature;
	}
	
	public void cool(Double temperature) {
		this.currentTemp -= temperature;
	}
	
}