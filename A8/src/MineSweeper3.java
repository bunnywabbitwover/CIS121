import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;


//Andrea Batchelor 300219108

public class MineSweeper3 extends Application{
	static Timeline keepTime = new Timeline();
	static long getMillis = 0;
	Scene scene;
	static int xSize = 0;
	static int ySize = 0;
	static int numRow = 0;
	static int numCol = 0;
	static int bombs = 0;
	static boolean isFirstClicked = true;
	static int index = 0;
	static String gameLevel = "";

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage pS) {
		BorderPane pane = new BorderPane();
		Menu mainMenu = new Menu("Pick your level");
		MenuItem levelOne = new MenuItem("Beginner");
		MenuItem levelTwo = new MenuItem("Intermidate");
		MenuItem levelThree = new MenuItem("Expert");

		levelOne.setOnAction(e -> {
			numRow = 8;
			numCol = 8;
			bombs = 10;
			xSize = 269;
			ySize = 360;
			gameLevel = "Beginner";
			isFirstClicked = true;
			getMillis = 0;
			keepTime.stop();
			start(pS);

		});

		levelTwo.setOnAction(e -> {

			numRow = 16;
			numCol = 16;
			bombs = 40;
			xSize = 510;
			ySize = 605;
			gameLevel = "Intermidate";
			isFirstClicked = true;
			getMillis = 0;
			keepTime.stop();
			start(pS);

		});

		levelThree.setOnAction(e -> {

			numRow = 16;
			numCol = 32;
			bombs = 99;
			xSize = 995;
			ySize = 605;
			gameLevel = "Expert";
			isFirstClicked = true;
			getMillis = 0;
			keepTime.stop();
			start(pS);
		});

		mainMenu.getItems().addAll(
				levelOne,
				levelTwo,
				levelThree
				);

		MenuBar mBar = new MenuBar();
		mBar.getMenus().add(mainMenu);
		pane.setTop(mBar);

		FaceButton face = new FaceButton();
		Tile buttons[][] = new Tile[numRow][numCol];
		Tile countBombs = new Tile();
		Tile numOfTilesClicked = new Tile();
		countBombs.setBombs(bombs);

		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);

		int[][] tile = new int[numRow][numCol];
		gameBoard(bombs, tile, numRow, numCol);

		scene = new Scene(pane, xSize, ySize);

		for (int row = 0; row < tile.length; row++) {
			for(int col = 0; col < tile[row].length; col++) {
				buttons[row][col] = new Tile(tile[row][col]);
				Tile button = buttons[row][col];
				button.value = tile[row][col];
				button.setGraphic(button.imageCover);
				face.setGraphic(face.imageSmile);
				int r = row;
				int c = col;
				button.setOnMousePressed(e -> face.setGraphic(face.imageOface));
				button.setOnMouseReleased(e -> 	face.setGraphic(face.imageSmile));
				button.setOnMouseClicked(e ->{

					if(e.getButton() == MouseButton.SECONDARY) {
						rightClicked(pane, face, countBombs, button, buttons, tile, r, c);
					}
					if(e.getButton() == MouseButton.PRIMARY) {
						if(isFirstClicked) {
							keepTime = new Timeline(new KeyFrame(Duration.millis(1000), k -> {
								getMillis++;
								pane.setCenter(getFlowPane(face,countBombs, keepTime, getMillis));
							}));
							keepTime.setCycleCount(Timeline.INDEFINITE);
							keepTime.play();
							startZero(button, tile, buttons, countBombs, bombs, numRow, numCol, getMillis);
							isFirstClicked = false;
						}
						fastPlay(buttons, button, tile,  numRow,  numCol,  r,  c,  pane, face,  countBombs, numOfTilesClicked);
						leftClicked(pane, face, buttons, countBombs, numOfTilesClicked, tile, button, r, c);
					}		
				});

				face.setOnMouseClicked(e ->{
					face.setGraphic(face.imageSmile);
					for (int x = 0; x < tile.length; x++) {
						for(int y = 0; y < tile[x].length; y++) {
							tile[x][y] = 0;
						}
					}

					Tile.resetTiles(buttons, button, countBombs, bombs, tile, numRow, numCol, getMillis);
					isFirstClicked = true;
					getMillis = 0;
					pane.setCenter(getFlowPane(face,countBombs, keepTime, getMillis));

				});

				gp.add(buttons[row][col], col, row);
				pane.setBottom(gp);
				pane.setPadding(new Insets(6,6,6,6));
				pane.setCenter(getFlowPane(face,countBombs, keepTime, getMillis));
			}
		}
		pS.setScene(scene);
		pS.setTitle("MineSweeper");
		pS.show();
		mBar.setStyle("-fx-background-color: white;"
				+ "-fx-border-color: #BDBDBD;"
				+ "-fx-border-width: 0 0 2 0;");
		gp.setStyle("-fx-border-width:3px;"
				+ "-fx-border-insets: 5 0 0 0;"
				+ "-fx-border-color: #7b7b7b white white #7b7b7b;"
				+ "-fx-border-radius: 0.01;");
		pane.setStyle("-fx-background-color: #BDBDBD;"
				+ "-fx-border-width: 3 3 3 3;"
				+ "-fx-border-color: white #7b7b7b #7b7b7b white;"
				+ "-fx-border-radius: 0.01;");
	}
	//method suggested by Ashley Clark
	private void rightClicked(BorderPane pane, FaceButton face, Tile countBombs, Tile button, Tile[][] buttons, int[][] tile, int r, int c) {
		button = buttons[r][c];
		button.value = tile[r][c];
		if(!button.isFlagged) { //set flag
			button.isFlagged = true;
			button.setGraphic(button.flag);
			countBombs.bombs--;
			pane.setCenter(getFlowPane(face,countBombs, keepTime, getMillis));

		}
		else if(button.isFlagged){ //remove flag
			button.isFlagged = false;
			button.setGraphic(button.imageCover);
			countBombs.bombs++;
			pane.setCenter(getFlowPane(face,countBombs, keepTime, getMillis));
		}
	}
	//method suggested by Ashley Clark
	private static void leftClicked(BorderPane pane, FaceButton face, Tile[][] buttons, Tile countBombs, Tile numOfTilesClicked, int[][] tile, Tile button, int r, int c) {
		button = buttons[r][c];
		button.value = tile[r][c];
		if(!button.isFlagged){
			if(button.value == 9) { //lose condition
				face.setGraphic(face.imageDead);
				Tile.disableTiles(buttons, button);
				Tile.revealBomb(buttons, button);
				button.setGraphic(button.redBomb);
				keepTime.stop();
				pane.setCenter(getFlowPane(face,countBombs, keepTime, getMillis));

			} else {
				if(button.value == 0) { //call floodFill method 
					floodFill(r, c, numRow, numCol, buttons, tile);

				}else {
					button.state = 1; //set value of tile 1
					button.setGraphic(new ImageView(new Image("file:res/" + button.value +".png")));
				}
			}
		}
		if (numOfTilesClicked.tileState(buttons, button) == bombs) { //win conditions
			face.setGraphic(face.imageSunGlasses);
			keepTime.stop();
			pane.setCenter(getFlowPane(face,countBombs, keepTime, getMillis));
			Tile.disableTiles(buttons, button);
		}
	}
	//check in bound
	public static boolean isValid(int r, int c, int numRow, int numCol){
		return r >= 0 && r < numRow && c >= 0 && c < numCol;
	}
	//recursive method to start on a zero at being of game play powered by Sarah
	private static void startZero(Tile button, int[][] tile, Tile[][] buttons, Tile countBombs, 
			int bombs, int numRow, int numCol, long getMillis) {
		if(button.value == 0) {
			return;
		}

		for (int x = 0; x < tile.length; x++) {
			for(int y = 0; y < tile[x].length; y++) {
				tile[x][y] = 0;
			}
		}
		Tile.resetTiles(buttons, button, countBombs, bombs, tile, numRow, numCol, getMillis);
		if(button.value != 0) {
			startZero(button, tile, buttons, countBombs, bombs, numRow, numCol, getMillis);
		}
	}
	//rapid game play powered by Ken (with the help of Ashley Clark)
	private static void fastPlay(Tile buttons[][], Tile currentButton, int[][] tile, int numRow, int numCol, int row, int col, BorderPane pane, FaceButton face, Tile countBombs, Tile numOfTilesClicked) {
		int butVal = currentButton.value;
		int flagCount = 0;
		for (int r = row - 1; r <= row +1; r++) {
			for(int c =  col - 1; c <= col + 1; c++) {
				if(isValid(r, c, numRow, numCol)){
					Tile button = buttons[r][c];
					if(button.isFlagged){
						flagCount++;
					}
				}
			}
		}
		for (int r = row - 1; r <= row +1; r++) {
			for(int c =  col - 1; c <= col + 1; c++) {
				if(isValid(r, c, numRow, numCol)){
					Tile button = buttons[r][c];
					button.value = tile[r][c];
					if(flagCount == butVal && !button.isFlagged) {
						leftClicked(pane, face, buttons,  countBombs, numOfTilesClicked, tile, button, r, c);
					}
				}
			}
		}
	}
	//recursive method to floodFill empty powered by Ken
	private static void floodFill(int row, int col, int numRow, int numCol, Tile[][] buttons, int[][] tile){
		if(isValid(row, col, numRow, numCol)) {
			Tile button = buttons[row][col];
			button.value = tile[row][col];
			if(button.value != 0) {
				button.state = 1;
				button.tilesClicked = 1;
				button.setGraphic(new ImageView(new Image("file:res/" + button.value +".png")));
			}else {
				button.setGraphic(new ImageView(new Image("file:res/" + button.value +".png")));
				if(button.state == 0) {
					button.state = 1; 
					button.tilesClicked = 1;

					floodFill(row - 1, col - 1, numRow, numCol, buttons, tile);//up right
					floodFill(row - 1, col, numRow, numCol, buttons, tile);//up
					floodFill(row - 1, col + 1, numRow, numCol, buttons, tile);// up left
					floodFill(row, col - 1, numRow, numCol, buttons, tile);//right
					floodFill(row, col + 1, numRow, numCol, buttons, tile);//left
					floodFill(row + 1, col - 1, numRow, numCol, buttons, tile);// down right
					floodFill(row + 1, col, numRow, numCol, buttons, tile); //down
					floodFill(row + 1, col + 1, numRow, numCol, buttons, tile);//down left
				}
			}
		}
	}
	//generate gameBoard
	private static void gameBoard(int bombs, int[][] tile, int numRow, int numCol) {
		int countMine = 0;
		while(countMine < bombs) {
			int x = (int)(Math.random() * numRow);
			int y = (int)(Math.random() * numCol);
			if(tile[x][y] == 0) {
				tile[x][y] = 9;
				countMine++;
			}
		}
		for (int row = 0; row < tile.length; row++) {
			for(int col = 0; col < tile[row].length; col++) {
				countMine = 0;
				for(int r = row - 1; r <= row + 1; r++) {
					for(int c = col - 1; c <= col + 1; c++) {
						if(isValid(r,c, numRow, numCol) && tile[r][c] == 9) { //check out of bounds
							countMine++;
						}
					}
				}
				if(tile[row][col] < 9 ) {
					tile[row][col] = countMine;
				}
			}
		}		
		for(int[] arr: tile) {
			for(int num : arr) {
				System.out.print(num + " "); //print out array of gameBoard
			}
			System.out.println();
		}
		System.out.println();
	}
	//set Header
	private static FlowPane getFlowPane(FaceButton face, Tile countBombs, Timeline keepTime, long getMillis) {
		FlowPane header = new FlowPane();
		int hrd = countBombs.bombs/100;
		int ten = countBombs.bombs/10;
		int one = countBombs.bombs % 10;
		int t = (-countBombs.bombs/10 % 10) + 10 % 10;
		int o = (-countBombs.bombs % 10 + 10) % 10;

		ImageView flag100 = new ImageView(new Image("file:digits/" + hrd + ".png"));
		ImageView flag10 = new ImageView(new Image("file:digits/" + ten + ".png"));
		ImageView flag1 = new ImageView(new Image("file:digits/" + one + ".png"));

		if(countBombs.bombs < 0) {

			flag100 = new ImageView(new Image("file:digits/neg.png"));	
			flag10 = new ImageView(new Image("file:digits/" + t + ".png"));
			flag1 = new ImageView(new Image("file:digits/" + o + ".png"));
		}
		long hSec = getMillis / 100;
		long mSec = (getMillis%100) / 10;
		long sSec = getMillis % 10;

		ImageView time100 = new ImageView(new Image("file:digits/" + hSec + ".png"));
		ImageView time10 = new ImageView(new Image("file:digits/" + mSec + ".png"));
		ImageView time1 = new ImageView(new Image("file:digits/" + sSec + ".png"));

		header.setAlignment(Pos.CENTER);

		HBox bombHBox = new HBox();
		bombHBox.setAlignment(Pos.CENTER_LEFT);
		bombHBox.setPadding(new Insets(0,0,6,0));

		HBox timeHBox = new HBox();
		timeHBox.setAlignment(Pos.CENTER_RIGHT);
		timeHBox.setPadding(new Insets(0,0,6,0));

		HBox faceHBox = new HBox();
		faceHBox.setAlignment(Pos.CENTER);
		faceHBox.setPadding(new Insets(0,12,6,12));

		timeHBox.getChildren().addAll(
				time100,
				time10,
				time1
				);
		faceHBox.getChildren().add(face);

		bombHBox.getChildren().addAll(
				flag100,
				flag10,
				flag1);
		header.getChildren().addAll(bombHBox, faceHBox, timeHBox);

		header.setStyle("-fx-border-width:2px;"
				+ "-fx-border-color: #7b7b7b white white #7b7b7b;"
				+ "-fx-border-style: solid;"
				+ "-fx-border-radius: 0.01;");
		bombHBox.setStyle("-fx-border-width:1px;"
				+ "-fx-border-color: #7b7b7b white white #7b7b7b;"
				+ "-fx-border-style: solid;"
				+ "-fx-border-radius: 0.01;");
		timeHBox.setStyle("-fx-border-width:1px;"
				+ "-fx-border-color: #7b7b7b white white #7b7b7b;"
				+ "-fx-border-style: solid;"
				+ "-fx-border-radius: 0.01;");
		return header;
	}

	class FaceButton extends Button{

		int state;
		ImageView imageSmile;
		ImageView imageDead;
		ImageView imageSunGlasses;
		ImageView imageOface;
		String w;
		Image win;
		public FaceButton() {
			state = 0;
			double size = 50;
			setMinWidth(size);
			setMaxWidth(size);
			setMinHeight(size);
			setMaxHeight(size);
			setPadding(Insets.EMPTY);
			w = "file:res/face-win.png";
			imageOface = new ImageView(new Image("file:res/face-O.png"));
			imageSmile = new ImageView(new Image("file:res/face-smile.png"));
			imageDead = new ImageView(new Image("file:res/face-dead.png"));
			win = new Image(w);
			imageSunGlasses = new ImageView();
			imageSunGlasses.setImage(win);
		}
	}

	class Tile extends Button{

		boolean isFlagged = false;
		boolean isMisFlagged = false;
		int state = 0; //state becomes 1 when clicked then count  
		int value;
		int bombs = 0; 
		int flagState = 0;
		ImageView imageCover;
		ImageView flag;
		ImageView redBomb;
		ImageView misFlag;
		int tileCounter = 0;
		int tilesClicked;
		int getBombs;
		int row; 
		int col; 

		public Tile() {}

		public Tile(int value) {
			value =0;
			state = 0;
			bombs = 0;
			imageCover = new ImageView(new Image("file:res/cover.png"));
			flag = new ImageView(new Image("file:res/flag.png"));
			redBomb = new ImageView(new Image("file:res/red-mine.png"));
			misFlag = new ImageView(new Image("file:res/mine-misflagged.png"));
			row = 0;
			col= 0;

			double size = 30;
			setMinWidth(size);
			setMaxWidth(size);
			setMinHeight(size);
			setMaxHeight(size);
			setPadding(Insets.EMPTY);
		}

		public Tile(int row, int col){
			value = 0;
			this.row = row;
			this.col = col;
		}

		public int getBombs() {
			return bombs;
		}

		public void setBombs(int bombs) {
			this.bombs = bombs;
		}
		//show bombs
		private static void revealBomb(Tile buttons[][], Tile button) {
			for (int row = 0; row < buttons.length; row++) {
				for(int col = 0; col < buttons[row].length; col++) {
					button = buttons[row][col];
					if(button.value == 9) {
						button.setGraphic(new ImageView(new Image("file:res/9.png")));
					}
					if(button.isFlagged && button.value==9) {
						button.setGraphic(button.flag);
					}
					else if (button.isFlagged && button.value!=9) {
						button.setGraphic(button.misFlag);
					}
				}
			}
		}
		//reset game when clicking face button
		public static void resetTiles(Tile buttons[][], Tile button, Tile countBombs, int bombs, int[][] tile, int numRow, int numCol, long getMillis) {
			gameBoard(bombs, tile, numRow, numCol);
			for (int row = 0; row < buttons.length; row++) {
				for(int col = 0; col < buttons[row].length; col++) {
					button = buttons[row][col];
					button.state = 0;
					button.value = tile[row][col];
					button.isFlagged= false;
					countBombs.setBombs(bombs);
					button.setGraphic(button.imageCover);
					button.setDisable(false);
					getMillis = 0;
					isFirstClicked = true;
				}
			}
		}
		//stop tile from being clicked
		private static void disableTiles(Tile buttons[][], Tile button) {
			for (int row = 0; row < buttons.length; row++) {
				for(int col = 0; col < buttons[row].length; col++) {
					button = buttons[row][col];
					button.setDisable(true);
					button.setStyle("-fx-opacity: 1");
				}
			}
		}
		//used for win condition
		private  int tileState(Tile buttons[][], Tile button) {
			tilesClicked = 0;
			for (int row = 0; row < buttons.length; row++) {
				for(int col = 0; col < buttons[row].length; col++) {
					button = buttons[row][col];
					if(button.state == 0) {
						tilesClicked++;
					}
				}
			}
			return tilesClicked;
		}
	}
}


