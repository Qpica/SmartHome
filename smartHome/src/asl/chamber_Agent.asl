// Agent chamber_Agent in project smartHome

/*
 * Wants to keep the room cold
 * 
 */

/* Initial beliefs and rules */

targetTemp(12).

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("hello world.").	

+temp(Room, Temp) : Temp > 12 <- cool(Room). 



