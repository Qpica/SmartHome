import java.util.ArrayList;
import java.util.logging.Logger;


public class Termostate {
	private ArrayList<Room> rooms;
	private boolean isHeatingOn;
    private  Logger logger = Logger.getLogger("smartHome."+ Termostate.class.getName());

	
	Termostate(ArrayList<Room> rooms){
		this.rooms = rooms;
		this.isHeatingOn = false;
	}
	
	public ArrayList<Room> getRooms() {
		return this.rooms;
	}
	
	public void updateRoomTemperatures() {
		if(isHeatingOn) {
			for(Room room : rooms) {
				room.currentTemp++;
			}
		}else {
			for(Room room : rooms) {
				room.currentTemp--;
			}
		}
	}

	public void setHeatingStatus(boolean heatingVoted) {
		this.isHeatingOn = heatingVoted;
		logger.info("Heating status: " + this.isHeatingOn);
	}
}
