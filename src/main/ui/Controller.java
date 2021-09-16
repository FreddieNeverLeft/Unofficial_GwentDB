package ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

// https://www.youtube.com/watch?v=8lR9scOLE7U for controller method inspiration and startup code
// https://github.com/k33ptoo/RestaurantMgtSampleUI associated git source.
// https://www.geeksforgeeks.org/javafx-togglebutton-class/ for toggle button litsener example.

public class Controller implements Initializable {
    private HashSet<Button> buttonHashSet = new HashSet<>();
    private HashSet<Button> leaderHashSet = new HashSet<>();
    private JsonRead jsonRead = new JsonRead();
    private DeckRender deckRender = new DeckRender();
    private Filter filter = new Filter();
    private String oldLeaderName = "null";
    private ColorAdjust blackout = new ColorAdjust();

    @FXML
    private ToggleGroup provisionSelect;
    @FXML
    private ToggleGroup factionSelect;
    @FXML
    private ToggleGroup colorSelect;
    @FXML
    private ToggleGroup typeSelect;
    @FXML
    private ToggleGroup raritySelect;
    @FXML
    private ToggleButton factionAll;
    @FXML
    private ToggleButton factionMO;
    @FXML
    private ToggleButton factionNR;
    @FXML
    private ToggleButton factionNG;
    @FXML
    private ToggleButton factionSY;
    @FXML
    private ToggleButton factionSK;
    @FXML
    private ToggleButton factionST;
    @FXML
    private ToggleButton factionNeutral;
    @FXML
    private ToggleButton provAll;
    @FXML
    private ToggleButton provFour;
    @FXML
    private ToggleButton provFive;
    @FXML
    private ToggleButton provSix;
    @FXML
    private ToggleButton provSeven;
    @FXML
    private ToggleButton provEight;
    @FXML
    private ToggleButton provNine;
    @FXML
    private ToggleButton provTen;
    @FXML
    private ToggleButton provEleven;
    @FXML
    private ToggleButton colorAll;
    @FXML
    private ToggleButton colorBronze;
    @FXML
    private ToggleButton colorGold;
    @FXML
    private ToggleButton typeAll;
    @FXML
    private ToggleButton typeArti;
    @FXML
    private ToggleButton typeSpec;
    @FXML
    private ToggleButton typeUnit;
    @FXML
    private ToggleButton rarityAll;
    @FXML
    private ToggleButton rarityRare;
    @FXML
    private ToggleButton rarityComm;
    @FXML
    private ToggleButton rarityEpic;
    @FXML
    private ToggleButton rarityLegn;
    @FXML
    private VBox finalDeck = null;
    @FXML
    private TilePane cardLibrary = null;
    @FXML
    private ScrollPane cardLibraryScroll = null;
    @FXML
    private Label leaderNameText;
    @FXML
    private Label minUnitCounter;
    @FXML
    private Label maxProvision;
    @FXML
    private Label maxProvisionCounter;
    @FXML
    private Label minCardCounter;
    @FXML
    private ImageView leaderView;
    @FXML
    private TextField nameSearch;
    @FXML
    private MenuItem loadMenu;
    @FXML
    private MenuItem saveMenu;
    @FXML
    private MenuItem newMenu;
    @FXML
    private TextField nameText;

