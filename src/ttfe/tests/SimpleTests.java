package ttfe.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ttfe.MoveDirection;
import ttfe.SimulatorInterface;
import ttfe.TTFEFactory;

/**
 * This class provides a very simple example of how to write tests for this project.
 * You can implement your own tests within this class or any other class within this package.
 * Tests in other packages will not be run and considered for completion of the project.
 */
public class SimpleTests {

	private SimulatorInterface game;
	private SimulatorInterface gameTest;
	private int previousPoints;

	@Before
	public void setUp() {
		game = TTFEFactory.createSimulator(4, 4, new Random(0));
	}
	
	@Test
	public void testInitialGamePoints() {
		assertEquals("The initial game did not have zero points", 0,
				game.getPoints());
	}
	
	@Test
	public void testInitialBoardHeight() {
		assertTrue("The initial game board did not have correct height",
				4 == game.getBoardHeight());
	}
	private SimulatorInterface gameAddPiece;
	@Test
	public void testWrongAddPiece1() {
		gameAddPiece = TTFEFactory.createSimulator(2, 2, new Random(0));
		gameAddPiece.setPieceAt(0, 0, 4);
		gameAddPiece.setPieceAt(0, 1, 8);
		gameAddPiece.setPieceAt(1, 1, 16);
		gameAddPiece.setPieceAt(1, 0, 16);
		assertThrows (IllegalStateException.class,() -> {
			gameAddPiece.addPiece();
		});	
	}
	private SimulatorInterface game1;
	@Test
	public void testNumMoves() {
		game1 = TTFEFactory.createSimulator(2, 2, new Random(0));
		assertEquals(0, game1.getNumMoves());
		game1.performMove(MoveDirection.EAST);
		assertEquals(1, game1.getNumMoves());
		game1.performMove(MoveDirection.WEST);
		assertEquals(2, game1.getNumMoves());
		game1.performMove(MoveDirection.EAST);
		assertEquals(3, game1.getNumMoves());
		game1.performMove(MoveDirection.WEST);
		assertEquals(4, game1.getNumMoves());
	}
	private SimulatorInterface gameMovePossible;
	public void setFor4(int x0y0,int x1y0,int x0y1,int x1y1) {
		gameMovePossible.setPieceAt(0, 0, x0y0);
		gameMovePossible.setPieceAt(0, 1, x0y1);
		gameMovePossible.setPieceAt(1, 0, x1y0);
		gameMovePossible.setPieceAt(1, 1, x1y1);
	}
	public void allTrue() {
		assertTrue(gameMovePossible.isMovePossible());
		assertTrue(gameMovePossible.isMovePossible(MoveDirection.EAST));
		assertTrue(gameMovePossible.isMovePossible(MoveDirection.NORTH));
		assertTrue(gameMovePossible.isMovePossible(MoveDirection.SOUTH));
		assertTrue(gameMovePossible.isMovePossible(MoveDirection.WEST));
	}
	public void allFalse() {
		assertNotEquals(gameMovePossible.isMovePossible(), true);
		assertNotEquals(gameMovePossible.isMovePossible(MoveDirection.EAST), true);
		assertNotEquals(gameMovePossible.isMovePossible(MoveDirection.NORTH), true);
		assertNotEquals(gameMovePossible.isMovePossible(MoveDirection.SOUTH), true);
		assertNotEquals(gameMovePossible.isMovePossible(MoveDirection.WEST), true);
	}
	public void checkTrue(int north,int south,int west,int east){
		if (north == 1) assertTrue(gameMovePossible.isMovePossible(MoveDirection.NORTH));
		else assertNotEquals(gameMovePossible.isMovePossible(MoveDirection.NORTH), true);
		if (south == 1) assertTrue(gameMovePossible.isMovePossible(MoveDirection.SOUTH));
		else assertNotEquals(gameMovePossible.isMovePossible(MoveDirection.SOUTH), true);
		if (west == 1) assertTrue(gameMovePossible.isMovePossible(MoveDirection.WEST));
		else assertNotEquals(gameMovePossible.isMovePossible(MoveDirection.WEST), true);
		if (east == 1) assertTrue(gameMovePossible.isMovePossible(MoveDirection.EAST));
		else assertNotEquals(gameMovePossible.isMovePossible(MoveDirection.EAST), true);	
	}
	public void setFor16 (int x0y0,int x1y0,int x2y0,int x3y0,int x0y1,int x1y1,int x2y1,int x3y1,int x0y2,int x1y2,int x2y2,int x3y2,int x0y3,int x1y3,int x2y3,int x3y3, SimulatorInterface name){
		name.setPieceAt(0, 0, x0y0);
		name.setPieceAt(1, 0, x1y0);
		name.setPieceAt(2, 0, x2y0);
		name.setPieceAt(3, 0, x3y0);
		name.setPieceAt(0, 1, x0y1);
		name.setPieceAt(1, 1, x1y1);
		name.setPieceAt(2, 1, x2y1);
		name.setPieceAt(3, 1, x3y1);
		name.setPieceAt(0, 2, x0y2);
		name.setPieceAt(1, 2, x1y2);
		name.setPieceAt(2, 2, x2y2);
		name.setPieceAt(3, 2, x3y2);
		name.setPieceAt(0, 3, x0y3);
		name.setPieceAt(1, 3, x1y3);
		name.setPieceAt(2, 3, x2y3);
		name.setPieceAt(3, 3, x3y3);
	}
	public void setFull16(SimulatorInterface name){
		setFor16(2,4,2,4,4,2,4,2,2,4,2,4,4,2,4,2, name);
	}
	@Test
	public void testmovepossible() {
		gameMovePossible = TTFEFactory.createSimulator(2, 2, new Random(0));
		allTrue();

		setFor4(0, 0, 0, 0);
		allFalse();

		setFor4(2, 4, 8, 16);
		allFalse();

		setFor4(2, 2, 4, 4);
		checkTrue(0, 0, 1, 1);

		setFor4(2, 4, 2, 4);
		checkTrue(1, 1, 0, 0);

		setFor4(2, 2, 0, 0);
		checkTrue(0, 1, 1, 1);

		setFor4(2, 4, 0, 0);
		checkTrue(0, 1, 0, 0);

		setFor4(0, 0, 2, 2);
		checkTrue(1, 0, 1, 1);

		setFor4(0, 0, 2, 4);
		checkTrue(1, 0, 0, 0);
		
		setFor4(2, 0, 2, 0);
		checkTrue(1, 1, 0, 1);

		setFor4(2, 0, 4, 0);
		checkTrue(0, 0, 0, 1);

		setFor4(0, 2, 0, 2);
		checkTrue(1, 1, 1, 0);

		setFor4(0, 2, 0, 4);
		checkTrue(0, 0, 1, 0);

		setFor4(0, 2, 2, 0);
		allTrue();

		setFor4(2, 0, 0, 2);
		allTrue();

		setFor4(0, 2, 4, 8);
		checkTrue(1, 0, 1, 0);

		setFor4(2, 4, 8, 0);
		checkTrue(0, 1, 0, 1);



		game.setPieceAt(0, 3, 0);
		game.setPieceAt(1, 3, 2);
		game.setPieceAt(2, 3, 4);
		game.setPieceAt(3, 3, 0);
		assertTrue(game.isMovePossible());
		assertTrue(game.isMovePossible(MoveDirection.WEST));
		assertTrue(game.isMovePossible(MoveDirection.EAST));
		setFull16(game);
		assertNotEquals(game.isMovePossible(), true);
		assertNotEquals(game.isMovePossible(MoveDirection.EAST), true);
		assertNotEquals(game.isMovePossible(MoveDirection.WEST), true);
		assertNotEquals(game.isMovePossible(MoveDirection.NORTH), true);
		assertNotEquals(game.isMovePossible(MoveDirection.SOUTH), true);
		setFor16(
		0,4,2,4,
		0,2, 4,2,
		0,	4,2,4,
		0,2,4,2, 
		game);
		assertTrue(game.isMovePossible());
		assertNotEquals(game.isMovePossible(MoveDirection.EAST), true);
		assertTrue(game.isMovePossible(MoveDirection.WEST));
		assertNotEquals(game.isMovePossible(MoveDirection.NORTH), true);
		assertNotEquals(game.isMovePossible(MoveDirection.SOUTH), true);
		setFor16(
		0,4,2,0,
		0,2, 4,0,
		0,	4,2,0,
		0,2,4,0, 
		game);
		assertTrue(game.isMovePossible());
		assertTrue(game.isMovePossible(MoveDirection.EAST));
		assertTrue(game.isMovePossible(MoveDirection.WEST));
		assertNotEquals(game.isMovePossible(MoveDirection.NORTH), true);
		assertNotEquals(game.isMovePossible(MoveDirection.SOUTH), true);
		setFor16(
		2,4,2,0,
		4,2, 4,0,
		2,	4,2,0,
		4,2,4,0, 
		game);
		assertTrue(game.isMovePossible());
		assertTrue(game.isMovePossible(MoveDirection.EAST));
		assertNotEquals(game.isMovePossible(MoveDirection.WEST), true);
		assertNotEquals(game.isMovePossible(MoveDirection.NORTH), true);
		assertNotEquals(game.isMovePossible(MoveDirection.SOUTH), true);
		setFor16(
		0,0,0,0,
		4,2, 4,2,
		2,	4,2,4,
		4,2,4,2, 
		game);
		assertTrue(game.isMovePossible());
		assertNotEquals(game.isMovePossible(MoveDirection.EAST), true);
		assertNotEquals(game.isMovePossible(MoveDirection.WEST), true);
		assertTrue(game.isMovePossible(MoveDirection.NORTH));
		assertNotEquals(game.isMovePossible(MoveDirection.SOUTH), true);
		setFor16(
		0,0,0,0,
		4,2, 4,2,
		2,	4,2,4,
		0,0,0,0, 
		game);
		assertTrue(game.isMovePossible());
		assertNotEquals(game.isMovePossible(MoveDirection.EAST), true);
		assertNotEquals(game.isMovePossible(MoveDirection.WEST), true);
		assertTrue(game.isMovePossible(MoveDirection.NORTH));
		assertTrue(game.isMovePossible(MoveDirection.SOUTH));
		setFor16(
		2,4,2,4,
		4,2, 4,2,
		2,	4,2,4,
		0,0,0,0, 
		game);
		assertTrue(game.isMovePossible());
		assertNotEquals(game.isMovePossible(MoveDirection.EAST), true);
		assertNotEquals(game.isMovePossible(MoveDirection.WEST), true);
		assertNotEquals(game.isMovePossible(MoveDirection.NORTH), true);
		assertTrue(game.isMovePossible(MoveDirection.SOUTH));
	}
	private int pieces = 2;
	@Test
	public void testgetnumpieces(){
		gameTest = TTFEFactory.createSimulator(4, 4, new Random(0));
		
		assertEquals(2, gameTest.getNumPieces());
		if (gameTest.getPieceAt(1, 1) == 0){
		gameTest.setPieceAt(1, 1, 2);
		pieces++;
		}
		assertEquals(pieces, gameTest.getNumPieces()); 
		if (gameTest.getPieceAt(2, 2) == 0){
			gameTest.setPieceAt(2, 2, 2);
			pieces++;
			}
		assertEquals(pieces, gameTest.getNumPieces()); 
		if (gameTest.getPieceAt(3, 3) == 0){
			gameTest.setPieceAt(3, 3, 2);
			pieces++;
			}
		assertEquals(pieces, gameTest.getNumPieces()); 
		gameTest.setPieceAt(3, 3, 0);
		pieces--;
		assertEquals(pieces, gameTest.getNumPieces()); 

	}
	private SimulatorInterface gameTestSpace1;
	@Test
	public void testIsSpaceLeft(){
		gameTestSpace1 = TTFEFactory.createSimulator(2, 2, new Random(0));
		gameTestSpace1.setPieceAt(0, 0, 2);
		gameTestSpace1.setPieceAt(1, 0, 2);
		gameTestSpace1.setPieceAt(0, 1, 2);
		gameTestSpace1.setPieceAt(1, 1, 0);

		assertTrue(gameTestSpace1.isSpaceLeft());
		gameTestSpace1.setPieceAt(1, 1, 2);
		assertTrue(gameTestSpace1.isSpaceLeft() != true);
	}
	private SimulatorInterface testPerformMove;
	@Test
	public void testPerform(){
		testPerformMove = TTFEFactory.createSimulator(2, 2, new Random(0));
		assertThrows(IllegalArgumentException.class, () -> {
			testPerformMove.performMove(null);
		});
		testPerformMove.setPieceAt(0, 0, 2);
		testPerformMove.setPieceAt(1, 0, 2);
		assertTrue(testPerformMove.performMove(MoveDirection.EAST));
		assertEquals(4, testPerformMove.getPieceAt(1, 0));
		testPerformMove.setPieceAt(0, 0, 2);
		testPerformMove.setPieceAt(1, 0, 2);
		assertTrue(testPerformMove.performMove(MoveDirection.WEST));
		assertEquals(4, testPerformMove.getPieceAt(0, 0));
		testPerformMove.setPieceAt(0, 0, 2);
		testPerformMove.setPieceAt(0, 1, 2);
		assertTrue(testPerformMove.performMove(MoveDirection.NORTH));
		assertEquals(4, testPerformMove.getPieceAt(0, 0));
		testPerformMove.setPieceAt(0, 0, 2);
		testPerformMove.setPieceAt(0, 1, 2);
		assertTrue(testPerformMove.performMove(MoveDirection.SOUTH)); 
		assertEquals(4, testPerformMove.getPieceAt(0, 1));
		testPerformMove.setPieceAt(1, 0, 4);
		testPerformMove.setPieceAt(0, 0, 8);
		testPerformMove.setPieceAt(1, 1, 16);
		testPerformMove.setPieceAt(0, 1, 32);
		assertNotEquals(testPerformMove.performMove(MoveDirection.EAST), true);
		assertNotEquals(testPerformMove.performMove(MoveDirection.WEST), true);
		assertNotEquals(testPerformMove.performMove(MoveDirection.NORTH), true);
		assertNotEquals(testPerformMove.performMove(MoveDirection.SOUTH), true);
	}
	private SimulatorInterface testPoints;
	@Test
	public void testPointsGame(){
		testPoints = TTFEFactory.createSimulator(2, 2, new Random(0));
		assertEquals(0, testPoints.getPoints());
		testPoints.setPieceAt(0, 0, 4);
		testPoints.setPieceAt(0, 1, 8);
		testPoints.setPieceAt(1, 1, 16);
		testPoints.setPieceAt(1, 0, 16);
		testPoints.performMove(MoveDirection.NORTH);
		assertEquals(32, testPoints.getPoints());
		testPoints.setPieceAt(1, 1, 8);
		testPoints.performMove(MoveDirection.WEST);
		assertEquals(48, testPoints.getPoints());
		testPoints.setPieceAt(0, 0, 4);
		testPoints.setPieceAt(0, 1, 4);
		testPoints.setPieceAt(1, 1, 16);
		testPoints.setPieceAt(1, 0, 16);
		testPoints.performMove(MoveDirection.NORTH);
		assertEquals(88, testPoints.getPoints());
		testPoints.setPieceAt(1, 1, 0);
		testPoints.setPieceAt(0, 1, 0);
		testPoints.performMove(MoveDirection.SOUTH);
		assertEquals(88, testPoints.getPoints());
	}

}