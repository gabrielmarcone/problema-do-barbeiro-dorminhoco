/* ***************************************************************
* Autor: Gabriel Marcone Magalhaes Santos
* Matricula........: 202410374
* Inicio...........: 05/06/2025
* Ultima alteracao.: 17/06/2025
* Nome.............: ControllerTelaSimulacao.java
* Funcao...........: Controlar o funcionamento da tela de simulacao,
  onde sera exibida a simulacao do problema classico, com o pinguim
  barbeiro dormindo quando nao houver puffles (clientes) e acordando
  quando houver puffles (clientes) para serem atendidos.
*************************************************************** */
package controller;

import java.util.List;
import java.util.concurrent.*;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Barbeiro;
import model.Cliente;
import model.GeradorDeClientes;
import javafx.beans.value.ChangeListener;

public class ControllerTelaSimulacao implements Initializable {
  @FXML
  private Button botaoVoltar; // Botao para voltar a tela inicial

  @FXML
  private ImageView imagemBotaoVoltar; // Imagem do botao "Voltar"

  @FXML
  private ImageView imagemBotaoVoltarDetectado; // Imagem do botao "Voltar" quando o mouse passa sobre ele

  @FXML
  private Button botaoReset; // Botao para resetar a simulacao

  @FXML
  private ImageView imagemBotaoReset; // Imagem do botao "Reset"

  @FXML
  private ImageView imagemBotaoResetDetectado; // Imagem do botao "Reset" quando o mouse passa sobre ele

  @FXML
  private Button botaoPlayAndPauseBarbeiro; // Botao para pausar e retomar a simulacao do barbeiro (pinguim)

  @FXML
  private ImageView imagemBotaoPauseBarbeiro; // Imagem do botao "Pause" para barbeiro (pinguim)

  @FXML
  private ImageView imagemBotaoPauseBarbeiroDetectado; // Imagem do botao "Pause" quando o mouse passa sobre ele

  @FXML
  private ImageView imagemBotaoPlayBarbeiro; // Imagem do botao "Play" para barbeiro (pinguim)

  @FXML
  private ImageView imagemBotaoPlayBarbeiroDetectado; // Imagem do botao "Play" quando o mouse passa sobre ele

  public static boolean barbeiroPausado = false; // Variavel para controlar se o barbeiro (pinguim) esta pausado ou nao

  @FXML
  private Button botaoPlayAndPauseCliente; // Botao para pausar e retomar a entrada de clientes (puffles)

  @FXML
  private ImageView imagemBotaoPauseCliente; // Imagem do botao "Pause" para clientes (puffles)

  @FXML
  private ImageView imagemBotaoPauseClienteDetectado; // Imagem do botao "Pause" quando o mouse passa sobre ele

  @FXML
  private ImageView imagemBotaoPlayCliente; // Imagem do botao "Play" para clientes (puffles)

  @FXML
  private ImageView imagemBotaoPlayClienteDetectado; // Imagem do botao "Play" quando o mouse passa sobre ele

  public static boolean clientesPausados = false; // Variavel para controlar se os clientes (puffles) estao pausados ou nao

  @FXML
  public Slider sliderBarbeiro; // Slider para ajustar a velocidade barbeiro (pinguim) poda o pelo dos puffles (clientes)
  private int velocidadeBarbeiro; // Variavel para armazenar a velocidade do barbeiro (pinguim) que sera exibida no label

  @FXML
  private Slider sliderClientes; // Slider para ajustar a velocidade que os clientes (puffles) entram na sala de espera
  private int velocidadeClientes; // Variavel para armazenar a velocidade dos clientes (puffles) que sera exibida no label

  @FXML
  public ImageView barbeiroCortando; // Imagem do barbeiro (pinguim) cortando cabelo na tela de simulacao
  @FXML
  public ImageView barbeiroDormindo; // Imagem do barbeiro (pinguim) dormindo na tela de simulacao
  @FXML
  public ImageView simboloDormindo; // Imagem do simbolo de dormir na tela de simulacao
  @FXML
  public ImageView tesouraCortando; // Imagem da tesoura cortando cabelo na tela de simulacao
  @FXML
  public ImageView imagemBarbeiroPausado; // Imagem que mostra o pause do barbeiro na tela de simulacao

