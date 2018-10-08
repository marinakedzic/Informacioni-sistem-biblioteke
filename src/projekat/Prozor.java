
package projekat;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Prozor {
    // staticka metoda koju pozivamo kada se odredjena manipulacija odradi uspesno i ona otvara novi prozor sa obavestenjem koje dodajemo sami prilikom poziva
   public static void prikazUspesno(String action,String upit){
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Projekat.class.getResourceAsStream("good.png")));
        stage.setTitle("Uspesno");
        stage.setHeight(200);
        stage.setWidth(500);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        HBox layout = new HBox(30);
        layout.setStyle("-fx-background-color:#F2C663;");
        Label l1 = new Label();
        l1.setText("Uspesno ste " + action + " "+ upit + ".");
        l1.setStyle("-fx-color: black;");
        Button b2 = new Button();
        b2.setText("OK");
        layout.getChildren().addAll(l1,b2);
        layout.setAlignment(Pos.CENTER);
        Scene s = new Scene(layout);
        b2.setOnAction(evt ->{
            stage.close();
        });
        stage.setScene(s);
        stage.showAndWait();
       }
   // staticka metoda koju pozivamo kada se odredjena manipulacija odradi neuspesno i ona otvara novi prozor sa obavestenjem koje dodajemo sami prilikom poziva
      public static void prikazNeuspesno(String action,String upit){
        Stage stage1 = new Stage();
        stage1.getIcons().add(new Image(Projekat.class.getResourceAsStream("bad.png")));
        stage1.setTitle("Neuspesno");
        stage1.setHeight(200);
        stage1.setWidth(500);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.initStyle(StageStyle.DECORATED);
        HBox layout1 = new HBox(30);
        layout1.setStyle("-fx-background-color: #F2C663;");
        Label l2 = new Label();
        l2.setStyle("-fx-color: black;");
        l2.setText("Neuspesno ste " + action + " "+ upit + ".");
        Button b3 = new Button();
        b3.setText("OK");
        layout1.getChildren().addAll(l2,b3);
        layout1.setAlignment(Pos.CENTER);
        Scene s1 = new Scene(layout1);
        b3.setOnAction(evt ->{
            stage1.close();
        });
        stage1.setScene(s1);
        stage1.showAndWait();
       }
}
