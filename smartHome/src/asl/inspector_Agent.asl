/*
 * Wants to keep the room at the desired temperature
 * 
 */

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("I am an Inspector").

+temp(Room, Temp) : targetTemp(T) & Temp >= T  <- 
.print("DONT WANT HEAT");
.my_name(Name);
.send(voter_Agent,tell,vote(Name,heat_off)).

+temp(Room, Temp) : targetTemp(T) & Temp < T  <- 
.print("WANT HEAT");
.my_name(Name);
.send(voter_Agent,tell,vote(Name,heat_on)).