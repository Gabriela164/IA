/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2SEModule/module-info.java to edit this template
 */

module ajedrez {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens ia.ajedrez to javafx.fxml;
    
    exports ia.ajedrez;
}
