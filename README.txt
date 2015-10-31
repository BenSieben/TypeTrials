//Header
Type Trials
By Ben Sieben
Created May 2013


//Description
     Type Trials is a game that is not new for sure, but it does have a twist on the traditional 
programs that try to teach people how to efficiently touch type. Touch typing is a very important 
skill for people that frequently use computers, as it helps to reduce time spent typing out words. 
The goal of this game is to make for a fun experience while keeping touch typing skills sharp or 
maybe even try to learn touch typing (this last purpose is not served very well in this program's 
design and setup). The basics of the game is that words appear on screen, one by one. What the user
needs to do is exactly type out what is on the screen to progress to the next word that needs to be 
typed. As stated before, this program should help teach people to refine their touch typing skills to 
the point where very little to no vision of the keyboard is needed for someone to type out words, 
sentences, and essays. This game includes multiple modes of playing as well as 
many customizable options to make the game more fleshed out. For instance, there is an "arcade" 
mode where the user always does a set amount of words (20) of similar length (length can be 
determined by a difficult setting, ranging from "short" to "long" words). During this, a score 
is kept depending on how well the user types the words. Longer combos award larger points per key 
typed, while mistypes stop the combo and remove points. The combo also has a timer to make sure that 
the user does not take long amounts of time to type each character (this will hopefully encourage 
the user to learn to type quickly and accurately). At the end, the user can submit their score 
with a name and make a local leaderboard. With this, people will hopefully be encouraged to play the 
game over and over to refine their skills and increase their high score. Another mode would be the 
"time attack" mode, where the user selects a difficulty and gets a set time limit to type out as 
many words as they can before the time runs out. Likewise, TimeAttack will punish mistyping by 
reducing the time left to type more words out. A feature includes the customizable list, 
where the user is free to add to the lists for the "short", "lengthy", and other word length types 
to add unlimited potential to the possible words. One other mode that could be present is a 
"practice" mode where only single letters show up on the screen so the user can learn where single 
letters are on their keyboard.
*Words retrieved from: 
(<http://simple.wiktionary.org/wiki/Wiktionary:Extended_Basic_English_alphabetical_wordlist>)


//Instructions
     Navigate the menus with the mouse. A left click is a select, where that mode or option is 
selected. Pressing the ESC key will cause the program to exit to the menu while not at the menu 
or if already at the menu, the program will exit.


//Class List
TypeTrials - Holds main method that uses all the other classes to run the program

Mode - A superclass that sets what all modes of gameplay need

ArcadeMode - Extending Mode, the "arcade" version of playing the game

TimeAttackMode - Extending Mode, the "time attack" version of playing the game

PracticeMode - Extending Mode, the "practice" version of playing the game

TypeGame - Manages what is being drawn on program window and communicates between the panels

TypeMenu - Draws the main menu and tells TypeGame where to go based on mouse clicks

WordManager - Takes care of reading and editing the lists for the various difficulties of words 
as well as the leaderboards

Score - A class that represents a single score from either Arcade or Time Attack

Leaderboard - An array of scores that can sort the scores

LeaderboardPanel - Manages which Leaderboard to show on the screen based on mouse clicks

HelpPanel - Draws the help screen that tells how to use the program

OptionPanel - Draws the option screen, where some tweaks to the program can be made by the user
