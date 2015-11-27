/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panezoompandemonstrator;


import javafx.geometry.Point2D;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author ben
 */
public class ZoomPane extends Pane {
    private static Point2D mouseInitialPosition;
    public ZoomPane(Pane view){
        this.getChildren().add(view);
        this.setOnScroll(this::onMouseScroll);
        this.setOnMousePressed(this::onMousePressed);
        this.setOnMouseDragged(this::onMouseDragged);
    }
    
    public void onMousePressed(MouseEvent event){
        mouseInitialPosition = new Point2D(event.getX(),event.getY());
    }
    
    public void onMouseDragged(MouseEvent event){
        pan(event.getX()-mouseInitialPosition.getX(),event.getY()-mouseInitialPosition.getY());
        mouseInitialPosition =  new Point2D(event.getX(),event.getY());
    }
    
    public void onMouseScroll(ScrollEvent event){
        zoom(event.getX(),event.getY(), event.getDeltaY()/1000d);
    }
    
    private void zoom(double x, double y, double scaleFactor){
        
        this.getChildren().forEach(child->{
            Point2D stationaryPoint = new Point2D(x, y);
            Point2D childCentre = new Point2D(
                    child.getLayoutX()+child.getLayoutBounds().getWidth()/2,
                    child.getLayoutY()+child.getLayoutBounds().getHeight()/2
            );
            Point2D magnitudeChange = new Point2D(
                    (childCentre.getX()-stationaryPoint.getX())*(scaleFactor/child.getScaleX()),
                    (childCentre.getY()-stationaryPoint.getY())*(scaleFactor/child.getScaleY())
            );
            child.setScaleX(child.getScaleX()+scaleFactor);
            child.setScaleY(child.getScaleY()+scaleFactor);
            child.setLayoutX(child.getLayoutX()+magnitudeChange.getX());
            child.setLayoutY(child.getLayoutY()+magnitudeChange.getY());
        });
        
    }
    
    private void pan(double x, double y){
        this.getChildren().forEach(child->{
            child.setLayoutX(child.getLayoutX()+x);
            child.setLayoutY(child.getLayoutY()+y);
        });
    }
}
