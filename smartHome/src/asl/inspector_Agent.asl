// Agent chamber_Agent in project smartHome

/*
 * Wants to keep the room cold
 * 
 */

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("Hello World").

+temp(Room, Temp) : targetTemp(T) & Temp >= T  <- 
.print("DONT WANT HEAT");
.my_name(Name);
.send(voter_Agent,tell,vote(Name,heat_off)).

+temp(Room, Temp) : targetTemp(T) & Temp < T  <- 
.print("WANT HEAT");
.my_name(Name);
.send(voter_Agent,tell,vote(Name,heat_on)).
