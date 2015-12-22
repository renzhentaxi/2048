import Model.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class My2048App extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Board board = new Board(4, 4);
        board.add(2);
        BoardView boardView = new BoardView(board);
        boardView.update();
        Scene scene = new Scene(boardView, 800, 600);
        boardView.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
