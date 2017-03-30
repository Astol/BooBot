# BooBot

This is a discord bot built using the discord4j library.

## Commands
### Prefix = '!!'
The commands bellow should be used with the prefix '!!'. List will
be updated when new commands are implemented.

`help` posts a message with a link to this page. 

`logoff` logs the bot out if you are the owner specified in program.

`quiz` starts the quiz game as described below.
 
 
## !!Quiz game
This creates a quiz question and publishes it in the channel. 
<br />Example 1:
```
Quiz Category: Entertainment: Film

Who plays Alice in the Resident Evil movies? 

!!a Milla Jovovich
!!b Milla Johnson
!!c Kim Demp
!!d Madison Derpe
````
Example 2:

```
Quiz Category: History 

Theodore Roosevelt Jr. was the only General involved in the initial assault on D-Day. 

!!true or !!false?
````

Rules:
1. Anyone in the channel can answer, first answer ends the question.
2. You must answer within 12 seconds or your answer will not be processed.
3. You can not start a new question while another question is active.