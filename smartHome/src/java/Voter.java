import java.util.ArrayList;
import java.util.logging.Logger;

import jason.asSyntax.Literal;
import jason.asSyntax.*;
import jason.asSyntax.parser.ParseException;
import jason.environment.*;

public class Voter extends Thread {
	
	private  Environment env;
    private  Logger logger = Logger.getLogger("smartHome."+ Voter.class.getName());
    private Termostate termostate;
    
    Voter(Environment env, ArrayList<Room> rooms){
    	this.env = env;
    	termostate = new Termostate(rooms);

    }

	public  void startNewVote() {
		this.refreshPercepts();
	}
	

	private void refreshPercepts() {
		env.clearPercepts();
		for(Room room : termostate.getRooms()) {
			env.clearPercepts(room.name);
			env.addPercept(room.name, Literal.parseLiteral("targetTemp("+ room.targetTemp +")"));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		env.addPercept(room.name, Literal.parseLiteral("temp("+ room.currentTemp +")"));
		}
	
	}


	public void setVoteForRoom(Room room, Structure action) {
		if(action.getFunctor().equals("heat_on")) {
        	logger.info(room.name + " has voted to turn heating on");
        	room.isHeatingRequired = true;
        }
        else if(action.getFunctor().equals("heat_off")) {
        	logger.info(room.name + " has voted to turn heating off");
        	room.isHeatingRequired = false;
        }
	}
	
	private boolean isHeatingVoted() {
		int voteForHeating = 0;
		for(Room room : termostate.getRooms()) {
			if(room.isHeatingRequired) {
				voteForHeating++;
			}
		}
		
		return (termostate.getRooms().size() - voteForHeating) < voteForHeating; 
	}


	@Override
	public void run() {
		while(true) {
			this.termostate.updateRoomTemperatures();
			this.startNewVote();
			try {
				Thread.sleep(1000);
				termostate.setHeatingStatus(this.isHeatingVoted());
				logger.info("Új szavazás < ------------------------------------------------");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
