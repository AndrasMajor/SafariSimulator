# Plans
<details>
<summary>Class diagram</summary>
![uml_diagram](diagrams/Class_UML_diagrams/uml_diagram.png)
<h3>Updated class diagram<h3>
![uml_diagram_updated](uploads/80da3248b6925c1605049632adb1762c/uml_diagram_updated.png)
</details>

<details>
<summary>Use-Case diagram</summary>
![use_case_dia](uploads/0e19110ae152932d0cbc390bc4107406/use_case_dia.png)
</details>

# Requirements
<details>
<summary>Functional Requirements</summary><br>


# Main Menu:
- It should be possible to start a new game.
- It should be possible to exit the application.
- It should be possible to load a saved game.
- It should be possible to set the difficulty level.

# Setup Phase:
- It should be possible to exit the game.
- It should be possible to return to the main menu (with or without saving).
- It should be possible to open the in-game shop.
- It should be possible to place plants and paths.
- It should be possible to buy animals, jeeps and gamekeepers.
- Purchased units can be sold in this phase with a 100% refund (only before starting the game).
- It should be possible to start the simulation.

# Simulation:
- All functions of the setup phase should be available except for starting the game
- After loading the game the selling mechanic has to change to 50% refund
- Game speed has to be adjustable (pause, hours, days, weeks)/second
- When money runs out game ends (defeat)
- After reaching x amount of money game ends (victory)
- When time runs out game ends (defeat)
- User can save the actual state of the game after pressing exit button

# Objects and NPCs

## Animals
### Herbivores
- Herbivores move around the map randomly in groups if they can (max number of herd members is 10).
- When Mating season is called if the group the animal is in meets the parameters new animals of the group's kind are spawned on the map.
- A herd has to move in the general direction the oldest animal is moving to
- When feeding or when they are chased animals move in their own direction and leave the group
- When animal has no group and has an animal of the same species in sight they create a group the older animal dictates the general direction
- Hunger bar has to decrease monotonously after X seconds
- If Hunger bar is not full and plant type tile is in sight for animal it should move directly to the tile and eat until hunger bar is full
- If Hunger Bar reaches zero hp of animal decreases each second
- When damaged by carnivore or poacher hp of animal decreases
- When Carnivore or Poacher is in sight move into opposite direction of carnivore
- When hunger is full and health bar is not health starts to regenerate
- When animal dies it leaves x amount of food value for carnivores to eat
- When animal reaches certain age it dies.

### Carnivores 
- Carnivores move around the map randomly when healthy
- Carnivores move in groups of maximum 5.
- Carnivores move in the same general direction as the group leader (if in group) 
- Hunger bar has to decrease monotonously after X seconds
- If Hunger Bar reaches zero hp of animal decreases each second
- When damaged by poacher hp of animal decreases
- When Herbivore is in sight speed increases by 2 and carnivore starts to chase animal until it is out of sight or is eliminated
- Carnivores eat from the prey they catch.
- When carnivore has food in sight and the prey is not yet damaged the animals priority shifts to eating food
- When hunger is full and health bar is not health starts to regenerate

## Poachers
- Has to spawn randomly on the edges of the map. 
- When an animal is in sight starts to attack it until it eliminates the animal or the animal is out of sight.
- When it is attacked by gamekeepers and health falls below 50% it starts to escape to the edge of the map where it despawns.

