package application;

import java.io.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

public class MainController {

	public static final String MAIN_CFG = Main.MAIN_CFG;

	@FXML
	private BorderPane Main_BorderPane1;

	@FXML
	private VBox Main_VBox1;

	@FXML
	private ImageView Main_ImageView1;

	@FXML
	private ImageView Main_ImageView11;

	@FXML
	private HBox Main_HBox1;

	@FXML
	private StackPane Main_StackPane1;

	@FXML
	private ImageView Main_ImageView21;

	@FXML
	private ImageView Main_ImageView211;

	@FXML
	private Label Main_Label1;

	@FXML
	private ImageView Main_ImageView2;

	@FXML
	private ListView<?> Main_ListView1;

	@FXML
	private StackPane Main_StackPane2;

	@FXML
	private ScrollPane Main_ScrollPane1;

	@FXML
	private TilePane Main_TilePane1;

	@FXML
	private ScrollPane Main_ScrollPane2;

	@FXML
	private ScrollPane Main_ScrollPane3;

	@FXML
	private ScrollPane Main_ScrollPane4;

	/**
	 * Event that left-clicking on the ListView 鼠标左击列表的触发器
	 * 
	 * @param event
	 *            Mouse input events
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	void Main_ListView1_MouseClicked(MouseEvent event) throws ClassNotFoundException, IOException {

		List<Object> StackPaneItems = new ArrayList<>(Arrays.asList(Main_StackPane2.getChildren().toArray()));
		List<String> ListViewItems = new ArrayList<>(
				Arrays.asList(ConfigHandle.getConfigStringData(MAIN_CFG, "ListViewItems")));
		List<Integer> ListViewEventSequence = new ArrayList<>();
		int[] aIntArray = ConfigHandle.getConfigIntData(MAIN_CFG, "ListViewEventSequence");
		for (int i : aIntArray) {
			ListViewEventSequence.add(i);
		}

		HashMap<Integer, String> hashMap = new UsefulToolkit().autoItemCountConformity(StackPaneItems, ListViewItems,
				ListViewEventSequence);
		Object item = Main_ListView1.getSelectionModel().getSelectedItem();

		if (item != null) {
			for (int i = 0; i < hashMap.size(); i++) {
				// 过时方法!
				String urlString = ((ImageView) item).getImage().impl_getUrl();
				String imageName = urlString.substring(urlString.lastIndexOf("/") + 1, urlString.lastIndexOf("."));

				if (imageName.equals(hashMap.get(i))) {
					for (int j = 0; j < hashMap.size(); j++) {
						ScrollPane AllPane = (ScrollPane) Main_StackPane2.lookup("#Main_ScrollPane" + (j + 1));
						AllPane.setVisible(false);
					}
					ScrollPane ActivityPane = (ScrollPane) Main_StackPane2.lookup("#Main_ScrollPane" + (i + 1));
					ActivityPane.setVisible(true);
				}
			}
		}
	}

}
