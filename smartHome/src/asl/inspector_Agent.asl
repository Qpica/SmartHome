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

+temp(Room, Temp) : targetTemp(T) & Temp > T  <- .print(" I want to COOL"); .send(voter_Agent,tell,heat_off).

+temp(Room, Temp) : targetTemp(T) & Temp < T  <- .print(": I want to HEAT"); .send(voter_Agent,tell,heat_on).

+temp(Room, Temp) : targetTemp(T) & Temp == T  <- .print(": I am HAPPY"); .send(voter_Agent,tell,heat_off).
