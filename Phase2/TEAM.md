# CSC207 Project Phase 2 Team Agreement

## Contact Information

### Team Member 1
* Name: Tzu-Ching Yen
* Email: thomson.yen@mail.utoronto.ca
* Phone: 647-563-4795
* Git username: thomsonyen 

### Team Member 2
* Name: Jiahe Lyu
* Email: jiahe.lyu@mail.utoronto.ca
* Phone: (647) 916-9782
* Git username: Jiahe Lyu

### Team Member 3
* Name: Zhuoyue Lyu (me)
* Email: zhuoyue.lyu@mail.utoronto.ca
* Phone: (647) 685-4909
* Git username: Zhuoyue Lyu

## Team Contract
* I will get my allotted work done on time.
* I will attend every team meeting and actively participate.
* Should an emergency arise that prevents me from attending a team meeting, I will notify my team immediately.
* The work will be divided roughly equally among all team members.
* I will help my team to understand every concept in the project.
* If I do not understand a concept or code, I will immediately ask my team for help.
* I will notify my team members before making major changes to the project.

## Major Controbution
##### Tzu-Ching Yen: 
* Design and implement the game "Memory"
* Implemented feature to make “Sliding Tile” game solvable
* Unit tests for some of “Sliding Tile” and most of “Memory”
##### Jiahe Lyu:
* Design and implement the game "2048"
* Extract to superclasses including “GenericTile”, “GenericBoard”, “GenericBoardManager”, “GenericStartingActivity” and “GenericGameActivity”
* Unit tests for all about game “2048”
##### Zhuoyue Lyu (me):
* Refactor all activity classes (Follow MVC) to View and Controller
* Build SQLite databaseHelper, scoreboard/leaderboard (Adapter pattern) 
* Wrote Instrumented tests for all controllers. Redesign the UI and regroup classes into 5 packages.


## Meeting Minutes

### Meeting 1: November 11, 2018
##### Place: BA 3195
* Tzu-Ching Yen join the team
* Generally discussed the project goal and responsibilities

### Meeting 2: November 14, 2018
##### Place: BA 3195
* Discussed the general idea of the project and came up with some questions to clarify
* Design our own classes and compare and discuss
* Divided the tasks among team members: Tzu-Ching Yen -- Memory Game, Jiahe Lyu -- 2048 Game, Zhuoyue Lyu -- JUnit test
* Tzu-Ching Yen set up the repository for the group

### Meeting 3: November 17, 2018, 
##### Place: John M.Kelly Library
* Tzu-Ching Yen and Jiahe Lyu discuss the possibility of having an unified design for three games
* Zhuoyue Lyu talked about the difference between test and instrumented test
* Asked the questions on piazza

### Meeting 4: November 21, 2018
##### Place: BA 3195
* We improve the overall design for the game, trying to incorporate thoughts of future design into our design
* Tzu-Ching Yen came up with the design that can potentially remove all static fields
* Jiahe Lyu tried to unify three GameActivity and StartingActivity to generic ones
* Zhuoyue Lyu finished the implementation of the Instrumented test for DatabaseHelper

### Meeting 5: November 24, 2018
##### Place: BA 3195
* We discussed the task and work that still need to be done in the last week of work 
* Tzu-Ching Yen Realize the “Memory Game”. Start working on unit tests
* Jiahe Lyu implemented some functionalities of “2048” like score display, new undo approach and tiles’ appearance
* Zhuoyue Lyu talked about the bug in Database, it seems that it can't store the negative score, then we realized that it's just a mistake in getComplexity

### Meeting 6: November 28, 2018
##### Place: BA 3195
* Tzu-Ching Yen Trying to add design pattern like Builder and Singleton to give refactor code
* Jiahe Lyu tested all unittests and the game flows, searching for unexpected bugs
* Zhuoyue Lyu regrouping the code into separate packages

### Meeting 7: November 30, 2018
##### Place: home

* We started at 9:30 PM ( After finished our 258 ) and worked until 8:30 AM the next day.
* Tzu-Ching Yen General improvements like deleting unused code, give javadoc, cleaning up
* Jiahe Lyu improved all generic classes for more generality and usage
* Zhuoyue Lyu pulling the controller of all the activities and wrote the test for them



