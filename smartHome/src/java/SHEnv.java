// Environment code for project smartHome

import jason.asSyntax.*;
import jason.asSyntax.parser.ParseException;
import jason.environment.*;

import java.util.ArrayList;
import java.util.logging.*;

public class SHEnv extends Environment {

    private Logger logger = Logger.getLogger("smartHome."+SHEnv.class.getName());
    
    private static final Room kitchen = new Room(20, 15, "kitchen");
    private static final Room chamber = new Room(19, 18, "chamber");
    private static final Room room1 = new Room(24, 20, "room1");
    private static final Room room2 = new Room(23, 20, "room2");
    
    private static ArrayList<Room> roomList = new ArrayList<>();
    
    
    private static boolean isHeatingOn = false;
    
    private static final Integer RequiredVotes = 4;
    private static Integer ReceivedVotes = 0;
    

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
        
        roomList.add(kitchen);
        roomList.add(chamber);
        roomList.add(room1);
        roomList.add(room2);
        
        try {
        	updatePercepts();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        boolean result = false;
        
      
        
        //set heating state
        if(action.getFunctor().equals("heating_off")) {
        	logger.info("Heating is turned OFF");
        	isHeatingOn = false;
        	result = true;
        }
        else if(action.getFunctor().equals("heating_on")){
        	logger.info("Heating is turned ON");
        	isHeatingOn = true;
        	result = true;
        }
        
        //do action based on heating state
        if(isHeatingOn) {
        	for(Room room : roomList) {
        		room.heat(1.0);
        	}
        }
        else{
        	for(Room room : roomList) {
        		room.cool(0.5);
        	}
        }
        
        for(Room room : roomList) {
      		logger.info(room.name + ", Target: " + room.targetTemp + ", Current: " + room.currentTemp);
      	}	
        
        updatePercepts();
        logger.info("NEW VOTE---------------------------------------------------------------------");
        //informAgsEnvironmentChanged();
        return result; // the action was executed with success
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
    
    private void updatePercepts() {
    	
    	clearAllPercepts();

    	// add global percepts
    	clearPercepts("voter_Agent");
		
    	//add percepts to chamber_Agent
    	clearPercepts("chamber_Agent");
    	addPercept("chamber_Agent", Literal.parseLiteral("targetTemp("+ chamber.targetTemp +")"));
    	addPercept("chamber_Agent", Literal.parseLiteral("temp(chamber,"+ chamber.currentTemp +")"));
    				
    				
    	//add percepts to kitchen_Agent
    	clearPercepts("kitchen_Agent");
    	addPercept("kitchen_Agent", Literal.parseLiteral("targetTemp("+ kitchen.targetTemp +")"));
    	addPercept("kitchen_Agent", Literal.parseLiteral("temp(kitchen,"+ kitchen.currentTemp +")"));
    			
    				
    	//add percepts to room1_Agent
    	clearPercepts("room1_Agent");
    	addPercept("room1_Agent", Literal.parseLiteral("targetTemp("+ room1.targetTemp +")"));
    	addPercept("room1_Agent", Literal.parseLiteral("temp(room1,"+ room1.currentTemp +")"));
    				
    				
    	//add percepts to room2_Agent
    	clearPercepts("room2_Agent");
    	addPercept("room2_Agent", Literal.parseLiteral("targetTemp("+ room2.targetTemp +")"));
    	addPercept("room2_Agent", Literal.parseLiteral("temp(room2,"+ room2.currentTemp +")"));
    }
    
}
