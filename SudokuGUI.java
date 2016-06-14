import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.*; 
import java.io.*;
import javafx.scene.input.*;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;
import javafx.application.Platform;

public class SudokuGUI extends Application {
	// Board properties
	private Brett board;
	private int rowsInBox;
	private int columnsInBox;
	private boolean solveBeenCalled = false;
	// Solution properties
	private int[][] original;
	private Solution theFirstSolution;
	private SudokuBeholder sb;
	// GUI properties
	private Stage primaryStage;
	private BorderPane borderPane = new BorderPane();
	private String filepath;
	private Label solutionsLabel = new Label();
	private Button nextSolution = new Button("Show next solution");

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		HBox topBar = getTopBar();
		
		readFile();
		
		borderPane.setTop(topBar);
		borderPane.setBottom(new StackPane(solutionsLabel));

		Scene scene = new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sudoku v. 1.0");
		primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(1));
   		primaryStage.minHeightProperty().bind(scene.widthProperty().divide(1));
		primaryStage.show();
	}
/**
* Constructs and returns a HBox object that is supposed to be set as a top bar.
*
* @return HBox	HBox object.
*/
	public HBox getTopBar() {
		Button solve = new Button("Solve");
		solve.setMaxWidth(Double.MAX_VALUE);
		solve.setMaxHeight(Double.MAX_VALUE);
		solve.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Solve?");
				alert.setHeaderText("Are you sure you want to solve the sudoku? This will lead to the board filled with the right numbers.");
				alert.setContentText("ATTENTION! Finding a solution may take some time. All you current progress will be lost.");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					if (solveBeenCalled) {
						int[][] solution = theFirstSolution.getASolution2D();
						for(int i = 0; i < solution.length; i++) {
							for (int j = 0; j < solution[i].length; j++) {
								if (!board.getBoard()[i][j].isSet()) {
									board.getBoard()[i][j].setNr(solution[i][j]);
								}
							}
						}
						fillInnTheBoard(board);
						solutionsLabel.setText("Original board had " + board.getSudoku().amountFoundSolutions);
					    solutionsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
					}else{
						for(int i = 0; i < original.length; i++) {
							for (int j = 0; j < original[i].length; j++) {
								if (!board.getBoard()[i][j].isSet()) {
									board.getBoard()[i][j].setNr(original[i][j]);
								}
							}
						}
						board.solve();
						sb = board.getSudoku();
						theFirstSolution = board.getSudoku().taUt();
						int[][] solution = theFirstSolution.getASolution2D();
						for(int i = 0; i < solution.length; i++) {
							for (int j = 0; j < solution[i].length; j++) {
								if (!board.getBoard()[i][j].isSet()) {
									board.getBoard()[i][j].setNr(solution[i][j]);
								}
							}
						}
						fillInnTheBoard(board);
						solutionsLabel.setText("Solution: " + SudokuBeholder.counter + "/" + board.getSudoku().amountFoundSolutions);
						solutionsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
						solveBeenCalled = true;
						nextSolution.setDisable(false);
					}
				}else return;
			}
		});

		Button restart = new Button("Restart");
		restart.setMaxWidth(Double.MAX_VALUE);
		restart.setMaxHeight(Double.MAX_VALUE);
		restart.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Restart?");
				alert.setHeaderText("If you want to restart, all your current progress is going to be deleted.");
				alert.setContentText("Are you sure you want to restart?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					for (Rute[] rr : board.getBoard()) {
						for (Rute r: rr) {
							if(!r.isSet()) r.setNr(0);
						}
					}
					fillInnTheBoard(board);
					if (solveBeenCalled) {
						solutionsLabel.setText("Original board had " + board.getSudoku().amountFoundSolutions);
					    solutionsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
					}
				}else{
					return;
				}
			}
		});

		Button hint = new Button("Give a hint!");
		hint.setMaxWidth(Double.MAX_VALUE);
		hint.setMaxHeight(Double.MAX_VALUE);
		hint.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Give a hint!");
				alert.setHeaderText("If you want to get a hint, all your current progress is going to be deleted.\nGetting a hint will also lead to losing of the original board state and reducing the amount of possible solutions.");
				alert.setContentText("Are you sure you want to get a hint?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
				    if (!solveBeenCalled) {
						for(int i = 0; i < original.length; i++) {
							for (int j = 0; j < original[i].length; j++) {
								if (!board.getBoard()[i][j].isSet()) {
									board.getBoard()[i][j].setNr(original[i][j]);
								}
							}
						}
						board.solve();
						sb = board.getSudoku();
						theFirstSolution = board.getSudoku().taUt();
						solveBeenCalled = true;
						boolean foundNotSetCell = false;
						for(int i = 0; i < theFirstSolution.getASolution2D().length; i++) {
							for (int j = 0; j < theFirstSolution.getASolution2D()[i].length; j++) {
								if (!foundNotSetCell && board.getBoard()[i][j].getNr() == 0){
									board.getBoard()[i][j] = new Rute(theFirstSolution.getASolution2D()[i][j], board);
									board.opprettDatastruktur();
									foundNotSetCell = true;
								}
							}
						}
						fillInnTheBoard(board);
						solutionsLabel.setText("Original board had " + board.getSudoku().amountFoundSolutions);
						solutionsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
					}else{
						nextSolution.setDisable(true);
						for(int i = 0; i < original.length; i++) {
							for (int j = 0; j < original[i].length; j++) {
								if (!board.getBoard()[i][j].isSet()) {
									board.getBoard()[i][j].setNr(original[i][j]);
								}
							}
						}
						boolean foundNotSetCell = false;
						for(int i = 0; i < theFirstSolution.getASolution2D().length; i++) {
							for (int j = 0; j < theFirstSolution.getASolution2D()[i].length; j++) {
								if (!foundNotSetCell && board.getBoard()[i][j].getNr() == 0) {
									board.getBoard()[i][j] = new Rute(theFirstSolution.getASolution2D()[i][j], board);
									foundNotSetCell = true;
								}
							}
						}
						fillInnTheBoard(board);
						solutionsLabel.setText("Original board had " + board.getSudoku().amountFoundSolutions + " solutions");
						solutionsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
					}
				} else {
				    return;
				}
			}
		});

		
		nextSolution.setDisable(true);
		nextSolution.setMaxWidth(Double.MAX_VALUE);
		nextSolution.setMaxHeight(Double.MAX_VALUE);
		nextSolution.setOnAction(showSolution -> {
			int[][] solution = sb.taUt().getASolution2D();
				for(int i = 0; i < solution.length; i++) {
					for (int j = 0; j < solution[i].length; j++) {
						if (!board.getBoard()[i][j].isSet()) {
							board.getBoard()[i][j].setNr(solution[i][j]);
						}
					}
				}
				fillInnTheBoard(board);
				solutionsLabel.setText("Solution: " + SudokuBeholder.counter + "/" + SudokuBeholder.amountFoundSolutions);
				solutionsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		});

		Button quit = new Button("Quit");
		quit.setPrefSize(100, 10);
		quit.setOnAction((quitTheProgram) -> { 
			Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Quit?");
				alert.setHeaderText("If you quit right now all your current progress is going to be deleted.");
				alert.setContentText("Are you sure you want to quit?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) Platform.exit();
				else return;
			});


		HBox topBar = new HBox(10, restart, hint, solve, nextSolution, quit);
		return topBar;
	}
