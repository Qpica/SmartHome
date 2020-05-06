// Agent kitchen_Agent in project smartHome

/*
 * Wants to keep the room at a normal temp e.g. 20-21 °C
 */

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("I am a Voter Collector").

//+heat_off[source(S)] : true <- .print("I received a call from ", S).

//+heat_on[source(S)] : true <- .print("I received a call from ", S).


+vote(S,heat_off) : true <- 
+judge.

+vote(S,heat_on) : true <- 
+judge.

+judge : .count(vote(_,_),4) <-
.print("DECIDING WHAT TO DO");
.count(vote(_,heat_on), COn);
.print("FOR HEATING: ", COn);
.count(vote(_,heat_off), COff);
.print("FOR NOT HEATING: ", COff);
.abolish(vote(_,_));
if	(COn > COff) { heating_on; .abolish(judge)}
else { heating_off; .abolish(judge)}.

+judge : true <- .abolish(judge).