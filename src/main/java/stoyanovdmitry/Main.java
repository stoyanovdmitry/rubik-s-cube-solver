package stoyanovdmitry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/cube-scene.fxml"));
		root.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
		primaryStage.setTitle("Rubik's Cube Solver");
		primaryStage.setScene(new Scene(root));
		primaryStage.setMinHeight(825);
		primaryStage.setMinWidth(900);
		primaryStage.show();
	}
}