/**
* Fills in the boards with the valid values.
*
*
*/
	public void fillInnTheBoard(Brett board) {
		GridPane gridpane = new GridPane();
		for(int row = 0; row < board.getBoard().length; row++) {
			for (int column = 0; column < board.getBoard()[row].length; column++) {
				StackPane stackpane = new StackPane();
				Rute cell = board.getBoard()[row][column];
				Button cellButton = new Button();
				Text text;

				int value = board.getBoard()[row][column].getNr();
				if(value != 0) {
					text = new Text(Integer.toString(value));
					text.setFont(Font.font("Impact", FontWeight.BOLD, 22));
					text.setFill(Color.BLACK);
					stackpane.getChildren().add(text);
				}else{
					stackpane.getChildren().add(cellButton);
				}
				
				cellButton.setFont(new Font(20));
				cellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						MouseButton button = event.getButton();
						if(button == MouseButton.PRIMARY) {
							int[] possibleValues = cell.finnAlleMuligeTall();
							cell.incr();
							boolean impossible = true;
							int valueInTheCell = cell.getNr();
							if (valueInTheCell == 0) cellButton.setText("");
							else cellButton.setText(Integer.toString(valueInTheCell));
							for (int i = 0; i < possibleValues.length; i++) {
								if(valueInTheCell == possibleValues[i]) {
									cellButton.setStyle("-fx-font: 20 impact; -fx-background-color: #ffffff; -fx-text-fill: #48cf00;");
									impossible = false;
								}
							}
							if (impossible) cellButton.setStyle("-fx-font: 20 impact; -fx-background-color: #ffffff; -fx-text-fill: red;");
							System.out.println("Left click " + cell.getNr());
						}
						if(button == MouseButton.SECONDARY) {
							int[] possibleValues = cell.finnAlleMuligeTall();
							cell.decr();
							boolean impossible = true;
							int valueInTheCell = cell.getNr();
							if (valueInTheCell == 0) cellButton.setText("");
							else cellButton.setText(Integer.toString(valueInTheCell));
							for (int i = 0; i < possibleValues.length; i++) {
								if(valueInTheCell == possibleValues[i]) {
									cellButton.setStyle("-fx-font: 20 impact; -fx-background-color: #ffffff; -fx-text-fill: #48cf00;");
									impossible = false;
								}
							}
							if (impossible) cellButton.setStyle("-fx-font: 20 impact; -fx-background-color: #ffffff; -fx-text-fill: red;");
							System.out.println("Right click " + cell.getNr());
						}
					}
				});
				cellButton.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				stackpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				int topBorder = 1;
				int rightBorder = 1;
				int botBorder = 1;
				int leftBorder = 1;
				if (column % columnsInBox == 0) topBorder = 5;
				if (column == board.getBoard()[row].length - 1) botBorder = 5; 
				if (row % rowsInBox == 0) leftBorder = 5;
				if (row == board.getBoard()[row].length - 1) rightBorder = 5;
				stackpane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(topBorder, rightBorder,botBorder, leftBorder))));
				gridpane.add(stackpane, row, column);
			}
		}

		for(int i = 0; i < rowsInBox*columnsInBox; i++) {
			ColumnConstraints kolonneStr = new ColumnConstraints();
			kolonneStr.setPercentWidth(100.0/rowsInBox*columnsInBox);
			gridpane.getColumnConstraints().add(kolonneStr);

			RowConstraints radStr = new RowConstraints();
			radStr.setPercentHeight(100.0/rowsInBox*columnsInBox);
			gridpane.getRowConstraints().add(radStr);
		}
		borderPane.setCenter(gridpane);
	}
