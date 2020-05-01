package controllers;

import ORM.CasesDatasource;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import entities.Case;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.ResourceBundle;

public class casesSceneController extends userSceneController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        CasesDatasource.getInstance().getCases();
        prepareSlideMenuAnimation();
    }





}