    HashMap<String,Card> currentDeckLibrary;
    HashMap<String,Leader> currentLeaderLibrary;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leaderNameText.setStyle("-fx-font: normal bold 13 System; "
                + "-fx-alignment: center;");
        factionNeutral.setVisible(false);
        initializeCardandLeaders();
        setCurrentDeckLibrary(filter.getCurrentDeck());
        setCurrentLeaderLibrary(filter.getCurrentLeaders());
        cardLibraryScroll.setFitToWidth(false);
        initializeLeaderLibrary();
        initializeCardLibrary();
        sortAllLibraryButtons();
        initializeToggleGroup();
        provisionSelectListener();
        factionSelectListener();
        colorSelectListener();
        typeSelectListener();
        raritySelectListener();
        nameSearchlistener();
        nameTextListener();
        initializeMenu();
        initializeTextforFaction();
        initializeTextforRarity();
    }

    private void initializeTextforRarity() {
        Tooltip lg = new Tooltip("Legendary");
        lg.setStyle("-fx-font: normal bold 13 System;");
        rarityLegn.setTooltip(lg);
        Tooltip ep = new Tooltip("Epic");
        ep.setStyle("-fx-font: normal bold 13 System;");
        rarityEpic.setTooltip(ep);
        Tooltip rr = new Tooltip("Rare");
        rr.setStyle("-fx-font: normal bold 13 System;");
        rarityRare.setTooltip(rr);
        Tooltip cm = new Tooltip("Common");
        cm.setStyle("-fx-font: normal bold 13 System;");
        rarityComm.setTooltip(cm);
    }

    private void initializeTextforFaction() {
        Tooltip nr = new Tooltip("Northern Realms");
        nr.setStyle("-fx-font: normal bold 13 System;");
        factionNR.setTooltip(nr);
        Tooltip mo = new Tooltip("Monster");
        mo.setStyle("-fx-font: normal bold 13 System;");
        factionMO.setTooltip(mo);
        Tooltip nu = new Tooltip("Neutral");
        nu.setStyle("-fx-font: normal bold 13 System;");
        factionNeutral.setTooltip(nu);
        Tooltip ng = new Tooltip("Nilfgaard");
        ng.setStyle("-fx-font: normal bold 13 System;");
        factionNG.setTooltip(ng);
        Tooltip st = new Tooltip("Scoia'tael");
        st.setStyle("-fx-font: normal bold 13 System;");
        factionST.setTooltip(st);
        Tooltip sk = new Tooltip("Skellige");
        sk.setStyle("-fx-font: normal bold 13 System;");
        factionSK.setTooltip(sk);
        Tooltip sy = new Tooltip("Syndicate");
        sy.setStyle("-fx-font: normal bold 13 System;");
        factionSY.setTooltip(sy);
    }


    private void sortAllLibraryButtons() {
        HashSet<Button> sortedCardSet = new LinkedHashSet<>();
        HashSet<Button> sortedLeaderSet = new LinkedHashSet<>();
        for (Map.Entry<String,Leader> entry: getCurrentLeaderLibrary().entrySet()) {
            for (Button b: leaderHashSet) {
                if(b.getId().equals(entry.getKey())) {
                    sortedLeaderSet.add(b);
                    cardLibrary.getChildren().add(b);
                }
            }
        }
        for (Map.Entry<String, Card> entry: getCurrentDeckLibrary().entrySet()) {
            for (Button b: buttonHashSet) {
                if(b.getId().equals(entry.getKey())) {
                    sortedCardSet.add(b);
                    cardLibrary.getChildren().add(b);
                }
            }
        }
        buttonHashSet = sortedCardSet;
        leaderHashSet = sortedLeaderSet;
    }

    private void initializeMenu() {
        loadMenuAction();
        saveMenuListener();
        newMenuListener();
    }

    private void saveMenuListener() {
        saveMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter
                        = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                    try {
                        deckRender.setOutputPath(selectedFile.getCanonicalPath());
                        deckRender.save();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void newMenuListener() {
        newMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                nameSearch.setText("");
                deckRender = new DeckRender();
                deckRender.setCardLibrary(jsonRead.getCardMap());
                deckRender.setLeaderLibrary(jsonRead.getLeaderMap());
                leaderView.setImage(null);
                leaderNameText.setText("Please select a leader.");
                nameText.setText(deckRender.getDeckName());
                updateNumbersWithColour();
                filter.setLeaderisChosen(false);
                resetToggle();
                finalDeck.getChildren().clear();
                resetAllButtons();
            }
        });
    }

    private void resetToggle() {
        factionNeutral.setSelected(true);
        factionNeutral.setVisible(false);
        factionAll.setSelected(true);
        colorAll.setSelected(true);
        provAll.setSelected(true);
        rarityAll.setSelected(true);
        typeAll.setSelected(true);
        factionMO.setDisable(false);
        factionSY.setDisable(false);
        factionST.setDisable(false);
        factionNR.setDisable(false);
        factionNG.setDisable(false);
        factionSK.setDisable(false);
    }

    private void resetAllButtons() {
        for (Button b: leaderHashSet) {
            setGraphicForButton(b);
        }
        for (Button b: buttonHashSet) {
            setGraphicForButton(b);
        }
    }

    private void loadMenuAction() {
        loadMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter
                        = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                    try {
                        initiatenewMenu(selectedFile);
                        initiateLoadHelper();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initiatenewMenu(File selectedFile) throws IOException {
        newMenu.fire();
        deckRender.setInputPath(selectedFile.getCanonicalPath());
    }

    private void initiateLoadHelper() throws IOException {
        if (deckRender.checkfileValid()) {
            deckRender.load();
            nameText.setText(deckRender.getDeckName());
            clickLeader();
            clickCards();
        }
    }

    private void clickCards() {
        List<Card> renderDeck = deckRender.getCurrentDeck();
        List<Card> temp = new LinkedList<>();
        temp.addAll(renderDeck);
        for (Card c : temp) {
            for (Button b : buttonHashSet) {
                if (b.getId().equals(c.getName())) {
                    b.fire();
                }
            }
        }
    }

    private void clickLeader() {
        for (Button b: leaderHashSet) {
            if (b.getId().equals(deckRender.getLeaderName())) {
                b.fire();
            }
        }
    }

    private void initializeCardandLeaders() {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("cards.json"));
            jsonRead.loadJsonCards(jsonObject);
            filter.setAllDeck(jsonRead.getCardMap());
            filter.setAllLeaders(jsonRead.getLeaderMap());
            renderCurrentDeck();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initializeLeaderLibrary() {
        getCurrentLeaderLibrary().entrySet().parallelStream()
                .forEach(entry -> {
                    InputStream input = null;
                    try {
                        input = new URL(entry.getValue().getUrl()).openStream();

                    }  catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Image i = new Image(input);
                    ImageView iw = new ImageView(i);
                    iw.setFitHeight(219.3);
                    iw.setFitWidth(163.95);
                    generateLeaderButton(entry, i, iw);
                });
    }

    private void generateLeaderButton(Map.Entry<String, Leader> entry, Image i, ImageView iw) {
        Button b = new Button("", iw);
        Tooltip tt = new Tooltip(entry.getKey());
        tt.setStyle("-fx-font: normal bold 13 System;");
        b.setTooltip(tt);
        ColorAdjust blackout = new ColorAdjust();
        blackout.setBrightness(-0.5);
        b.setId(entry.getKey());
        leaderHashSet.add(b);
        b.setOnAction(event -> {
            leaderButtonwhenClicked(entry, i, iw, b, blackout);
        });
    }

    private void leaderButtonwhenClicked(Map.Entry<String, Leader> entry,
                                         Image i, ImageView iw, Button b, ColorAdjust blackout) {
        filter.setLeaderFaction(entry.getValue().getFaction());
        hideAndShowFactionSelect(entry.getValue().getFaction());
        leaderView.setImage(i);
        iw.setEffect(blackout);
        b.setGraphic(iw);
        b.setStyle("-fx-background-color : #cccccc");
        leaderNameText.setText(entry.getKey());
        deckRender.setLeader(entry.getValue());
        updateNumbersWithColour();
        resetOldLeader(oldLeaderName);
        oldLeaderName = deckRender.getLeaderName();
    }

    private void resetOldLeader(String name) {
        if (!oldLeaderName.equals(deckRender.getLeaderName()) && !oldLeaderName.equals("null")) {
            for (javafx.scene.control.Button b: leaderHashSet) {
                if (name.equals(b.getId())) {
                    setGraphicForButton(b);
                }
            }
        }
    }

    private void initializeToggleGroup() {
        JavafxUtil.get().addAlwaysOneSelectedSupport(provisionSelect);
        JavafxUtil.get().addAlwaysOneSelectedSupport(factionSelect);
        JavafxUtil.get().addAlwaysOneSelectedSupport(colorSelect);
        JavafxUtil.get().addAlwaysOneSelectedSupport(typeSelect);
        JavafxUtil.get().addAlwaysOneSelectedSupport(raritySelect);
    }

    private void raritySelectListener() {
        raritySelect.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {
                    public void changed(ObservableValue<? extends Toggle> ov,
                                        final Toggle toggle, final Toggle newToggle) {
                        raritySelectHelper(newToggle);
                        refreshCardLibrary();
                    }
                });
    }

    private void raritySelectHelper(Toggle newToggle) {
        if (newToggle.equals(rarityAll)) {
            filter.setRaritySelect(Filter.RaritySelect.RarityAll);
        }
        if (newToggle.equals(rarityRare)) {
            filter.setRaritySelect(Filter.RaritySelect.RarityRare);
        }
        if (newToggle.equals(rarityComm)) {
            filter.setRaritySelect(Filter.RaritySelect.RarityComm);
        }
        if (newToggle.equals(rarityEpic)) {
            filter.setRaritySelect(Filter.RaritySelect.RarityEpic);
        }
        if (newToggle.equals(rarityLegn)) {
            filter.setRaritySelect(Filter.RaritySelect.RarityLegn);
        }
    }

    private void refreshCardLibrary() {
        if (filter.shouldDisplayLeaders()) {
            filter.filterLeaderFaction();
            setCurrentLeaderLibrary(filter.getCurrentLeaders());
            filter.filterLibrary();
            setCurrentDeckLibrary(filter.getCurrentDeck());
            hideAndShowLeadersandCards();
        } else {
            filter.filterLibrary();
            setCurrentDeckLibrary(filter.getCurrentDeck());
            hideAndShowCards();
        }
    }


    private void typeSelectListener() {
        typeSelect.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {
                    public void changed(ObservableValue<? extends Toggle> ov,
                                        final Toggle toggle, final Toggle newToggle) {
                        typeSelectHelper(newToggle);
                        refreshCardLibrary();
                    }
                });
    }

    private void typeSelectHelper(Toggle newToggle) {
        if (newToggle.equals(typeAll)) {
            filter.setTypeSelect(Filter.TypeSelect.TypeAll);
        }
        if (newToggle.equals(typeArti)) {
            filter.setTypeSelect(Filter.TypeSelect.TypeArti);
        }
        if (newToggle.equals(typeSpec)) {
            filter.setTypeSelect(Filter.TypeSelect.TypeSpec);
        }
        if (newToggle.equals(typeUnit)) {
            filter.setTypeSelect(Filter.TypeSelect.TypeUnit);
        }
    }

    private void colorSelectListener() {
        colorSelect.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {
                    public void changed(ObservableValue<? extends Toggle> ov,
                                        final Toggle toggle, final Toggle newToggle) {
                        if (newToggle.equals(colorAll)) {
                            filter.setColorSelect(Filter.ColorSelect.ColorALL);
                        }
                        if (newToggle.equals(colorGold)) {
                            filter.setColorSelect(Filter.ColorSelect.ColorGold);
                        }
                        if (newToggle.equals(colorBronze)) {
                            filter.setColorSelect(Filter.ColorSelect.ColorBronze);
                        }
                        refreshCardLibrary();
                    }
                });
    }

    private void factionSelectListener() {
        factionSelect.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {
                    public void changed(ObservableValue<? extends Toggle> ov,
                                        final Toggle toggle, final Toggle newToggle) {
                        factionSelectHelper(newToggle);
                        refreshCardLibrary();
                    }
                });
    }

    private void factionSelectHelper(Toggle newToggle) {
        firstHalfofFsh(newToggle);
        if (newToggle.equals(factionSY)) {
            filter.setFactionSelect(Filter.FactionSelect.FactionSY);
        }
        if (newToggle.equals(factionST)) {
            filter.setFactionSelect(Filter.FactionSelect.FactionST);
        }
        if (newToggle.equals(factionSK)) {
            filter.setFactionSelect(Filter.FactionSelect.FactionSK);
        }
        if (newToggle.equals(factionNeutral)) {
            filter.setFactionSelect(Filter.FactionSelect.FactionNeu);
        }
    }

    private void firstHalfofFsh(Toggle newToggle) {
        if (newToggle.equals(factionAll)) {
            filter.setFactionSelect(Filter.FactionSelect.FactionAll);
        }
        if (newToggle.equals(factionNG)) {
            filter.setFactionSelect(Filter.FactionSelect.FactionNG);
        }
        if (newToggle.equals(factionNR)) {
            filter.setFactionSelect(Filter.FactionSelect.FactionNR);
        }
        if (newToggle.equals(factionMO)) {
            filter.setFactionSelect(Filter.FactionSelect.FactionMO);
        }
    }

    private void provisionSelectListener() {
        provisionSelect.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {
                    public void changed(ObservableValue<? extends Toggle> ov,
                                        final Toggle toggle, final Toggle newToggle) {
                        if (newToggle.equals(provAll)) {
                            filter.setProvisionSelect(Filter.ProvisionSelect.ProvAll);
                        }
                        if (newToggle.equals(provFour)) {
                            filter.setProvisionSelect(Filter.ProvisionSelect.ProvFour);
                        }
                        if (newToggle.equals(provFive)) {
                            filter.setProvisionSelect(Filter.ProvisionSelect.ProvFive);
                        }
                        provisionSelectHelper(newToggle);
                        refreshCardLibrary();
                    }
                });
    }

    private void provisionSelectHelper(Toggle newToggle) {
        if (newToggle.equals(provSix)) {
            filter.setProvisionSelect(Filter.ProvisionSelect.ProvSix);
        }
        if (newToggle.equals(provSeven)) {
            filter.setProvisionSelect(Filter.ProvisionSelect.ProvSeven);
        }
        if (newToggle.equals(provEight)) {
            filter.setProvisionSelect(Filter.ProvisionSelect.ProvEight);
        }
        if (newToggle.equals(provNine)) {
            filter.setProvisionSelect(Filter.ProvisionSelect.ProvNine);
        }
        if (newToggle.equals(provTen)) {
            filter.setProvisionSelect(Filter.ProvisionSelect.ProvTen);
        }
        if (newToggle.equals(provEleven)) {
            filter.setProvisionSelect(Filter.ProvisionSelect.ProvEleven);
        }
    }

    private void initializeCardLibrary() {
        getCurrentDeckLibrary().entrySet().parallelStream()
                .forEach(entry -> {
                    InputStream input = null;
                    try {
                        input = new URL(entry.getValue().getUrl()).openStream();

                    }  catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    renderNewButtonImage(entry, input);
                });
    }

    private void renderNewButtonImage(Map.Entry<String, Card> entry, InputStream input) {
        Image i = new Image(input);
        ImageView iw = new ImageView(i);
        iw.setFitHeight(219.3);
        iw.setFitWidth(163.95);
        renderNewButton(entry, iw);
    }

    private void renderNewButton(Map.Entry<String, Card> entry, ImageView iw) {
        javafx.scene.control.Button b = new javafx.scene.control.Button("", iw);
//        if(entry.getValue().getRarity().equals(GwentCard.Rarity.Common)
//                ||entry.getValue().getRarity().equals(GwentCard.Rarity.Rare)){
//            b.setText("");
//        }
        b.setId(entry.getKey());
        cardToolTip(entry, b);
        buttonHashSet.add(b);
        blackout.setBrightness(-0.5);
        b.setOnAction(event -> {
            if (deckRender.getLeader() != null) {
                System.out.println(b.getText());
                deckRender.addCard(entry.getValue());
                iw.setEffect(blackout);
                b.setGraphic(iw);
                b.setStyle("-fx-background-color : #cccccc");
                finalDeck.getChildren().clear();
                updateNumbersWithColour();
                renderCurrentDeck();
            }
        });
    }

    private void cardToolTip(Map.Entry<String, Card> entry, Button b) {
        Tooltip tt = new Tooltip(entry.getKey());
        tt.setStyle("-fx-font: normal bold 13 System;");
        b.setTooltip(tt);
    }

    private void updateNumbersWithColour() {
        int maxProvision;
        if (deckRender.getLeader() != null) {
            maxProvision = deckRender.getLeader().getProvision();
        } else {
            maxProvision = 150;
        }
        int maxProvisionCounter = deckRender.getProvisionCounter();
        int unitCount = deckRender.getUnitCount();
        int cardCount = deckRender.getCardCount();
        colourUpdating(maxProvision, maxProvisionCounter, unitCount, cardCount);
        updateNumbers(maxProvision, maxProvisionCounter, unitCount, cardCount);
    }

    private void colourUpdating(int maxProvision, int maxProvisionCounter, int unitCount, int cardCount) {
        if (maxProvisionCounter > maxProvision) {
            this.maxProvisionCounter.setTextFill(Color.web("#FF0000"));
        }
        if (maxProvisionCounter <= maxProvision) {
            this.maxProvisionCounter.setTextFill(Color.web("#000000"));
        }
        if (unitCount < 13) {
            minUnitCounter.setTextFill(Color.web("#FF0000"));
        }
        if (unitCount >= 13) {
            minUnitCounter.setTextFill(Color.web("#000000"));
        }
        if (cardCount < 25) {
            minCardCounter.setTextFill(Color.web("#FF0000"));
        }
        if (cardCount >= 25) {
            minCardCounter.setTextFill(Color.web("#000000"));
        }
    }

    private void updateNumbers(int maxProvision, int maxProvisionCounter, int unitCount, int cardCount) {
        this.maxProvision.setText(Integer.toString(maxProvision));
        this.maxProvisionCounter.setText(Integer.toString(maxProvisionCounter));
        minCardCounter.setText(Integer.toString(cardCount));
        minUnitCounter.setText(Integer.toString(unitCount));
    }

    private void renderCurrentDeck() {
        List<Card> sortedDeck = deckRender.getCurrentDeck();
        for (Card c: sortedDeck) {
            Button d = new Button(c.getProvision() + "      " + c.getName());
            d.setStyle("-fx-font: normal bold 13 System;"
                    + "-fx-alignment: center-left;");
            d.setMinSize(280,35);
            d.setMaxSize(280, 35);
            if (c instanceof UnitCard) {
                d.setText(c.getProvision() + "      " + c.getName() + "    " + ((UnitCard) c).getValue());
            }
            finalDeck.getChildren().add(d);
            d.setOnMouseClicked(event -> {
                resetButton(c.getName());
                deckRender.removeCard(c);
                updateNumbersWithColour();
                finalDeck.getChildren().remove(d);

            });
        }
    }

    private void resetButton(String name) {
        for (javafx.scene.control.Button b: buttonHashSet) {
            if (name.equals(b.getId())) {
                setGraphicForButton(b);
            }
        }
    }

    private void hideAndShowCards() {
        cardLibrary.getChildren().clear();
        for (Button b: buttonHashSet) {
            if (currentDeckLibrary.containsKey(b.getId())) {
                cardLibrary.getChildren().add(b);
            }
        }
    }

    private void hideAndShowLeadersandCards() {
        cardLibrary.getChildren().clear();
        for (Button b: leaderHashSet) {
            if (currentLeaderLibrary.containsKey(b.getId())) {
                cardLibrary.getChildren().add(b);
            }
        }
        for (javafx.scene.control.Button b: buttonHashSet) {
            if (currentDeckLibrary.containsKey(b.getId())) {
                cardLibrary.getChildren().add(b);
            }
        }
    }

    private void hideAndShowFactionSelect(GwentCard.Faction faction) {
        if (faction.equals(GwentCard.Faction.Skellige)) {
            skelligeButtonDisabler();
        }
        if (faction.equals(GwentCard.Faction.Syndicate)) {
            syndicateButtonDisabler();
        }
        if (faction.equals(GwentCard.Faction.Scoiatael)) {
            scoiataelButtonDisabler();
        }
        if (faction.equals(GwentCard.Faction.Monster)) {
            monsterButtonDisabler();
        }
        if (faction.equals(GwentCard.Faction.Nilfgaard)) {
            nilfgaardButtonDisabler();

        }
        if (faction.equals(GwentCard.Faction.Northern_Realms)) {
            nrButtonDisabler();
        }
    }

    private void nrButtonDisabler() {
        factionNeutral.setVisible(true);
        factionNR.setSelected(true);
        factionMO.setDisable(true);
        factionSY.setDisable(true);
        factionST.setDisable(true);
        factionNG.setDisable(true);
        factionSK.setDisable(true);
    }

    private void nilfgaardButtonDisabler() {
        factionNeutral.setVisible(true);
        factionNG.setSelected(true);
        factionMO.setDisable(true);
        factionSY.setDisable(true);
        factionST.setDisable(true);
        factionNR.setDisable(true);
        factionSK.setDisable(true);
    }

    private void monsterButtonDisabler() {
        factionNeutral.setVisible(true);
        factionMO.setSelected(true);
        factionSK.setDisable(true);
        factionSY.setDisable(true);
        factionST.setDisable(true);
        factionNR.setDisable(true);
        factionNG.setDisable(true);
    }

    private void scoiataelButtonDisabler() {
        factionNeutral.setVisible(true);
        factionST.setSelected(true);
        factionMO.setDisable(true);
        factionSY.setDisable(true);
        factionSK.setDisable(true);
        factionNR.setDisable(true);
        factionNG.setDisable(true);
    }

    private void syndicateButtonDisabler() {
        factionNeutral.setVisible(true);
        factionSY.setSelected(true);
        factionMO.setDisable(true);
        factionSK.setDisable(true);
        factionST.setDisable(true);
        factionNR.setDisable(true);
        factionNG.setDisable(true);
    }

    private void skelligeButtonDisabler() {
        factionNeutral.setVisible(true);
        factionSK.setSelected(true);
        factionMO.setDisable(true);
        factionSY.setDisable(true);
        factionST.setDisable(true);
        factionNR.setDisable(true);
        factionNG.setDisable(true);
    }

    private void setCurrentDeckLibrary(HashMap<String, Card> deckList) {
        currentDeckLibrary = deckList;
    }

    private HashMap<String, Card> getCurrentDeckLibrary() {
        return currentDeckLibrary;
    }

    private void setCurrentLeaderLibrary(HashMap<String, Leader> currentLeaderLibrary) {
        this.currentLeaderLibrary = currentLeaderLibrary;
    }

    private HashMap<String, Leader> getCurrentLeaderLibrary() {
        return currentLeaderLibrary;
    }

    private void setGraphicForButton(Button button) {
        ImageView iv = (ImageView) button.getGraphic();
        ColorAdjust normal = new ColorAdjust();
        normal.setBrightness(0);
        iv.setEffect(normal);
        button.setGraphic(iv);
        button.setStyle(null);
    }

    private void nameSearchlistener() {
        nameSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                filter.setNameSearch(newValue);
                refreshCardLibrary();
            }
        });
    }

    private void nameTextListener() {
        nameText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                deckRender.setDeckName(newValue);
            }
        });
    }
}