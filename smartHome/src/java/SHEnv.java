// Environment code for project smartHome

import jason.asSyntax.*;
import jason.asSyntax.parser.ParseException;
import jason.environment.*;
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
    
    private static final boolean heatingIsOn = false;
    

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
        try {
        	// add global percepts
			addPercept(hoff);
			
			//add percepts to chamber_Agent
			addPercept("chamber_Agent", Literal.parseLiteral("temp(chamber,"+ chamber.currentTemp +")"));
			
			//add percepts to kitchen_Agent
			
			
			//add percepts to room1_Agent
			
			
			//add percepts to room2_Agent
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        logger.info("[" + agName + "] doing: " + action );
        boolean result = false;

        if (action.getFunctor().equals("cool")) {
            //informAgsEnvironmentChanged();
        	String roomName = action.getTerm(0).toString();
        	
        	if(chamber.name.equals(roomName)) {
        		chamber.currentTemp -= 1;
        		result = true;
        	}
        }
        
        updatePercepts();
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
    	//chamber_Agent
    	addPercept("chamber_Agent", Literal.parseLiteral("temp(chamber,"+ chamber.currentTemp +")"));
    }
    
}
