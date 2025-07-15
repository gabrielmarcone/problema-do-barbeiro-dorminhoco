/* ***************************************************************
* Autor: Gabriel Marcone Magalhaes Santos
* Matricula........: 202410374
* Inicio...........: 17/06/2025
* Ultima alteracao.: 17/06/2025
* Nome.............: ControllerTelaInformacoes.java
* Funcao...........: Controlar o funcionamento da tela de informacoes
*************************************************************** */
package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControllerTelaInformacoes implements Initializable {
  @FXML
  private Button botaoVoltarInfo; // Botao "Voltar" que retorna para a tela inicial

  @FXML
  private ImageView imagemBotaoVoltarInfo; // Imagem do botao "Voltar" que sera exibida quando o mouse nao estiver sobre o botao

  @FXML
  private ImageView imagemBotaoVoltarInfoDetectado; // Imagem do botao "Voltar" que sera exibida quando o mouse estiver sobre o botao
  /* ***************************************************************
  * Metodo: acaoBotaoVoltarInfo
  * Funcao: Metodo que define a acao do botao "Voltar" ao ser clicado.
    Ele carrega a tela inicial (scene1.fxml) e exibe-a ao usuario.
  * Parametros: ActionEvent event: Evento de acao do botao "Voltar".
  * Retorno: void
  *************************************************************** */
  @FXML
  void acaoBotaoVoltarInfo(ActionEvent event) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("../view/scene1.fxml")); // Carrega o .fxml da primeira tela
      Scene scene = new Scene(root); // Cria um novo objeto Scene
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Converte o objeto obtido inicialmente em um botao, e depois em um stage
      stage.setScene(scene); // Define a scene
      stage.setTitle("Puffle Shop - Pinguim Dorminhoco");
      stage.show(); // Exibe a janela para o usuario
    } catch (IOException exception) {
      System.out.println(exception.getMessage()); // Caso ocorra um erro ao carregar a interface, a mensagem de erro e exibida no console
    }
  }
  /* ***************************************************************
  * Metodo: initialize
  * Funcao: Metodo sobrecarregado que inicializa a tela, configurando
    o comportamento do botao "Voltar" ao passar o mouse sobre ele.
  * Parametros: URL location: Localizacao do arquivo FXML,
    ResourceBundle resources: Recursos para internacionalizacao.
  * Retorno: void
  *************************************************************** */
  @Override
  public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
    // Configura o botao "Voltar" para mudar a imagem quando o mouse passa sobre ele
    botaoVoltarInfo.setOnMouseEntered(e -> {
      imagemBotaoVoltarInfo.setVisible(false);
      imagemBotaoVoltarInfoDetectado.setVisible(true);
    });
    // Configura o botao "Voltar" para voltar a imagem original quando o mouse sai
    botaoVoltarInfo.setOnMouseExited(e -> {
      imagemBotaoVoltarInfo.setVisible(true);
      imagemBotaoVoltarInfoDetectado.setVisible(false);
    });
  }
}