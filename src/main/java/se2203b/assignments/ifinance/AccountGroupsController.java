package se2203b.assignments.ifinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountGroupsController implements Initializable {
    // will be complete in assignment 4
    @FXML
    private ContextMenu context;
    @FXML
    private TreeView tree;

    @FXML
    private TitledPane ac;

    @FXML
    private Button save;

    @FXML
    private Button exit;

    @FXML
    private TextField name;

    @FXML
    private AnchorPane anchor;
    ArrayList<String> gList = new ArrayList<>();
    ArrayList<String> cList = new ArrayList<>();
    private AccountCategoryAdapter accountCategoryAdapter;
    private AccountGroupAdapter accountGroupAdapter;
    IFinanceController iFinanceController;

    public void setAdapter(AccountGroupAdapter group, AccountCategoryAdapter category) throws SQLException {
        accountCategoryAdapter=category;
        accountGroupAdapter=group;
        gList=accountGroupAdapter.getGroups();
        cList=accountCategoryAdapter.getCategories();
        buildData();
    }
    public void setIFinanceController(IFinanceController controller) {
        iFinanceController = controller;
    }
    @FXML
    public void saveBtn(){

    }

    @FXML
    public void exitBtn(){
        // Get current stage reference
        Stage stage = (Stage) exit.getScene().getWindow();
        // Close stage
        stage.close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

    }
    public void buildData() throws SQLException {
        TreeItem<String> root = new TreeItem<>(null);
        System.out.println(gList);

        for (String item : cList) {
            root.getChildren().add(new TreeItem<>(item));
        }
        tree.setShowRoot(false);
        tree.setRoot(root);


        for (int i = 0; i < gList.size(); i++) {
            //Check if the parent# is equal to zero, if it is you know that its parent is one of the big four, if not, its parent is something else that has the id of parent#
            String currentName = gList.get(i);

            if (accountGroupAdapter.gettingParent(gList.get(i)) == 0) {
                //find the name of the parent by check which name in the datebase is equal to the current nodes element
                //String ele = accountGroupAdapter.gettingElement(currentName);

                for (TreeItem<String> currentNode : root.getChildren()) {
                    String ele = accountGroupAdapter.gettingElement(currentName);
                    String no=currentNode.getValue();
                    //System.out.println(currentNode.getValue());
                    System.out.println(ele);
                    if (currentNode.getValue().equals(accountGroupAdapter.gettingElement(gList.get(i)))) {
                        currentNode.getChildren().add(new TreeItem<>(gList.get(i)));
                    }
                }
            }
            //if parent# is not equal to zero
            else {
                int n = accountGroupAdapter.gettingParent(currentName);
                //first get the parent# of the current node using its name
                String parent = accountGroupAdapter.gettingName(n);
                //Second using that parent# get the name of the parent where the parent# equals the ID\
                child(root,parent,gList.get(i));
                //Third is to put the current node under the item that has the name from the second
            }

        }
    }
    public void child(TreeItem<String> root, String parent, String name){
        for (TreeItem<String> node: root.getChildren()){
            if (node.getChildren().isEmpty()) {
                if(node.getValue().equals(parent)){
                    node.getChildren().add(new TreeItem<>(name));
                }
            }else{
                child(node, parent,name);
            }
        }
    }



















//        for (int i =0; i<gList.size(); i++){
//            if (accountGroupAdapter.gettingParent(gList.get(i))!=0){
//                String p = accountGroupAdapter.gettingName(accountGroupAdapter.gettingParent(gList.get(i)));
//                childCheck(root, gList.get(i),p);
//            }else{
//                for(TreeItem<String> c: root.getChildren()){
//                    if (c.getValue().equals(accountGroupAdapter.gettingElement(gList.get(i)))){
//                        c.getChildren().add(new TreeItem<>(gList.get(i)));
//                    }
//                }
//            }
//        }

    //    public void childCheck(TreeItem<String> root,String nm,  String in ){
//        for (TreeItem<String> c: root.getChildren()){
//            if (c.getChildren().isEmpty()){
//                if (c.getValue().equals(nm)){
//                    c.getChildren().add(new TreeItem<>(in));
//                }
//            }else {
//                if (c.getValue().equals(nm)){
//                    c.getChildren().add(new TreeItem<>(in));
//                }else {
//                    childCheck(c,nm,in);
//                }
//            }
//        }
//
//    }
    private void displayAlert(String msg) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert-view.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/main/resources/se2203b/assignments/ifinance/WesternLogo.png"));
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }
    //    private TreeItem<Group> findNode(TreeItem<Group> rootNode, Group group) {
//        // Recursive function to find the parent node
//        if (rootNode.getValue() == group) {
//            return rootNode;
//        } else {
//            for (TreeItem<Group> childNode : rootNode.getChildren()) {
//                TreeItem<Group> foundNode = findNode(childNode, group);
//                if (foundNode != null) {
//                    return foundNode;
//                }
//            }
//            return null;
//        }
//    }
    @FXML
    private void handleTreeViewClick() {
        TreeItem<String> selectedItem = (TreeItem<String>) tree.getSelectionModel().getSelectedItem();
        MenuItem addGroup = new MenuItem("Add New Group");
        MenuItem changeName = new MenuItem("Change Group Name");
        MenuItem deleteGroup = new MenuItem("Delete Group");
        context=new ContextMenu(addGroup, changeName, deleteGroup);
        if (selectedItem != null) {
            if (selectedItem.getValue().equals("Assets") ||
                    selectedItem.getValue().equals("Liabilities") ||
                    selectedItem.getValue().equals("Income") ||
                    selectedItem.getValue().equals("Expenses")) {
                changeName.setDisable(true);
                deleteGroup.setDisable(true);
            }
            else {
                changeName.setDisable(false);
                deleteGroup.setDisable(false);
            }
        }
        tree.setContextMenu(context);
    }}