  @FXML
  public ImageView cliente; // Imagem do cliente (puffle) na tela de simulacao
  @FXML
  public ImageView geradorDeClientesPausado; // Imagem que mostra que o gerador de clientes (puffles) na tela de simulacao foi pausado

  @FXML
  public AnchorPane AnchorPane; // AnchorPane onde as imagens do barbeiro (pinguim) e dos clientes (puffles) serao exibidas

  public static final int CADEIRAS = 5; // Numero de cadeiras na sala de espera
  public static Semaphore clientes = new Semaphore(0); // Semaforo para controlar o numero de clientes (puffles)
  public static Semaphore barbeiro = new Semaphore(0); // Semaforo para controlar o barbeiro (pinguim)
  public static Semaphore mutex = new Semaphore(1); // Semaforo para garantir exclusao mutua
  public static int esperando = 0; // Contador de clientes (puffles) esperando para serem atendidos

  private Barbeiro barbeiroThread; // Variavel para armazenar a thread do barbeiro (pinguim)
  public GeradorDeClientes geradorClientes; // Variavel para armazenar a thread que gera os clientes (puffles)

  public static Cliente[] clientesNaFila = new Cliente[CADEIRAS]; // Array para armazenar os clientes (puffles) na fila de espera
  public static int[] posicoesBancoX = {207, 268, 333, 396, 458}; // LayoutX das cadeiras

  public static List<Cliente> todosClientes = new CopyOnWriteArrayList<>(); // Lista para armazenar todos os clientes (puffles) que foram criados

