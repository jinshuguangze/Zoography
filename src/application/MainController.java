package application;

import java.awt.event.MouseWheelEvent;
import java.io.*;
import java.util.*;
import java.util.function.DoubleToLongFunction;

import com.sun.glass.events.WheelEvent;
import com.sun.javafx.scene.layout.region.Margins;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public class MainController {
	public static final String LOGNAME_LOG = Main.LOGNAME_LOG;
	public static final String MAIN_CFG = Main.MAIN_CFG;
	public static final String BIOLOGY_CSV = Main.BIOLOGY_CSV;
	public static final HashMap<Integer, String> FILENUMBER = Main.FILENUMBER;

    @FXML
    private VBox Left;

    @FXML
    private ListView<?> MainListView;

    @FXML
    private HBox Right;

    @FXML
    private StackPane HBox_Left;

    @FXML
    private BorderPane MainListViewItem1;

    @FXML
    private TilePane Item1CenterFrame;

    @FXML
    private ImageView Item1LeftFrame;

    @FXML
    private ImageView Item1RightFrame;

    @FXML
    private ImageView Item1BottomFrame;

    @FXML
    private StackPane Item1TopFrame;

    @FXML
    private TextField TextField_Information;

    @FXML
    private Button Button_Inquiry;

    @FXML
    private Button Botton_Return;

    @FXML
    private BorderPane MainListViewItem2;

    @FXML
    private BorderPane MainListViewItem3;

    @FXML
    private StackPane Item3TopFrame;

    @FXML
    private TextField TextField_Information2;

    @FXML
    private Button Button_Inquiry2;

    @FXML
    private Button Botton_Return2;

    @FXML
    private ImageView Item3LeftFrame;

    @FXML
    private TilePane Item3CenterFrame;

    @FXML
    private ImageView Item3RightFrame;

    @FXML
    private ImageView Item3BottomFrame;

    @FXML
    private BorderPane MainListViewItem4;

    @FXML
    private BorderPane HBox_Right;

    @FXML
    private VBox Right_Center;

    @FXML
    private Label RightCenter_Title;

    @FXML
    private ImageView RightCenter_ImageView;

    @FXML
    private Label RightCenter_Label;

    @FXML
    private ImageView Right_Top;

    @FXML
    private ImageView Right_Right;

    @FXML
    private ImageView Right_Bottom;

    /**
     * Item1CenterFrame_Sroll
     * @param event The event
     * @throws ClassNotFoundException If fileName not found
     * @throws IOException If IO connection failed
     */
	@FXML
	void Item1CenterFrame_Sroll(ScrollEvent event) throws ClassNotFoundException, IOException {
		UsefulToolkit aToolkit = new UsefulToolkit();
		String fileName = TextField_Information.getText().replace(">", "_") + ".csv";
		int addon = (event.getDeltaY() > 0) ? -2 : +2;
		if (Item1CenterFrame.getChildren().size() > 0) {
			String text = ((Button) Item1CenterFrame.getChildren().get(0)).getText();
			int fromNumber = Integer.parseInt(text.substring(0, text.indexOf("\r\n"))) - 1;
			aToolkit.autoFillInterface(Item1CenterFrame, fileName, fromNumber + addon, TextField_Information,
					RightCenter_Title, RightCenter_ImageView, RightCenter_Label);
		}
	}
	
    /**
     * Item3CenterFrame_Sroll
     * @param event The event
     * @throws ClassNotFoundException If fileName not found
     * @throws IOException If IO connection failed
     */
	@FXML
	void Item3CenterFrame_Sroll(ScrollEvent event) throws ClassNotFoundException, IOException {
		UsefulToolkit aToolkit = new UsefulToolkit();
		String fileName = TextField_Information2.getText().replace(">", "_") + ".csv";
		int addon = (event.getDeltaY() > 0) ? -2 : +2;
		if (Item3CenterFrame.getChildren().size() > 0) {
			String text = ((Button) Item3CenterFrame.getChildren().get(0)).getText();
			int fromNumber = Integer.parseInt(text.substring(0, text.indexOf("\r\n"))) - 1;
			aToolkit.autoFillInterface(Item3CenterFrame, fileName, fromNumber + addon, TextField_Information2,
					RightCenter_Title, RightCenter_ImageView, RightCenter_Label);
		}
	}

	/**
	 * RightCenter_Label_Scroll
	 * @param event The event
	 */
	@FXML
	void RightCenter_Label_Scroll(ScrollEvent event) {
		double top = RightCenter_Label.getPadding().getTop();
		double right = RightCenter_Label.getPadding().getRight();
		double bottom = RightCenter_Label.getPadding().getBottom();
		double left = RightCenter_Label.getPadding().getLeft();
		RightCenter_Label.setPadding(new Insets(top + event.getDeltaY(), right, bottom, left));
	/*	
		int i=(int)Math.round(RightCenter_Label.getWidth()/20);
		StringBuilder aStringBuilder=new StringBuilder(RightCenter_Label.getText());
		
		String appendString=aStringBuilder.substring(0,i);
		aStringBuilder.append(appendString);
		aStringBuilder=new StringBuilder(aStringBuilder.substring(i+1));
		RightCenter_Label.setText(aStringBuilder.toString());
	*/
	}

	/**
	 * Button_Inquiry_Action
	 * @param event The event
	 */
	@FXML
	void Button_Inquiry_Action(ActionEvent event) {

	}

	/**
	 * Button_Return_Action
	 * @param event The event
	 * @throws ClassNotFoundException If fileName not found
	 * @throws IOException If IO connection failed
	 */
	@FXML
	void Button_Return_Action(ActionEvent event) throws ClassNotFoundException, IOException {
		String oldFileName = TextField_Information.getText() + ".csv";
		String newFileName = BIOLOGY_CSV;

		if (oldFileName != null && !oldFileName.equals("") && !oldFileName.equals(BIOLOGY_CSV))
			newFileName = oldFileName.substring(0, oldFileName.lastIndexOf(">")).replace(">", "_") + ".csv";

		UsefulToolkit aToolkit = new UsefulToolkit();

		aToolkit.autoFillInterface(Item1CenterFrame, newFileName, 0, TextField_Information, RightCenter_Title,
				RightCenter_ImageView, RightCenter_Label);
	}
	
	/**
	 * Button_Return2_Action
	 * 
	 * @param event
	 *            The event
	 * @throws ClassNotFoundException
	 *             If fileName not found
	 * @throws IOException
	 *             If IO connection failed
	 */
	@FXML
	void Button_Return2_Action(ActionEvent event) throws ClassNotFoundException, IOException {
		String oldFileName = TextField_Information2.getText() + ".csv";
		String newFileName = BIOLOGY_CSV;

		if (oldFileName != null && !oldFileName.equals("") && !oldFileName.equals(BIOLOGY_CSV))
			newFileName = oldFileName.substring(0, oldFileName.lastIndexOf(">")).replace(">", "_") + ".csv";

		UsefulToolkit aToolkit = new UsefulToolkit();

		aToolkit.autoFillInterface(Item3CenterFrame, newFileName, 0, TextField_Information2, RightCenter_Title,
				RightCenter_ImageView, RightCenter_Label);
	}

	/**
	 * Event that left-clicking on the ListView 鼠标左击列表的触发器
	 * 
	 * @param event
	 *            Mouse input events
	 * @throws ClassNotFoundException If fileName not found
	 * @throws IOException If IO connection failed
	 */
	@FXML
	void MainListView_MouseClicked(MouseEvent event) throws ClassNotFoundException, IOException {

		List<Object> StackPaneItems = new ArrayList<>(Arrays.asList(HBox_Left.getChildren().toArray()));
		List<String> ListViewItems = new ArrayList<>(
				Arrays.asList(ConfigHandle.getConfigStringData(MAIN_CFG, "ListViewItems")));
		List<Integer> ListViewEventSequence = new ArrayList<>();
		int[] aIntArray = ConfigHandle.getConfigIntData(MAIN_CFG, "ListViewEventSequence");
		for (int i : aIntArray) {
			ListViewEventSequence.add(i);
		}

		HashMap<Integer, String> hashMap = new UsefulToolkit().autoItemCountConformity(StackPaneItems, ListViewItems,
				ListViewEventSequence);
		Object item = MainListView.getSelectionModel().getSelectedItem();

		if (item != null) {
			for (int i = 0; i < hashMap.size(); i++) {
				// 过时方法!
				String urlString = ((ImageView) item).getImage().impl_getUrl();
				String imageName = urlString.substring(urlString.lastIndexOf("/") + 1, urlString.lastIndexOf("_"));

				if (imageName.equals(hashMap.get(i))) {
					for (int j = 0; j < hashMap.size(); j++) {
						BorderPane AllPane = (BorderPane) HBox_Left.lookup("#MainListViewItem" + (j + 1));
						AllPane.setVisible(false);
					}
					BorderPane ActivityPane = (BorderPane) HBox_Left.lookup("#MainListViewItem" + (i + 1));
					ActivityPane.setVisible(true);
					LogHandle.writeLog(LOGNAME_LOG,
							new Throwable().getStackTrace()[0].getMethodName() + ":" + ActivityPane.toString());
					
					switch (imageName) {
					case "随机页面":
						UsefulToolkit aToolkit = new UsefulToolkit();
						String fileName=FILENUMBER.get((int)Math.floor(Math.random()*FILENUMBER.size()));
						aToolkit.autoFillInterface(Item3CenterFrame, fileName, 0, TextField_Information2,
								RightCenter_Title, RightCenter_ImageView, RightCenter_Label);
						break;

					default:
						break;
					}
				}
			}
		}
	}
}
