package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.requests.ExtraProductionRequest;
import it.polimi.ingsw.network.requests.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LeaderProductionController implements Initializable {

    @FXML
    private ImageView imgCard1, imgCard2;
    @FXML
    private ImageView r_stone, r_servant, r_shield, r_coin;
    @FXML
    private ImageView depot1_1, depot2_1, depot2_2, depot3_1, depot3_2, depot3_3;
    @FXML
    private Label d_stone, d_servant, d_shield, d_coin;
    @FXML
    private Pane resourcePanel, depot, imgPane1, imgPane2;
    @FXML
    private Spinner<Integer> stone, servant, shield, coin;
    @FXML
    private Button confirmButton;
    @FXML
    Label labelLeaderCards;
    @FXML
    private RadioButton rdOUT_1, rdOUT_2, rdOUT_3, rdOUT_4;
    @FXML
    private ToggleGroup toggleGroupOUT;

    private AbilityChoice choice;
    private ResourceType res;

    /**
     * The ImageView that contains the select image card
     */
    private ImageView selectedCard;

    private ImageView source;

    /**
     * The list of player's leader cards
     */
    private final List<ImageView> imgsLeaderCards = new ArrayList<>();
    /**
     * The list of pane to disable leader cards
     */
    private final List<Pane> panesLeaderCards = new ArrayList<>();

    /**
     * The corresponding MainController
     */
    private MainController mainController;

    /**
     * Sets the MainController
     * @param mainController  the MainController to associate with this controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Initializes @FXML fields, organizing the corresponding images of leader cards in one list<br>
     * <p>Called automatically when an entity is injected from FXML</p>
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.imgsLeaderCards.add(imgCard1);
        this.imgsLeaderCards.add(imgCard2);
        this.panesLeaderCards.add(imgPane1);
        this.panesLeaderCards.add(imgPane2);
        this.resourcePanel.setVisible(false);
        this.stone.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.servant.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.shield.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.coin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
    }

    /**
     * Checks if the player has at least one leader card with a production power (based on card id)
     * @return true if the player has leaders with production power
     */
    public boolean hasProductionCard(){
        List<LeaderCard> playerCards = this.mainController.getClientModel().getPersonalBoardModel().getLeaderMap().get(this.mainController.getNickname());
        List<Integer> productionID = new ArrayList<>();
        productionID.add(13);
        productionID.add(14);
        productionID.add(15);
        productionID.add(16);

        for ( Integer id: productionID ){
            for (LeaderCard playerCard : playerCards) {
                if (id == playerCard.getId()) return true;
            }
        }
        return false;
    }

    private boolean isProductionCard(LeaderCard card){
        List<Integer> productionID = new ArrayList<>();
        productionID.add(13);
        productionID.add(14);
        productionID.add(15);
        productionID.add(16);

        for ( Integer id: productionID ){
                if (id == card.getId()) return true;
        }
        return false;
    }

    /**
     * Reset the Buy Panel to the default
     */
    public void closeResourcePanel(){
        this.r_stone.setImage(null);
        this.r_servant.setImage(null);
        this.r_shield.setImage(null);
        this.r_coin.setImage(null);
        this.d_stone.setText("x0");
        this.d_servant.setText("x0");
        this.d_shield.setText("x0");
        this.d_coin.setText("x0");
        this.resourcePanel.setVisible(false);
        this.depot.setVisible(true);
        if(selectedCard != null) this.selectedCard.getStyleClass().remove("selected-card");
    }

    public void updateLeaderCards() {
        List<LeaderCard> playerLeaderCards = this.mainController.getClientModel().getPersonalBoardModel().getLeaderMap().get(this.mainController.getNickname());

        for(int i=0; i<imgsLeaderCards.size(); i++){
            if( i < playerLeaderCards.size() && playerLeaderCards.get(i)!=null) {
                imgsLeaderCards.get(i).setImage(Util.getLeaderImg(playerLeaderCards.get(i).getImg()));
                panesLeaderCards.get(i).setVisible(!isProductionCard(playerLeaderCards.get(i)));

            } else {
                imgsLeaderCards.get(i).setImage(Util.getGenericImg("emptyCard"));
                panesLeaderCards.get(i).setVisible(true);
            }

        }
    }

    public void loadStorages() {
        List<Image> imgs = this.mainController.getPersonalBoardController().getDepotImgs();
        depot1_1.setImage(imgs.get(0));
        depot2_1.setImage(imgs.get(1));
        depot2_2.setImage(imgs.get(2));
        depot3_1.setImage(imgs.get(3));
        depot3_2.setImage(imgs.get(4));
        depot3_3.setImage(imgs.get(5));
        //Reset default value to 0
        this.stone.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.servant.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.shield.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.coin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        //Set max value to corresponding amount in strongbox
        Resource strongbox = this.mainController.getClientModel().getStoragesModel().getStrongboxMap().get(this.mainController.getNickname());
        try {
            ((SpinnerValueFactory.IntegerSpinnerValueFactory)this.stone.getValueFactory()).setMax(strongbox.getValue(ResourceType.STONE));
            ((SpinnerValueFactory.IntegerSpinnerValueFactory)this.servant.getValueFactory()).setMax(strongbox.getValue(ResourceType.SERVANT));
            ((SpinnerValueFactory.IntegerSpinnerValueFactory)this.shield.getValueFactory()).setMax(strongbox.getValue(ResourceType.SHIELD));
            ((SpinnerValueFactory.IntegerSpinnerValueFactory)this.coin.getValueFactory()).setMax(strongbox.getValue(ResourceType.COIN));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void useCard1(MouseEvent mouseEvent) {
            if (this.selectedCard != null) this.selectedCard.getStyleClass().remove("selected-card");
            this.selectedCard = ((ImageView) mouseEvent.getSource());
            selectedCard.getStyleClass().add("selected-card");
            this.resourcePanel.setVisible(true);
            this.depot.setVisible(true);
        this.choice = AbilityChoice.FIRST;
    }

    public void useCard2(MouseEvent mouseEvent) {
        if (this.selectedCard != null) this.selectedCard.getStyleClass().remove("selected-card");
        this.selectedCard = ((ImageView) mouseEvent.getSource());
        selectedCard.getStyleClass().add("selected-card");
        this.resourcePanel.setVisible(true);
        this.depot.setVisible(true);
        this.choice = AbilityChoice.SECOND;
    }

    public void dragDetection(MouseEvent mouseEvent) {
        this.source = (ImageView) mouseEvent.getSource();
        Dragboard db = this.source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putImage(this.source.getImage());
        db.setContent(cb);
        mouseEvent.consume();
    }

    public void dragOver(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasImage()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void dragDrop(DragEvent dragEvent) {
        String sourceId = this.source.getId();
        switch (this.mainController.getPersonalBoardController().getGenericSlot(sourceId)){
            case STONE:
                addRes(r_stone, ResourceType.STONE, d_stone);
                break;
            case SERVANT:
                addRes(r_servant, ResourceType.SERVANT, d_servant);
                break;
            case SHIELD:
                addRes(r_shield, ResourceType.SHIELD, d_shield);
                break;
            case COIN:
                addRes(r_coin, ResourceType.COIN, d_coin);
                break;
            default:
                break;
        }
        this.source.setImage(null);
    }

    private void addRes(ImageView id, ResourceType res, Label d){
        if(id.getImage()==null) id.setImage(Util.resToImage(res));
        int newValue = Integer.parseInt(d.getText().substring(1)) + 1;
        d.setText("x" + newValue);
    }

    public void onClickStone3(ActionEvent actionEvent) { this.res = ResourceType.STONE; }
    public void onClickCoin3(ActionEvent actionEvent) { this.res = ResourceType.COIN; }
    public void onClickShield3(ActionEvent actionEvent) { this.res = ResourceType.SHIELD; }
    public void onClickServant3(ActionEvent actionEvent) { this.res = ResourceType.SERVANT; }

    public void buildRequest(ActionEvent actionEvent){
            Resource depot = new Resource(0, 0, 0, 0);
            Resource strongbox = new Resource(0, 0, 0, 0);
            try {
                depot.modifyValue(ResourceType.STONE, Integer.parseInt(d_stone.getText().substring(1)));
                depot.modifyValue(ResourceType.SERVANT, Integer.parseInt(d_servant.getText().substring(1)));
                depot.modifyValue(ResourceType.SHIELD, Integer.parseInt(d_shield.getText().substring(1)));
                depot.modifyValue(ResourceType.COIN, Integer.parseInt(d_coin.getText().substring(1)));
                strongbox.modifyValue(ResourceType.STONE, stone.getValue());
                strongbox.modifyValue(ResourceType.SERVANT, servant.getValue());
                strongbox.modifyValue(ResourceType.SHIELD, shield.getValue());
                strongbox.modifyValue(ResourceType.COIN, coin.getValue());
            } catch (NegativeResAmountException e) {
                e.printStackTrace();
            }
            Request request = new ExtraProductionRequest(this.choice, depot, strongbox, this.res);
            this.mainController.sendMessage(request);
            this.mainController.closeLeader();
    }
}