/**
* Method that initializes the FileChooser while starting a program.
*
*/
	public void readFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a file with a sudoku board:");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		if (selectedFile == null) errorAlertThrower(new FileNotFoundException(), "The file with sudoku has not been chosen!", "The error leads to the termination of the program. Please restart the program and choose a file.");
		filepath = selectedFile.getPath();
		if (selectedFile != null) {
			String errorMessage = "The error leads to the termination of the program. Please check the given file, or chose another file while restarting the program.";
			try {
				FileOperator fo = new FileOperator();
				board = fo.lesFil(selectedFile);
				board.opprettDatastruktur();
				rowsInBox = fo.getParameter("row");
				columnsInBox = fo.getParameter("col");
				original = new int[rowsInBox*columnsInBox][rowsInBox*columnsInBox];
				for (int i = 0; i < board.getBoard().length; i++) {
					for (int j = 0; j < board.getBoard()[i].length; j++) {
						original[i][j] = board.getBoard()[i][j].getNr(); 
					}
				}
				fillInnTheBoard(board);
				System.out.println("Everything went fine!");
			}catch (FileNotFoundException e) {
				errorAlertThrower(e, "The file with sudoku has not been chosen!", "The error leads to the termination of the program. Please restart the program and choose a file.");
			}catch (IncorrectCharacterInFile e) {			
				errorAlertThrower(e, e.getMessage(), errorMessage);
			}catch (IllegalValueIntervalException e) {
				errorAlertThrower(e, e.getMessage(), errorMessage);
			}catch (IncorrectAmountOfSymbols e) {
				errorAlertThrower(e, e.getMessage(), errorMessage);
			}catch (TheBoardIsToLargeException e) {
				errorAlertThrower(e, e.getMessage(), errorMessage);
			}
		}
	}
	// The credit to the dialog window of alert belongs to http://code.makery.ch/blog/javafx-dialogs-official/
	// The code below was created to the guidlines of the url above.
/**
* Method that provides a basic structure for error alerts when an exception is thrown while running
* the program.
*
* @param Exception	The thrown exception.
* @param String	Header text for an Alert dialog window for the given exception.
* @param String	Content text for an Alert dialog window for the given exception.
*/
	public void errorAlertThrower(Exception e, String headText, String contText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR!");
		alert.setHeaderText(headText);
		alert.setContentText(contText);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
		Platform.exit();
	}
}