  /* ***************************************************************
  * Metodo: resetarVariaveisSemaforos
  * Funcao: Metodo que reseta as variaveis semaforos e o contador de clientes esperando.
    Ele deve ser chamado quando a simulacao for reiniciada ou resetada.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void resetarVariaveisSemaforos() {
    clientes = new Semaphore(0); // Reseta o semaforo de clientes
    barbeiro = new Semaphore(0); // Reseta o semaforo do barbeiro
    mutex = new Semaphore(1); // Reseta o semaforo de exclusao mutua
    esperando = 0; // Reseta o contador de clientes esperando

    barbeiroPausado = false; // Reseta a variavel que controla se o barbeiro esta pausado ou nao
    clientesPausados = false; // Reseta a variavel que controla se os clientes estao pausados ou nao
  }
  /* ***************************************************************
  * Metodo: resetarImagensAnimacoes
  * Funcao: Metodo que reseta as imagens e animacoes do barbeiro (pinguim) e dos clientes (puffles).
    Ele deve ser chamado quando a simulacao for reiniciada ou resetada.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void resetarImagensAnimacoes() {
    Platform.runLater(() -> {
      // Volta as imagens padroes do barbeiro
      barbeiroCortando.setVisible(false);
      barbeiroDormindo.setVisible(true);
      simboloDormindo.setVisible(true);
      tesouraCortando.setVisible(false);
      imagemBarbeiroPausado.setVisible(false); // Reseta a imagem do barbeiro pausado
      geradorDeClientesPausado.setVisible(false); // Reseta a imagem do gerador de clientes pausado
    });
  }
  /* ***************************************************************
  * Metodo: limparFilaDeClientes
  * Funcao: Metodo que limpa a fila de clientes (puffles) na sala de espera.
    Ele deve ser chamado quando a simulacao for reiniciada ou resetada.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void limparFilaDeClientes() {
    for (int i = 0; i < clientesNaFila.length; i++) {
      clientesNaFila[i] = null; // Limpa a fila de clientes
    }
  }
  /* ***************************************************************
  * Metodo: resetarClientes
  * Funcao: Metodo que reseta as imagens clientes (puffles) na tela de simulacao.
    Alem de interromper as threads dos clientes (puffles) e remover suas imagens do AnchorPane.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void resetarClientes() {
    for (Cliente c : todosClientes) {
      if (c != null && c.isAlive()) {
        c.interrupt(); // Interrompe a execucao
      }
      Platform.runLater(() -> {
        c.interrupt();
        AnchorPane.getChildren().remove(c.getImagem()); // Remove imagem do AnchorPane
      });
      c.sleepThread(); // Espera um pouco para garantir que as imagens sejam removidas
    }
    todosClientes.clear(); // Limpa a lista
  }
  /* ***************************************************************
  * Metodo: acaoBotaoVoltar
  * Funcao: Metodo que define a acao do botao "Voltar". Ele carrega a
    tela inicial (scene1.fxml) quando o botao e clicado.
  * Parametros: ActionEvent event: Evento de acao do botao "Voltar".
  * Retorno: void
  *************************************************************** */
  @FXML
  void acaoBotaoVoltar(ActionEvent event) {
    try {
      // Para as threads ativas antes de mudar de cena
      if (barbeiroThread != null && barbeiroThread.isAlive()) {
        barbeiroThread.interrupt();
      }
      if (geradorClientes != null && geradorClientes.isAlive()) {
        geradorClientes.interrupt(); // Para a thread do gerador de clientes se ela estiver ativa
      }
      resetarImagensAnimacoes();
      resetarClientes();
      resetarVariaveisSemaforos(); // Reseta as variaveis semaforos e o contador de clientes esperando
      limparFilaDeClientes(); // Limpa a fila de clientes
      Parent root = FXMLLoader.load(getClass().getResource("../view/scene1.fxml")); // Carrega o .fxml da primeira tela
      Scene scene = new Scene(root); // Cria um novo objeto Scene
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Converte o objeto obtido inicialmente em um botao, e depois em um stage
      stage.setScene(scene); // Define a scene
      stage.setTitle("Puffle Shop - Pinguim Dorminhoco");
      stage.show(); // Exibe a janela para o usuario
    } catch (Exception exception) {
        System.out.println(exception.getMessage());
    }
  }
  /* ***************************************************************
  * Metodo: acaoBotaoPlayAndPauseBarbeiro
  * Funcao: Metodo que define a acao do botao "Play/Pause". Ele alterna
    entre pausar e retomar a simulacao do barbeiro (pinguim) quando o botao e clicado.
  * Parametros: ActionEvent event: Evento de acao do botao "Play/Pause".
  * Retorno: void
  *************************************************************** */
  @FXML
  void acaoBotaoPlayAndPauseBarbeiro(ActionEvent event) {
    barbeiroPausado = !barbeiroPausado;
    if (barbeiroPausado) {
      // Troca para modo "Play"
      imagemBotaoPauseBarbeiro.setVisible(false);
      imagemBotaoPauseBarbeiroDetectado.setVisible(false);
      imagemBotaoPlayBarbeiro.setVisible(true);
      imagemBotaoPlayBarbeiroDetectado.setVisible(false);
      imagemBarbeiroPausado.setVisible(true); // Exibe a imagem do barbeiro pausado

      System.out.println("Barbeiro pausado!");
    } else {
      // Troca para modo "Pause"
      imagemBotaoPlayBarbeiro.setVisible(false);
      imagemBotaoPlayBarbeiroDetectado.setVisible(false);
      imagemBotaoPauseBarbeiro.setVisible(true);
      imagemBotaoPauseBarbeiroDetectado.setVisible(false);
      imagemBarbeiroPausado.setVisible(false); // Esconde a imagem do barbeiro pausado

      System.out.println("Barbeiro retomado!");
    }
  }
  /* ***************************************************************
  * Metodo: acaoBotaoPlayAndPauseCliente
  * Funcao: Metodo que define a acao do botao "Play/Pause" para os clientes (puffles).
    Ele alterna entre pausar e retomar a entrada de clientes (puffles) quando o botao e clicado.
  * Parametros: ActionEvent event: Evento de acao do botao "Play/Pause" para clientes.
  * Retorno: void
  *************************************************************** */
  @FXML
  void acaoBotaoPlayAndPauseCliente(ActionEvent event) {
    clientesPausados = !clientesPausados;
    if (clientesPausados) {
      // Troca para modo "Play"
      imagemBotaoPauseCliente.setVisible(false);
      imagemBotaoPauseClienteDetectado.setVisible(false);
      imagemBotaoPlayCliente.setVisible(true);
      imagemBotaoPlayClienteDetectado.setVisible(false);
      geradorDeClientesPausado.setVisible(true); // Exibe a imagem do gerador de clientes pausado

      System.out.println("Geracao de clientes pausada!");
    } else {
      // Troca para modo "Pause"
      imagemBotaoPlayCliente.setVisible(false);
      imagemBotaoPlayClienteDetectado.setVisible(false);
      imagemBotaoPauseCliente.setVisible(true);
      imagemBotaoPauseClienteDetectado.setVisible(false);
      geradorDeClientesPausado.setVisible(false); // Esconde a imagem do gerador de clientes pausado

      System.out.println("Geracao de clientes retomada!");
    }
  }
  /* ***************************************************************
  * Metodo: resetarImagensBotoesPlayAndPause
  * Funcao: Metodo que reseta as imagens dos botoes "Play/Pause" para barbeiro e clientes.
    Ele deve ser chamado quando a simulacao for reiniciada ou resetada.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void resetarImagensBotoesPlayAndPause() {
    // Reseta as imagens dos botoes "Play/Pause" para barbeiro e clientes
    imagemBotaoPauseBarbeiro.setVisible(true);
    imagemBotaoPauseBarbeiroDetectado.setVisible(false);
    imagemBotaoPlayBarbeiro.setVisible(false);
    imagemBotaoPlayBarbeiroDetectado.setVisible(false);
    
    imagemBotaoPauseCliente.setVisible(true);
    imagemBotaoPauseClienteDetectado.setVisible(false);
    imagemBotaoPlayCliente.setVisible(false);
    imagemBotaoPlayClienteDetectado.setVisible(false);
  }
  /* ***************************************************************
  * Metodo: acaoBotaoReset
  * Funcao: Metodo que define a acao do botao "Reset". Ele reseta os sliders
    de velocidade do barbeiro e dos clientes para o valor inicial, a posicao
    do barbeiro e dos clientes para o valor inicial, e reinicia a simulacao.
  * Parametros: ActionEvent event: Evento de acao do botao "Reset".
  * Retorno: void
  *************************************************************** */
  @FXML
  void acaoBotaoReset(ActionEvent event) {
    if (barbeiroThread != null && barbeiroThread.isAlive()) {
      barbeiroThread.interrupt(); // Para a thread do barbeiro se ela estiver ativa
    }
    if (geradorClientes != null && geradorClientes.isAlive()) {
      geradorClientes.interrupt(); // Para a thread do gerador de clientes se ela estiver ativa
    }
    resetarImagensAnimacoes();
    resetarClientes(); // Reseta os clientes na tela de simulacao
    resetarVariaveisSemaforos(); // Reseta as variaveis semaforos e o contador de clientes esperando
    limparFilaDeClientes(); // Limpa a fila de clientes
    // Cria novas instancias
    barbeiroThread = new Barbeiro(this, sliderBarbeiro);
    geradorClientes = new GeradorDeClientes(this, sliderClientes, cliente);
    barbeiroThread.setDaemon(true); // Define a thread do barbeiro como daemon
    geradorClientes.setDaemon(true); // Define a thread do gerador de clientes como daemon
    barbeiroThread.start();
    geradorClientes.start();

    // Reseta os labels de velocidade
    sliderBarbeiro.setValue(5);
    sliderClientes.setValue(5);

    resetarImagensBotoesPlayAndPause(); // Reseta as imagens dos botoes "Play/Pause" para barbeiro e clientes

    System.out.println("Simulacao resetada!"); // Mensagem de confirmacao no console
  }
  /* ***************************************************************
  * Metodo: iniciarBotoes
  * Funcao: Metodo que inicia os botoes e suas acoes. Ele deve ser chamado
    no metodo initialize() para garantir que os botoes estejam configurados
    corretamente quando a tela de simulacao for carregada.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void iniciarBotoes() {
    // Configura o botao "Voltar" para mudar a imagem quando o mouse passa sobre ele
    botaoVoltar.setOnMouseEntered(e -> {
      imagemBotaoVoltar.setVisible(false);
      imagemBotaoVoltarDetectado.setVisible(true);
    });
    // Configura o botao "Voltar" para voltar a imagem original quando o mouse sai
    botaoVoltar.setOnMouseExited(e -> {
      imagemBotaoVoltar.setVisible(true);
      imagemBotaoVoltarDetectado.setVisible(false);
    });
    /* *************************************************************** */
    // Configura o botao "Reset" para mudar a imagem quando o mouse passa sobre ele
    botaoReset.setOnMouseEntered(e -> {
      imagemBotaoReset.setVisible(false);
      imagemBotaoResetDetectado.setVisible(true);
    });
    // Configura o botao "Reset" para voltar a imagem original quando o mouse sai
    botaoReset.setOnMouseExited(e -> {
      imagemBotaoReset.setVisible(true);
      imagemBotaoResetDetectado.setVisible(false);
    });
    /* *************************************************************** */
    // Configura o botao "Play/Pause" do barbeiro para mudar a imagem quando o mouse passa sobre ele
    botaoPlayAndPauseBarbeiro.setOnMouseEntered(e -> {
      if (barbeiroPausado) {
        imagemBotaoPlayBarbeiro.setVisible(false);
        imagemBotaoPlayBarbeiroDetectado.setVisible(true);
      } else {
        imagemBotaoPauseBarbeiro.setVisible(false);
        imagemBotaoPauseBarbeiroDetectado.setVisible(true);
      }
    });
    // Configura o botao "Play/Pause" do barbeiro para voltar a imagem original quando o mouse sai
    botaoPlayAndPauseBarbeiro.setOnMouseExited(e -> {
      if (barbeiroPausado) {
        imagemBotaoPlayBarbeiro.setVisible(true);
        imagemBotaoPlayBarbeiroDetectado.setVisible(false);
      } else {
        imagemBotaoPauseBarbeiro.setVisible(true);
        imagemBotaoPauseBarbeiroDetectado.setVisible(false);
      }
    });
    /* *************************************************************** */
    // Configura o botao "Play/Pause" dos clientes para mudar a imagem quando o mouse passa sobre ele
    botaoPlayAndPauseCliente.setOnMouseEntered(e -> {
      if (clientesPausados) {
        imagemBotaoPlayCliente.setVisible(false);
        imagemBotaoPlayClienteDetectado.setVisible(true);
      } else {
        imagemBotaoPauseCliente.setVisible(false);
        imagemBotaoPauseClienteDetectado.setVisible(true);
      }
    });
    // Configura o botao "Play/Pause" dos clientes para voltar a imagem original quando o mouse sai
    botaoPlayAndPauseCliente.setOnMouseExited(e -> {
      if (clientesPausados) {
        imagemBotaoPlayCliente.setVisible(true);
        imagemBotaoPlayClienteDetectado.setVisible(false);
      } else {
        imagemBotaoPauseCliente.setVisible(true);
        imagemBotaoPauseClienteDetectado.setVisible(false);
      }
    });
  }
  /* ***************************************************************
  * Metodo: initialize
  * Funcao: Metodo que inicializa a tela de simulacao. Ele e chamado automaticamente
    quando a tela e carregada. Este metodo configura os listeners dos sliders,
    inicia os botoes e as threads do barbeiro e do gerador de clientes.
  * Parametros: java.net.URL location: Localizacao do arquivo FXML.
    java.util.ResourceBundle resources: Recursos utilizados na tela de simulacao.
  * Retorno: void
  *************************************************************** */
  @Override
  public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
     sliderBarbeiro.valueProperty().addListener(new ChangeListener<Number>() {
      /* **************************************************************
      * Metodo: changed
      * Funcao: Metodo que define o comportamento do slider de velocidade do barbeiro.
        Ele atualiza a variavel velocidadeBarbeiro com o valor do slider quando o valor muda.
      * Parametros: ObservableValue<? extends Number> observable, Number oldValue,
        Number newValue
      * Retorno: void
      *************************************************************** */
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        velocidadeBarbeiro = (int) (sliderBarbeiro.getValue()); // Passa o valor do slider, que eh variavel, para a velocidade
      }
    });
    sliderClientes.valueProperty().addListener(new ChangeListener<Number>() {
      /* **************************************************************
      * Metodo: changed
      * Funcao: Metodo que define o comportamento do slider de velocidade do barbeiro.
        Ele atualiza a variavel velocidadeBarbeiro com o valor do slider quando o valor muda.
      * Parametros: ObservableValue<? extends Number> observable, Number oldValue,
        Number newValue
      * Retorno: void
      *************************************************************** */
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        velocidadeClientes = (int) (sliderClientes.getValue()); // Passa o valor do slider, que eh variavel, para a velocidade
      }
    });
    iniciarBotoes(); // Inicia os botoes e suas acoes
    // Inicia as threads do barbeiro e do gerador de clientes
    barbeiroThread = new Barbeiro(this, sliderBarbeiro);
    geradorClientes = new GeradorDeClientes(this, sliderClientes, cliente);
    barbeiroThread.setDaemon(true); // Define a thread do barbeiro como daemon
    geradorClientes.setDaemon(true); // Define a thread do gerador de clientes como daemon
    barbeiroThread.start(); // Inicia o barbeiro
    geradorClientes.start(); // Inicia o gerador de clientes
  }
}