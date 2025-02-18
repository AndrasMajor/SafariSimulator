## User Stories

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
  **I want to:** load the saved game 

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The application is running, the "Load Game" button is visible, and there is a previously saved game stored in the memory |
  | **When**  | The "Load Game" button is clicked |
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
  | **Then**  | A confirmation dialog appeares with the choices of "Save" or "Exit"  |

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
  | **Then**  | The speed of the game changes (slow -> medium -> fast -> slow) |

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
  | **When**  | The choosen plant is clicked |
  | **Then**  | The plant is selected and Shop window closes|

  |       |                                                           |
  |-------|-----------------------------------------------------------|
  | **Given** | The Shop window is closed and a plant is selected |
  | **When**  | A valid tile is clicked (invalid tiles: rock, water, road) |
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
  | **When**  | The choosen animal is clicked |
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
  | **When**  | The "Keeper" icon is clicked |
  | **Then**  | The keepr is selected and Shop window closes|

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
  | **When**  | The Jeep icon is clicked |
  | **Then**  | The Jeep is added to the vehicles (the price is deducted from the money) |

</details>