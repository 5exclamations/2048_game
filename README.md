# 2048

The 2048 sliding-tile game in Java, written as a university programming project (Saarland University).

- `Simulator` implements the game rules: moves, merging, scoring, random tile spawning and game-over detection
- `GUI` renders the board with Swing; `HumanPlayer` reads keyboard input
- `ComputerPlayer` plays automatically with a depth-4 expectimax search over player moves and random tile spawns
- `tests/` and `publictests/` contain JUnit tests for the simulator

Entry point: `ttfe.TTFE`. Options: `--player`, `--width`, `--height`, `--seed`.
