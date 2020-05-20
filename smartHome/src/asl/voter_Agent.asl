/*
 * Collects the Votes and publishes it to the Environment
 */

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("I am a Voter Collector").

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