Project Documentation – Lutemon Battle Arena
Team Members: Saikhansurtalt Sumiyabazar 002329527, Erdene Otgonjargal 002340166, Inthuja Inthumathan 002599632
Project Description
Lutemon Battle Arena is a fully functional Android app inspired by Pokémon-style mechanics, developed using Java and Android Studio. The app allows users to create, train, and battle custom creatures called Lutemons of five types: White, Green, Pink, Orange, and Black.
The game uses object-oriented principles and implements a turn-based battle system against AI enemies. It offers a rich UI experience with animated visuals, sound effects, and persistent storage.

Implemented Features
🧱 Object-Oriented Programming (OOP)
•	Encapsulation: Lutemon properties (HP, attack, defense, experience) are private and managed through getters/setters.
•	Inheritance: A Lutemon superclass is extended by specific types like White, Green, etc.
•	Polymorphism: Common methods like takeDamage, defend, and getStats behave differently depending on the subclass.
🛠️ Android App Development
•	Developed entirely in Java using Android Studio.
•	Runs smoothly on Android phones using standard layouts and resources.
📦 Data Management
•	All Lutemons are stored using a HashMap within the LutemonStorage singleton.
•	Persistent storage is implemented using Java serialization, so user data survives app closure or phone restart.
🧬 Lutemon Creation & Training
•	Users can create new Lutemons and assign them a name.
•	Training boosts their attack and experience, with cooldown to prevent spam.
•	Lutemons can evolve, gaining permanent stat boosts.
⚔️ Turn-Based Battle System
•	Players choose 2 Lutemons to battle 2 AI-controlled random Lutemons.
•	Attack, Defend, and Switch actions are available.
•	AI behavior is randomized between attack and defend actions.
•	Defending reduces damage based on defense stat.
•	Battle ends when all Lutemons on one side are fainted.
🧠 AI Enemies
•	AI Lutemons are randomly generated with unique names, colors, and balanced stats.
•	Their behavior is partially randomized for more dynamic combat.
💾 Saving and Loading
•	Data is saved using file serialization (ObjectOutputStream).
•	Automatically loads saved Lutemons when app starts.
🖼️ User Interface
•	Themed buttons, progress bars, and background elements reflect a Pokémon-inspired visual style.
•	Sound effects for attack and KO are implemented using MediaPlayer.
•	Shake animation triggers on hit.
📊 Stats and Cooldown
•	Lutemons have stats displayed live during battle.
•	Training and evolving are restricted by cooldowns, preventing overuse.


🛠️ Custom Features Implemented
Feature	Description
Pokémon-style design	Styled images and theme
2v2 AI Battle system	Strategic and randomized
Train + Evolve + Rename/Delete	From adapter, with validation
Animation + Sounds	Shake, KO effects, and SFX
Persistent data	Filesystem save/load for long-term use
Experience + Evolution threshold	Gain EXP from training or battle
Dynamic UI updates	Health bars and stats update every turn



📌 Notes
•	We intentionally did not delete defeated Lutemons, but instead allowed them to retain XP and remain available for further training.
•	All code and documentation are written in English, as required.
•	The design follows clean, readable structure for maintainability.
This documentation was done by an AI analysis based on the project and we tried to include most of the features we have chosen to include in the game. Most of us had taken turns coding it on a shared repository, sharing ideas on which things to implement etc. Hope you enjoyed our work. Thank you!
