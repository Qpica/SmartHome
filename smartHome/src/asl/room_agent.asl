// Agent chamber_Agent in project smartHome

/*
 * Wants to keep the room cold
 * 
 */

/* Initial beliefs and rules */


/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("hello world from chamber agent").


+temp(Temp) :  targetTemp(Target) & Temp > Target <- heat_off.

+temp(Temp) :  targetTemp(Target) & Temp <= Target <- heat_on.
