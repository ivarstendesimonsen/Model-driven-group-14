package cardgame.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cardgame.core.Player;
import cardgame.core.Table;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class StartMenuController {
	Table table;
	List<PlayerController> controllerList;
	List<Stage> stages;
	private boolean sliderChanged;
	@FXML
	private Slider sliderPlayers;
	@FXML
	int sliderPlayerValue;
	@FXML
	void initialize() {
		controllerList = new ArrayList<PlayerController>();
		stages = new ArrayList<Stage>();

	}
	@FXML
	void onSliderChanged() {
		sliderPlayerValue =  (int) Math.round(sliderPlayers.getValue());
		sliderChanged=true;
	}
	@FXML
	void startNewGame() {
		if (sliderChanged==true)
			table=new Table(sliderPlayerValue);
		else
			table=new Table((int) Math.round(sliderPlayers.getValue()));
		for (Player player:table.getPlayers()) {
			try {
				PlayerController playercontroller = new PlayerController(this.table,player);
				FXMLLoader playerfxml = new FXMLLoader(getClass().getResource("/cardgame/ui/Player.fxml"));
				playerfxml.setController(playercontroller);
				Parent root =playerfxml.load();


				Scene scene = new Scene(root);
				Stage stage= new Stage();

				stage.setScene(scene);
				stage.show();
				stage.setOnCloseRequest(event -> playercontroller.bootPlayer(player));
				stage.setTitle("Player"+(table.getPlayers().indexOf(player)+1));
				controllerList.add(playercontroller);
				table.addListeners(playercontroller);
				stages.add(stage);

			} catch (IOException e) {
				e.printStackTrace();
		}
	}

	}



}