## Gamekeepers 
- Wander around the map randomly
- When User clicks on gamekeeper is under Users controll (can be ordered to move to specific location or can be ordered to attack poacher.
- Health of gamekeeper has to regenerate over time when it isn't full.
- When Poacher is in sight auto gamekeeper attacks poacher until it is out of sight or dead.

## Tourists
- Travel in Jeeps on the road path built by the user in jeep
- Happiness rating increases when tourist spots an animal.
- Tourists only travel in Jeeps in designated road. 
- They spawn in numbers equivalent to the popularity of the park that is calculated off of the average happiness of tourists.

## Jeeps
- Jeeps move on the road built.
- Jeeps have to slow down when animal is in sight
- Jeeps have to stop when animal moves across the road infront of them 
- If all Jeeps are used no more tourists can enter the park.

## Plants
- If hp is not full it regenerates gradually
- If herbivore is eating the plant hp decreases.
- If Animal is on plant tile camo of animal increases
</details>






<details>
<summary>Non-Functional Requirements</summary>
<br>

# Product Requirements

## Efficiency

- Minimal load on the processor, memory, and storage.
- Fast response time to all inputs, even on low-end computers.

## Reliability

- No errors occur during the default use of the game.
- The game does not crash unexpectedly.

## Security

- The game does not connect to the internet and does not store personal data, making security concerns irrelevant.

## Portability

- Does require installation.
- Guaranteed compatibility with Windows 8, 10, and 11, Linux, MacOS.

## Usability

- The game interface is intuitive and easy to understand for anyone.
- No external guides or instructions are necessary.
- One user

## Management Requirements

### Environmental

- The game does not integrate with or connect to any external services or softwares.

### Operational

- Usually medium runtime: 1-3 hours.
- Frequent usage.
- Designed for one user, with no specialized knowledge required.

### Development

- Programming language: Java.
- Environment: jre.
- Object-oriented paradigm.
- Clean Code principles.
- Unit Testing.
- Git, GitLab.

## External Requirements

### Legal and Ethical Considerations

- The game's style and content do not harm the university's reputation.

</details>

# GUI
<details>
<summary>Wireframe</summary>

![Main_Menu](uploads/66bf43292454922c643e38ebe9c3609e/Main_Menu.png)

![New_Game](uploads/af95368b36fe7186ddcb090fa22dfa1b/In_Game.png)

![Load_Game](uploads/c43a675d330e6d526bf11d0509aeada5/Load_Game.png)

![In_Game](uploads/03d241f40a8ef731df1e2f890e667dc1/In_Game.png)

![Shop](uploads/09f3f29e55957d2af6b07c8260b5a9d3/Shop.png)

![Leave_Game](uploads/626f18c632492f73a252d12d3a149e05/Leave_Game.png)

</details>

<details>
<summary>UI</summary>

![Képernyőfotó_2025-05-25_-_15.07.11](uploads/54992931fce1dd02dabdb4bd27e9bfe6/Képernyőfotó_2025-05-25_-_15.07.11.png)

![Képernyőfotó_2025-05-25_-_15.07.40](uploads/9fd6f1e3689fd79d0f2cc34eec5ea230/Képernyőfotó_2025-05-25_-_15.07.40.png)

![Képernyőfotó_2025-05-25_-_15.07.48](uploads/2eb0d4514decb3facd45136c08838bb1/Képernyőfotó_2025-05-25_-_15.07.48.png)

![Képernyőfotó_2025-05-25_-_15.07.53](uploads/bba615138bb070f345b933ba456550c4/Képernyőfotó_2025-05-25_-_15.07.53.png)

![Képernyőfotó_2025-05-25_-_15.08.06](uploads/dddd8e6f6b36bb22a25394332df061ec/Képernyőfotó_2025-05-25_-_15.08.06.png)

![Képernyőfotó_2025-05-25_-_15.07.58](uploads/cbac54e241f764bd99f0cddf90977314/Képernyőfotó_2025-05-25_-_15.07.58.png)

</detauls>

# User Stories
<details>
<summary>All Stories</summary>
<details>
  <summary><b>New Game</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** start a new game 

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The application is running, and the "New Game" button is visible |
  | **When**  | The "New Game" button is clicked |
  | **Then**  | The difficulty selection window appears|

<br>

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The difficulty selection window is visible |
  | **When**  | A difficulty option is selected and the "Start" button is clicked |
  | **Then**  | A newly generated game board appears in its initial state, and the game begins |

</details>

<br>

<details>
  <summary><b>Load Game</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** load a saved game 

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The application is running, the "Load Game" button is visible, and there is at least one previously saved game stored in the memory |
  | **When**  | The "Load Game" button is clicked |
  | **Then**  | A new window appears with a list of previously saved games |

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The window with the list of previously saved games is active |
  | **When**  | The selected map is clicked |
  | **Then**  | The previously saved game state and map are loaded, and the game resumes from where it was left off |

</details>

<br>

<details>
  <summary><b>Exit Game</b></summary>

<br>

  **As a:** (current) player <br>
  **I want to:** exit the game  

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The game is running |
  | **When**  | The "Exit" button is clicked |
  | **Then**  | A confirmation dialog appears with the choices of "Save" or "Exit" |

<br>

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The confirmation dialog about exiting the game is active |
  | **When**  | The "Exit" button is clicked |
  | **Then**  | The game closes |

</details>


<br>


<details>
  <summary><b>Save Game</b></summary>

<br>

  **As a:** (current) player <br>
  **I want to:** save the game  

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The game is running |
  | **When**  | The "Exit" button is clicked |
  | **Then**  | A confirmation dialog appears with the choices of "Save" or "Exit"  |

<br>

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The confirmation dialog about exiting the game is active |
  | **When**  | The "Save" button is clicked |
  | **Then**  | The game closes |

</details>

<br>

<details>
  <summary><b>Pause Game</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** pause the game

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The game is running |
  | **When**  | The "Pause" button is clicked |
  | **Then**  | The game pauses (the timer and the entities stop) |

</details>

<br>

<details>
  <summary><b>Resume Game</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** resume the game

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The game is paused |
  | **When**  | The "Resume" button is clicked |
  | **Then**  | The game resumes (the timer and the entities continue) |

</details>

<br>

<details>
  <summary><b>Change Speed</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** change the speed of the game

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The game is running |
  | **When**  | The "Change Speed" button is clicked |
  | **Then**  | The speed of the game changes (slow -> medium -> fast -> slow -> ...) |

</details>

<br>

<details>
  <summary><b>Open Shop</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** open the shop

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The game is running and the Shop window is closed |
  | **When**  | The "Shop" button is clicked |
  | **Then**  | The Shop window appears |

</details>

<br>

<details>
  <summary><b>Close Shop</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** close the shop

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The game is running and the Shop window is open |
  | **When**  | The "Close Shop" button is clicked |
  | **Then**  | The Shop window closes |

</details>

<br>

<details>
  <summary><b>Buy (and) Place Plant</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** buy and place a plant

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The Shop window is open |
  | **When**  | The choosen plant is clicked, and we have the right amount of money to buy the selected plant |
  | **Then**  | The plant is selected and Shop window closes|

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The Shop window is closed and a plant is selected |
  | **When**  | A valid tile is clicked (invalid tiles: rock, water, road, plants) |
  | **Then**  | The plant is placed on the selected tile (the price of the plant is deducted from the money)|

</details>

<br>

<details>
  <summary><b>Buy (and) Place Animal</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** buy and place a plant

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The Shop window is open |
  | **When**  | The choosen animal is clicked, and we have the right amount of money to buy the selected animal |
  | **Then**  | The animal is selected and Shop window closes|

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The Shop window is closed and a animal is selected |
  | **When**  | A valid tile is clicked (invalid tiles: rock, water) |
  | **Then**  | The animal is placed on the selected tile (the price of the animal is deducted from the money)|

</details>

<br>

<details>
  <summary><b>Buy (and) Place Keeper</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** buy and place a plant

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The Shop window is open |
  | **When**  | The "Keeper" icon is clicked, and we have the right amount of money to buy the keeper |
  | **Then**  | The keeper is selected and Shop window closes|

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The Shop window is closed and the keeper is selected |
  | **When**  | A valid tile is clicked (invalid tiles: rock, water) |
  | **Then**  | The keeper is placed on the selected tile (the price of the keeper is deducted from the money)|

</details>

<br>

<details>
  <summary><b>Buy Jeep</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** buy and place a jeep

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The Shop window is open |
  | **When**  | The Jeep icon is clicked, and we have the right amount of money to buy the jeep |
  | **Then**  | The Jeep is added to the vehicles (the price is deducted from the money) |

</details>

 <br>

<details>
  <summary><b>Buy (and) Place Road</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** buy and place a road

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The Shop window is open |
  | **When**  | The Road icon is clicked, and we have the right amount of money to buy the road |
  | **Then**  | The Road is selected and the Shop window closes |

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The Shop window is closed and the road is selected |
  | **When**  | A valid tile is clicked (invalid tiles: rock, water, road) |
  | **Then**  | The road is placed on the selected tile (the price of the road is deducted from the money)

</details>

<br>

<details>
  <summary><b>Sell an Entity</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** sell an entity (animals, plants, roads, jeeps, lakes, keepers)

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The game is running and the "Start Selling" button is visible |
  | **When**  | The "Start Selling" button is clicked and then the selected entity is clicked on the map |
  | **Then**  | The clicked entity is sold and is removed from the map, the price of the sold entity gets added to the money |

</details>

<br>
<details>
  <summary><b>Turn off selling mode</b></summary>

<br>

  **As a:** (current) player  <br>
  **I want to:** turn off selling mode

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The game is running, the selling mode is on and the "Stop Selling" button is visible |
  | **When**  | The "Stop Selling" button is clicked |
  | **Then**  | The selling mode is turned off |

</details><br>
</details>

# Feasibility plans

<details>
<summary></summary> <br>

## Human Resources  
- Three designers/developers/testers  

## Hardware Resources  
- Three development computers (medium hardware requirements)  

## Software Resources  
- Development environment: InetelliJ IDEA, jrl
- Engine: libgdx
- Version control: Git  
- Project management platform: GitLab  

## Operations  
- No operational support required  

## Maintenance  
- No maintenance required beyond potential bug fixes  

## Implementation  
- Duration:  
- Cost: 
</details>
