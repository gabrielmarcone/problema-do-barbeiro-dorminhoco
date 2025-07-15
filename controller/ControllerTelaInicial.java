/* ***************************************************************
* Autor: Gabriel Marcone Magalhaes Santos
* Matricula........: 202410374
* Inicio...........: 05/06/2025
* Ultima alteracao.: 17/06/2025
* Nome.............: ControllerTelaInicial.java
* Funcao...........: Controlar o funcionamento da primeira tela e acao do botao "Iniciar"
*************************************************************** */
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControllerTelaInicial implements Initializable {
  @FXML
  private Button botaoIniciar; // Botao "Iniciar" que inicia a simulacao do problema classico

  @FXML
  private ImageView imagemBotaoIniciar; // Imagem do botao "Iniciar" que sera exibida quando o mouse nao estiver sobre o botao

  @FXML
  private ImageView imagemBotaoIniciarDetectado; // Imagem do botao "Iniciar" que sera exibida quando o mouse estiver sobre o botao

  @FXML
  private Button botaoInformacoes; // Botao "Informacoes" que exibe informacoes sobre a simulacao

  @FXML
  private ImageView imagemBotaoInformacoes; // Imagem do botao "Informacoes" que sera exibida quando o mouse nao estiver sobre o botao

  @FXML
  private ImageView imagemBotaoInformacoesDetectado; // Imagem do botao "Informacoes" que sera exibida quando o mouse estiver sobre o botao

  /* ***************************************************************
  * Metodo: initialize
  * Funcao: Metodo sobrecarregado que inicializa a tela, configurando
    o comportamento do botao "Iniciar" ao passar o mouse sobre ele.
  * Parametros: URL location: Localizacao do arquivo FXML,
    ResourceBundle resources: Recursos para internacionalizacao.
  * Retorno: void
  *************************************************************** */
  @Override
  public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
    // Configura o botao "Iniciar" para mudar a imagem quando o mouse passa sobre ele
    botaoIniciar.setOnMouseEntered(e -> {
      imagemBotaoIniciar.setVisible(false);
      imagemBotaoIniciarDetectado.setVisible(true);
    });
    // Configura o botao "Iniciar" para voltar a imagem original quando o mouse sai
    botaoIniciar.setOnMouseExited(e -> {
      imagemBotaoIniciar.setVisible(true);
      imagemBotaoIniciarDetectado.setVisible(false);
    });
    // Configura o botao "Informacoes" para mudar a imagem quando o mouse passa sobre ele
    botaoInformacoes.setOnMouseEntered(e -> {
      imagemBotaoInformacoes.setVisible(false);
      imagemBotaoInformacoesDetectado.setVisible(true);
    });
    // Configura o botao "Informacoes" para voltar a imagem original quando o mouse sai
    botaoInformacoes.setOnMouseExited(e -> {
      imagemBotaoInformacoes.setVisible(true);
      imagemBotaoInformacoesDetectado.setVisible(false);
    });
  }
  @FXML
  void acaoBotaoInformacoes(ActionEvent event) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("../view/info.fxml")); // Carrega o arquivo FXML da terceira cena (scene3.fxml) que contem as informacoes sobre a simulacao
      Scene scene = new Scene(root); // Obtem a janela atual e define a nova cena
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.setTitle("Puffle Shop - Informacoes");
    } catch (Exception exception) {
        System.out.println(exception.getMessage()); // Caso ocorra um erro ao carregar a interface, a mensagem de erro e exibida no console
    }
  }
  /* ***************************************************************
  * Metodo: acaoBotaoIniciar
  * Funcao: Muda para segunda tela, iniciando a simulacao do problema classico.
  * Parametros: ActionEvent event: Evento disparado pelo botao "Iniciar".
  * Retorno: void
  *************************************************************** */
  @FXML
  void acaoBotaoIniciar(ActionEvent event) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("../view/scene2.fxml")); // Carrega o arquivo FXML da segunda cena (scene2.fxml) que contem o layout principal da aplicacao
      Scene scene = new Scene(root); // Obtem a janela atual e define a nova cena
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.setTitle("Puffle Shop - Pinguim Dorminhoco");
    } catch (Exception exception) {
        System.out.println(exception.getMessage()); // Caso ocorra um erro ao carregar a interface, a mensagem de erro e exibida no console
    }
  }
}