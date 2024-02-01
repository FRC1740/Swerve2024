# Swerve 2024 code
1740's swerve repository for the FRC 2024 season. This is our 2024 codebase. To make changes to the repo, follow the [Github Setup in VSCode](#github-vscode-setup-tutorial), Then see the [Style Guide](#style-guide) 
### Robot Physical Specifications
* 26 * 26 inches (add height)
* camera positions
* Swerve specifications
* general position and idea GroundIntake
* The Horn
The Horn is the main intake and shooter on our robot. It can score in the speaker and amp. It currently has no trap support.
* general position and idea Climber

### Subsystems
#### Drive Train
* 8 motor REV MAXswerve system
* SparkMAX controllers
* Spark ID Numbers 1-8 clockwise starting from FrontLeft
* Odd: Drive; Even: Turn

#### Climber
* num of NEOs
* how it works

#### GroundIntake
* 1 NEO 
* It runs a motor to a bar that intakes notes into the horn

#### Horn
* 2 Neos
* A flat sheet to hold notes and 4 wheels on either side powered by Neos


#### Path Information
* Name: function and position

### Software Todo List (sorted by priority)
`Current things to test`
- [ ] Drive to point
- [ ] Test error tab
- [ ] Dpad rotation delay (probably needs to be fixed) BROKEN
- [ ] Shuffleboard logging (scary) maybe
- [ ] OTF pathing 
- [ ] Swerve logging check
- [ ] Check drivewhile aligning tolerences
`People`
- [ ] Cordinate the software team and see who wants to help with software
- [ ] Inform software team of structure and git and PID
- [ ] Make sure they all have a solid understanding of the principles the code runs on ([see last year's repo](#last-year-repo))
- [ ] Make sure everyone has a solid understanding of java
- [ ] Cordinate tasks
- [ ] Assign some sort of laptop system

`General Software`
- [x] Update codebase to 2024.2 when full release is out so we aren't on version "2024.1.1-beta-2"! This involves changing: 
* Many SparkMAX declarations to Spark
* The version of the RoboRio so builds will succeed. (This is what is currently preventing update now)
- [ ] Finish readme [Robot Physical Specifications](#robot-physical-specifications)
- [x] Consider Advantage kit and/or scope data logging for the season [Scope](https://github.com/Mechanical-Advantage/AdvantageScope) [Kit](https://github.com/Mechanical-Advantage/AdvantageKit)

`Shuffleboard`
- [x] Set up the shuffleboard base code, (not the indivudal methods that use it) based off last years example
- [ ] Log trajectories
- [ ] Get some sort of auto previsualization so we can see it before using it
- [ ] Add logging to drive subsystem to see wheel angles and robot position
- [ ] Add logging for note subsystems
- [ ] Add climber logging

`Swerve`
- [X] Find out why turning motors were turning seemingly randomly and fix it
- [X] Fix the one random wheel that didn't turn correctly
- [X] Turn on field relitive control
- [x] Add pathplanning for autos
- [ ] Auto motor stop on tilt
- [x] Custom pathfinder with vision https://pathplanner.dev/pplib-pathfinding.html#custom-pathfinders
- [ ] Finish the system functionality
- [ ] Tune the system so it works well
- [ ] If apriltag vision pose esimation should take precedence in getPose, update it to get the pos with the limelight if able

`Controls`
- [x] Get drive controls working
- [x] Quadratic driving falloff
- [ ] Create control ideas for the driver and co driver (talk to Abby and Co-Driver)
- [x] Add delay to DPad input so when releasing two buttons it doesn't collapse into one
- [x] Right Stick angle control and click stick in to snap?
- [ ] Implement controls
- [ ] Make sure control feel is good and everything makes sense
- [ ] Different control selection from shuffleboard
- [ ] Controller rumble would be cool, could provide feedback on time left in match??

`Vision`
* Concurent work on limelight and lamelight
- [x] Import old code
- [x] Setup and update [limelight](https://docs.limelightvision.io/docs/docs-limelight/getting-started/summary)
- [x] Calibrate the limelight with the online tool
- [ ] Check and fix imported limelight subsystem
- [ ] Incorperate limelight table with shuffleboard
- [ ] Get current limelight tag id and adjust co-driver controls based off it.
- [ ] Consider Using limelight to track gamepieces 

* Lamelight
- [x] Import old code
- [x] Grab the lamelight from last years robot
- [x] Set up lamelight (Photon vision) ([see last year's repo](#last-years-repo))
- [x] Rename instances of vision with photonVision for clarity
- [ ] Incorperate lamelight table with shuffleboard
- [ ] Calibrate lamelight
- [ ] Automatic Bumper Detection would be really cool

`Climber`
- [ ] Actually figure out what the mechanics entail and how it works
- [ ] Find out how many motors it is
- [ ] Create stub code for testing
- [ ] See [Shuffleboard](#Shuffleboard)

`Horn`
- [x] Figure out what this is called and rename it here
- [x] Actually figure out what the mechanics entail and how it works
- [x] See how the flap works
- [x] Create stub code for testing
- [x] See [Shuffleboard](#Shuffleboard)
- [ ] Add a homing mode where if nothing is happening it centers a note

`Ground Intake`
- [ ] See how the ground intake works
- [ ] Create stub code
- [ ] Create subsystem
- [ ] See [Shuffleboard](#Shuffleboard)

### Last Year's Repo
* :warning: This is intended as a place of reference to see the general structure, not to copy code without understanding it
* [2023 Souce Code](https://github.com/crephoto/CommandBased_2023)

### Important Devolopment Resources
This is the main resource we use besides googling things, this contains most, if not all the answers you need
* [Wpilib Documentation](https://docs.wpilib.org/en/stable/docs/zero-to-robot/introduction.html)
* [Java Wpilib Api](https://github.wpilib.org/allwpilib/docs/release/java/index.html)
* [Limelight Vision Documentation](https://docs.limelightvision.io/docs/docs-limelight/getting-started/summary)
* [The Most Important Resource](https://www.google.com)
* If you need any further help or explainations, feel free to talk to me, or any of the software mentors.


### Github VSCode Setup Tutorial 
There are 2 different methods to setup git, I am leaving the [legacy method](legacy-method) as reference, but we switched to an easier and faster branched solution more widely used professionally. The old method is still useful if you need to make a change to a repository you don't have access to.
* Create a [Github account](https://github.com)
* Ask Mr. Estabrooks to add you to his repository
* Clone the repository via VSCode
* Go to the third tab on the left
* Click on the three dots, and click create branch
* Name it something like dev-[name] or dev-[initals]
* Click publish branch
#### Pulling
* Ideally this should be done every time you open your code and as often as possible to avoid conflicts
* If it has been a long time since you have worked it's a good idea to pull so you don't get a lot of merge conflicts
* To pull code, make sure you have no pending changes (if you do, see [Pushing Code](#pushing))
* Run "git pull"
* If there are conflicts ask for advice if you don't know what you're doing (If you are reading this you don't)
#### Pushing
* Now that you have setup your github you can edit code on your working branch
* Make sure your changes work and make sure it builds and deploys before commiting
* After you finish the changes you now should look to the left panel and click the third git source control icon
* Hit Commit and if you haven't saved, hit Save all and Commit Changes
* It will ask you to input a message, either through a file or the message box at the top, either type in box or in the file save, and hit the checkmark to submit it
* Sync changes
* Go to github and click the dev branch you are on via the branch button
* Click create pull request and follow the thing through the menus
* You can continue commiting with an unresolved pull request and subsequent commits will be appeneded to the request

### Legacy Method
This is a guide for setting up Github with VSCode
* Create a [Github account](https://github.com)
* Sign into the VSCode with Github via the person logo in the bottom left above the wheel
* Fork the repository through the button on github
* Copy the link to the forked repository
* Clone the forked repository with VSCode
* Open the terminal 
* Run git remote -v
* You should see 2 values, a fetch and a push for origin
* These are added automatically because you cloned the repository
* This now is your forked repository
* Paste this command into the terminal "git remote add upstream [Link to original repository you forked]"
* Run git remote -v again, and you should see 2 more values for upstream fetch and push
* Run git pull upstream main
* If this succeeds you probablly have it set up correctly for your main branch

* Now, you need to make a different branch for saftey
* Run git checkout -b [Branch Name]
* I recommend "[name]-working" as the branch name
* This will create a new branch, you can switch between them in the bottom left
* :warning: All changes should be done on a working branch or equivelent, not on main
* After use, you can delete it and re-sync your main branch
* Then, run git branch --set-upstream-to=origin
* See [Using Git](#using-git)

### Using Git
#### Pulling Code
* Ideally this should be done every time you open your code and as often as possible to avoid conflicts
* If it has been a long time since you have worked it's a good idea to pull so you don't get a lot of merge conflicts
* To pull code, make sure you have no pending changes (if you do, see [Pushing Code](#pushing-code))
* Change to your main branch
* Run git pull upstream main
* Change back to working
* Run git pull upstream [Branch Name]
* You're ready to get back to work!
#### Pushing Code
* Now that you have setup your github you can edit code on your working branch
* Make sure your changes work and make sure it builds and deploys before commiting
* After you finish the changes you now should look to the left panel and click the third git source control icon
* Hit Commit and if you haven't saved, hit Save all and Commit Changes
* It will ask you to input a message, either through a file or the message box at the top, either type in box or in the file save, and hit the checkmark to submit it
* Sync changes
* Go on github to your local fork of the repository
* Open a pull request via the contribute button
* If there are merge conflicts don't touch anything and ask someone who knows, it can be easily resolved but you can mess it up really bad
* Hopefully the code works and get accepted into the main repo
* Make sure to [pull](#pulling-code) code after pushing

[Team Git "How To"](https://docs.google.com/document/u/0/d/15Kb6Wxj8sjFqbPtVO2GGT9Oe1oe9GFOMFhPrwpMIeqQ/mobilebasic?pli=1)
[Additional Info](https://code.visualstudio.com/docs/sourcecontrol/overview)

### Style Guide
If you are reading this, you have configured git and are set up to write code. Here we use two space indenting, so you need to go to the VSCode setting and change the default tab spacing for the workspace. It should be noted, this is a **guideline**, not hard rules; there will always be exceptions.
#### Functional
* When possible, static and final is always better, if it doesn't change, it should be marked as so. This mostly applies with constants in constant files
```java
public static final double kPXController = 1;
```
* Public vs Private
You should only make a variable public if it needs to be publicly accessible. For example,
```java
public static final double kPXController = 1;
```
Should be public because it needs to be accessed because it is a constant, but
```java
private DriveSubsystem m_robotDrive
```
In RobotContainer, should not be public because you should get a reference via RobotShared. When in doubt, it should be private and passed as an argument.
* File Placement
Files should be placed in a spot that makes sense. This is very subjective, but Subsystems should be placed in subsystems, and constants in constants ect. Sometimes, it makes sense to make a new folder, for example, you have a file "Horn Intake". It doesn't make sense to place it in commands, becuase it is simple with no PID, so, you place it in basic. But then, basic is getting a little crowded, so you can put it in a Horn folder.
#### Visual
##### Naming
* Variable Names
Local variables should be named with camelCase so the first letter is lowercase, note this affects variables with m_.
```java
int angleForDPad = 0;
```
* Constants
Constants should be written in PascalCase, so the first letter is uppercase and prefixed with a lowercase "k".
```java
public static final double kGyroAngularOffset = Math.PI / 2;
```
Generally, because the robot is so complex, abbreviating is frowned apon, however there are many exceptions.
* Subsystem Files
Files should be in PascalCase rather than camelCase, to denote importance. 
* Local subsystem refrenences
Local subsystem refrenences should be denoted with m_[subsystem's name]. This is to reduce confusion on what is a local reference because the m stands for my.
```java
private DriveSubsystem m_robotDrive;
```
##### Function calls
* Braces
Braces should be placed after the function a space after, not on a new line
* Parenthesis in functions
Parenthesis should be placed after the name with no space
```java
  private void configureButtonBindings() {
``` 
* Function wrapping:
If a function seems to be a lot for one line, you can separate it into multiple lines like in this example. Some code examples may use 2 tabs instead, but to stay consistent, use one.
```java
new RunCommand(() ->
  m_drive.drive(
    -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband), 
    -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband), 
    output, fieldRelative, rateLimit));
```
##### Math
Math should be coded with a space inbetween variables for clarity.
```java
public static final double kGyroAngularOffset = Math.PI / 2;
```