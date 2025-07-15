/* ***************************************************************
* Autor: Gabriel Marcone Magalhaes Santos
* Matricula........: 202410374
* Inicio...........: 05/06/2025
* Ultima alteracao.: 17/06/2025
* Nome.............: Principal.java
* Funcao...........: Classe principal do problema classico "Barbeiro Dorminhoco",
  com layout do jogo Club Penguin, onde esta inserido o main do arquivo.
*************************************************************** */
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import controller.ControllerTelaInicial;
import controller.ControllerTelaSimulacao;
import controller.ControllerTelaInformacoes;

public class Principal extends Application {
  /* ***************************************************************
  * Metodo: start
  * Funcao: Metodo principal da aplicacao JavaFX. Ele eh chamado quando o 
    aplicativo e iniciado pelo metodo launch(). Este metodo e responsavel
    por carregar a interface inicial e configurar a janela principal (Stage).
  * Parametros: Stage primaryStage: Representa a janela principal da aplicacao.
  * Retorno: void
  *************************************************************** */
  @Override
  public void start(Stage primaryStage) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("view/scene1.fxml")); // Carrega o arquivo FXML da primeira cena (scene1.fxml) que contem o layout inicial da aplicacao
      Scene scene = new Scene(root); // Cria um novo objeto Scene, passando o layout carregado como parametro
      Image icon = new Image("image/iconeAplicacao.png"); // Carrega uma imagem para ser usada como icone da aplicacao
      primaryStage.setResizable(false); // Impede que o usuario redimensione a janela
      primaryStage.getIcons().add(icon); // Define o icone da aplicacao na barra de titulo
      primaryStage.setTitle("Puffle Shop - Pinguim Dorminhoco"); // Define o titulo da aplicacao que aparece na barra superior da janela
      primaryStage.setScene(scene); // Define a cena criada como a cena principal da aplicacao
      primaryStage.show(); // Exibe a janela para o usuario
      primaryStage.setOnCloseRequest(e -> System.exit(0)); // Encerra a thread quando o usuario fecha a janela
    } catch (IOException exception) {
      System.out.println(exception.getMessage()); // Caso ocorra um erro ao carregar a interface, a mensagem de erro e exibida no console
    }
  }
  /* ***************************************************************
  * Metodo: main
  * Funcao: Metodo principal do programa. Responsavel por iniciar a aplicacao JavaFX.
    Ele chama o metodo launch(), que por sua vez inicializa o sistema JavaFX e invoca o metodo start().
  * Parametros: String[] args: Argumentos de linha de comando.
  * Retorno: void
  *************************************************************** */
  public static void main(String[] args) {
    launch(args); // Inicia a aplicacao JavaFX
  } 
}