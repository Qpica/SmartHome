// Environment code for project smartHome

import jason.asSyntax.*;
import jason.asSyntax.parser.ParseException;
import jason.environment.*;

import java.util.ArrayList;
import java.util.logging.*;

public class SHEnv extends Environment {

    private Logger logger = Logger.getLogger("smartHome."+SHEnv.class.getName());
    
    
    public static final Literal hoff = Literal.parseLiteral("heating_off");
    public static final Literal hon = Literal.parseLiteral("heating_on");
    //public static final Literal ccr = Literal.parseLiteral("cool(Chamber)"); //cool room
    //public static final Literal hcr = Literal.parseLiteral("heat(Chamber)"); //heat room
    
    
    private static final Room kitchen = new Room(20, 20, "kitchen");
    private static final Room chamber = new Room(12, 18, "chamber");
    private static final Room room1 = new Room(24, 20, "room1");
    private static final Room room2 = new Room(23, 20, "room2");
    
    private static ArrayList<Room> roomList = new ArrayList<>();
    
    
    private static boolean heatingIsOn = false;
    
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
        	// add global percepts
			addPercept(hoff);
			
			//add percepts to chamber_Agent
			addPercept("chamber_Agent", Literal.parseLiteral("temp(chamber,"+ chamber.currentTemp +")"));
			
			//add percepts to kitchen_Agent
			addPercept("kitchen_Agent", Literal.parseLiteral("temp(kitchen,"+ kitchen.currentTemp +")"));
			
			//add percepts to room1_Agent
			addPercept("room1_Agent", Literal.parseLiteral("temp(room1,"+ room1.currentTemp +")"));
			
			//add percepts to room2_Agent
			addPercept("room2_Agent", Literal.parseLiteral("temp(room2,"+ room2.currentTemp +")"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        boolean result = false;

        if(action.getFunctor().equals("cool")) {
            
        	String roomName = action.getTerm(0).toString();
        	
        	if(chamber.name.equals(roomName)) {
        		chamber.currentTemp -= 1;
        		result = true;
        	}
        }
        
        
        
        //set heating state
        if(action.getFunctor().equals("heat_on")) {
        	logger.info("Heating is turned ON");
        	SHEnv.heatingIsOn = true;
        	result = true;
        }
        else if(action.getFunctor().equals("heat_off")) {
        	logger.info("Heating is turned OFF");
        	SHEnv.heatingIsOn = false;
        	result = true;
        }
        
        //do action based on heating state
        if(SHEnv.heatingIsOn) {
        	for(Room room : roomList) {
        		room.heat(1.0);
        	}
        	result = true;
        }
        else if(!SHEnv.heatingIsOn) {
        	for(Room room : roomList) {
        		room.cool(0.1);
        	}
        	result = true;
        }
        
        
        updatePercepts();
        informAgsEnvironmentChanged();
        return result; // the action was executed with success
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
    
    private void updatePercepts() {
    	clearPercepts();
    	
    	//add global percepts
    	if(heatingIsOn) {
    		addPercept(hon);
    	}
    	else {
    		addPercept(hoff);
    	}
    	
    	//add local Percepts
    	//add percepts to chamber_Agent
		addPercept("chamber_Agent", Literal.parseLiteral("temp(chamber,"+ chamber.currentTemp +")"));
		
		//add percepts to kitchen_Agent
		addPercept("kitchen_Agent", Literal.parseLiteral("temp(kitchen,"+ kitchen.currentTemp +")"));
		
		//add percepts to room1_Agent
		addPercept("room1_Agent", Literal.parseLiteral("temp(room1,"+ room1.currentTemp +")"));
		
		//add percepts to room2_Agent
		addPercept("room2_Agent", Literal.parseLiteral("temp(room2,"+ room2.currentTemp +")"));

    }
    
}
