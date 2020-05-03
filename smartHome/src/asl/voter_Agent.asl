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

+heat_off[source(S)] : true <- 
+vote(S,heat_off);
.count(vote(_,heat_off), COff); 
.print("I have received so much HEAT_OFF: ", COff);
.abolish(heat_off);
!countVotes.


+heat_on[source(S)] : true <- 
+vote(S,heat_on);
.count(vote(_,heat_on), COn); 
.print("I have received so much HEAT_ON: ", COn);
.abolish(heat_on);
!countVotes.

+!countVotes : .count(vote(_,_), Votes) & Votes == 4 <-
.print("COUNTING VOTES");
.count(vote(_,heat_on), COn);
.print("FOR HEATING: ", COn);
.count(vote(_,heat_off), COff);
.print("FOR NOT HEATING: ", COff);
if	(COn > COff) { heating_on}
else { heating_off}.

//+!countVotes : true <- .print("").