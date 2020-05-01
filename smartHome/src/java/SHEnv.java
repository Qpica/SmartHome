// Environment code for project smartHome

import jason.asSyntax.*;
import jason.asSyntax.parser.ParseException;
import jason.environment.*;

import java.util.ArrayList;
import java.util.logging.*;

public class SHEnv extends Environment {

    private Logger logger = Logger.getLogger("smartHome."+SHEnv.class.getName());
    
    
    public static final Literal HEATING_OFF = Literal.parseLiteral("heating_off");
    public static final Literal HEATING_ON = Literal.parseLiteral("heating_on");
    //public static final Literal ccr = Literal.parseLiteral("cool(Chamber)"); //cool room
    //public static final Literal hcr = Literal.parseLiteral("heat(Chamber)"); //heat room
    
    
    private static final Room kitchen = new Room(20, 20, "kitchen");
    private static final Room basement = new Room(12, 18, "basement");
    private static final Room room1 = new Room(24, 20, "room1");
    private static final Room room2 = new Room(12, 20, "room2");
    
    private static ArrayList<Room> roomList = new ArrayList<>();
    private Voter voterThread;
    
    private static boolean isHeatingOn = false;
    
    private static final Integer MAX_VOTES = 4;
    private static final Integer COOLING_VOTE_MODIFIER = 1;

    private static Integer VotesForTurnOnHeating = 0;
    private static Integer VotesForTurnHeatingOff = 0;
    
    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
        SHEnv.isHeatingOn = true;
        roomList.add(kitchen);
        roomList.add(basement);
        roomList.add(room1);
        roomList.add(room2);
        
        //Initialize room target temperatures
        for(Room room: roomList) {
    		addPercept(room.name, Literal.parseLiteral("targetTemp("+ room.targetTemp +")"));
        }
        
        // add global percepts
		addPercept(HEATING_ON);
			
        voterThread = new Voter(this, roomList);
        voterThread.start();
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
    	for(Room room : roomList) {
    		if(room.name.equals(agName)) {
    			voterThread.setVoteForRoom(room, action);
    		}
    	}
        return true; // the action was executed with success
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
    

    
}
