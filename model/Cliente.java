/* ***************************************************************
* Autor: Gabriel Marcone Magalhaes Santos
* Matricula........: 202410374
* Inicio...........: 05/06/2025
* Ultima alteracao.: 17/06/2025
* Nome.............: Cliente.java
* Funcao...........: Criar e configurar a Thread que representa os clientes no problema classico "Barbeiro Dorminhoco".
*************************************************************** */
package model;

import controller.ControllerTelaSimulacao;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cliente extends Thread {
  private ControllerTelaSimulacao controller; // Controlador da tela de simulacao
  private Slider sliderVelocidade; // Slider para controlar a velocidade do cliente
  private ImageView imagemCliente; // Imagem do cliente que sera exibida na tela

  private String clienteAndandoFrente = getClass().getResource("../image/andandoDeFrente.gif").toExternalForm();
  private Image imagemClienteFrente = new Image(clienteAndandoFrente);

  private String cleinteAndandoLado = getClass().getResource("../image/andandoDeLado.gif").toExternalForm();
  private Image imagemClienteLado = new Image(cleinteAndandoLado);

  private String clienteAndandoCostas = getClass().getResource("../image/andandoDeCostas.gif").toExternalForm();
  private Image imagemClienteCostas = new Image(clienteAndandoCostas);

  private String clienteBravo = getClass().getResource("../image/clienteBravo.gif").toExternalForm();
  private Image imagemClienteBravo = new Image(clienteBravo);

  private String clienteAtendidoAndandoLado = getClass().getResource("../image/clienteAtendidoAndandoDeLado.gif").toExternalForm();
  private Image imagemClienteAtendidoLado = new Image(clienteAtendidoAndandoLado);

  private String clienteAtendidoAndandoCostas = getClass().getResource("../image/clienteAtendidoAndandoDeCostas.gif").toExternalForm();
  private Image imagemClienteAtendidoCostas = new Image(clienteAtendidoAndandoCostas);

  private String clienteSendoAtendido = getClass().getResource("../image/clienteSendoAtendido.png").toExternalForm();
  private Image imagemClienteSendoAtendido = new Image(clienteSendoAtendido);

  private String clienteEsperando = getClass().getResource("../image/clienteEsperando.png").toExternalForm();
  private Image imagemClienteEsperando = new Image(clienteEsperando);
  /* ***************************************************************
  * Metodo: Cliente
  * Funcao: Construtor da classe Cliente. Ele inicializa a thread do cliente e configura a imagem do cliente.
  * Parametros: ControllerTelaSimulacao controller: Controlador da tela de simulacao.
    ImageView imagemClienteModelo: Modelo de imagem do cliente para definir tamanho e posicao.
  * Retorno: void
  *************************************************************** */
  public Cliente (ControllerTelaSimulacao controller, ImageView imagemClienteModelo, Slider sliderVelocidade) {
    this.controller = controller; // Define a thread do cliente como daemon, para que ela possa ser finalizada quando a aplicacao for encerrada

    this.imagemCliente = new ImageView();
    this.imagemCliente.setImage(imagemClienteFrente); 
    this.imagemCliente.setFitWidth(imagemClienteModelo.getFitWidth()); // Define a largura da imagem do cliente
    this.imagemCliente.setFitHeight(imagemClienteModelo.getFitHeight()); // Define a altura da imagem do cliente
    this.imagemCliente.setLayoutX(imagemClienteModelo.getLayoutX()); // Define a posicao X da imagem do cliente
    this.imagemCliente.setLayoutY(imagemClienteModelo.getLayoutY()); // Define a posicao Y da imagem do cliente
    this.imagemCliente.setVisible(false); // A imagem do cliente comeca invisivel
    Platform.runLater(() -> controller.AnchorPane.getChildren().add(imagemCliente)); // Adiciona a imagem do cliente ao AnchorPane da tela de simulacao
    this.sliderVelocidade = sliderVelocidade; // Slider para controlar a velocidade do cliente
    ControllerTelaSimulacao.todosClientes.add(this);
  }
  /* ***************************************************************
  * Metodo: getVelocidade
  * Funcao: Metodo para obter o valor do slider de velocidade.
  * Parametros: Nenhum.
  * Retorno: double - Retorna o valor do slider de velocidade.
  *************************************************************** */
  public double getVelocidade() {
    return sliderVelocidade.getValue(); // Retorna o valor do slider de velocidade
  }
  /* ***************************************************************
  * Metodo: getImagem
  * Funcao: Metodo para obter a imagem do cliente.
  * Parametros: Nenhum.
  * Retorno: ImageView - Retorna a imagem do cliente.
  *************************************************************** */
  public ImageView getImagem() {
    return imagemCliente;
  }
  /* ***************************************************************
  * Metodo: sleepThread
  * Funcao: Metodo para fazer a thread Cliente dormir por um tempo determinado.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void sleepThread() {
    try {
      Thread.sleep(21);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      Platform.runLater(() -> controller.AnchorPane.getChildren().remove(imagemCliente));
      return; // Encerra a thread imediatamente
    }
  }
  /* ***************************************************************
  * Metodo: clienteEntrando
  * Funcao: Metodo que simula o cliente entrando na sala de espera. Ele move a imagem do cliente para baixo e depois para a direita.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void clienteEntrando() {
    Platform.runLater(()-> {
      this.imagemCliente.setImage(imagemClienteFrente);
      this.imagemCliente.setVisible(true);
    });
    while (imagemCliente.getLayoutY() <= 246) {
      if (Thread.interrupted()) return;
      if (imagemCliente.getLayoutY() < 247) {
        Platform.runLater(() -> {
          imagemCliente.setLayoutY(imagemCliente.getLayoutY() + 2); // Move o cliente para baixo
        });
      }
      sleepThread();
    }
    Platform.runLater(()-> imagemCliente.setImage(imagemClienteLado));
    while (imagemCliente.getLayoutX() <= 191) {
      if (Thread.interrupted()) return;
      if (imagemCliente.getLayoutX() < 192) {
        Platform.runLater(() -> {
          imagemCliente.setLayoutX(imagemCliente.getLayoutX() + 2); // Move o cliente para a direita
        });
      }
      sleepThread();
    }
    Platform.runLater(()-> { 
      this.imagemCliente.setVisible(false);
      this.imagemCliente.setLayoutX(69);
      this.imagemCliente.setLayoutY(138);
    });
  }
  /* ***************************************************************
  * Metodo: colocarClienteNaFila
  * Funcao: Metodo que coloca o cliente na fila de espera. Ele verifica se ha uma cadeira vazia e coloca o cliente na primeira cadeira disponivel.
    Se nao houver cadeiras disponiveis, o cliente nao sera adicionado a fila.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void colocarClienteNaFila() {
    for (int i = 0; i < ControllerTelaSimulacao.clientesNaFila.length; i++) {
      if (Thread.interrupted()) return;
      if (ControllerTelaSimulacao.clientesNaFila[i] == null) {
        int posicaoVazia = i;
        ControllerTelaSimulacao.clientesNaFila[i] = this;
        
        Platform.runLater(() -> {
          imagemCliente.setImage(imagemClienteEsperando);
          imagemCliente.setVisible(true);
          imagemCliente.setLayoutX(ControllerTelaSimulacao.posicoesBancoX[posicaoVazia]);
          imagemCliente.setLayoutY(136); // Altura padrAo da cadeira
        });
        return;
      }
    }
  }
  /* ***************************************************************
  * Metodo: removerClienteDaFila
  * Funcao: Metodo que remove o cliente da fila de espera. Ele procura o cliente na fila e o remove, liberando a cadeira.
    Se o cliente nao estiver na fila, nada acontece.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void removerClienteDaFila() {
    if (Thread.interrupted()) return;
    for (int i = 0; i < ControllerTelaSimulacao.clientesNaFila.length; i++) {
      if (ControllerTelaSimulacao.clientesNaFila[i] == this) {
        ControllerTelaSimulacao.clientesNaFila[i] = null; // Libera a cadeira
        Platform.runLater(() -> {
          imagemCliente.setVisible(false); // Esconde a imagem do cliente
        });
        return;
      }
    }
  }
  /* ***************************************************************
  * Metodo: clienteSendoAtendido
  * Funcao: Metodo que simula o cliente sendo atendido pelo barbeiro. Ele muda a imagem do cliente para
    "sendo atendido" e espera um tempo determinado.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void clienteSendoAtendido() {
    if (Thread.interrupted()) return;
    Platform.runLater(()-> {
      this.imagemCliente.setImage(imagemClienteSendoAtendido);
      this.imagemCliente.setVisible(true);
      this.imagemCliente.setLayoutX(333);
      this.imagemCliente.setLayoutY(260);
    });
    int tempoTotal = (int)(5000 / controller.sliderBarbeiro.getValue());
    int tempoPassado = 0;
    int fragmento = 20; // Milissegundos por fragmento

    while (tempoPassado < tempoTotal) {
      if (Thread.interrupted()) return;
      if (ControllerTelaSimulacao.barbeiroPausado) {
        try {
          Thread.sleep(100); // Espera enquanto esta pausado
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          Platform.runLater(() -> controller.AnchorPane.getChildren().remove(imagemCliente));
          return;
        }
        continue;
      }
      try {
        Thread.sleep(fragmento);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        Platform.runLater(() -> controller.AnchorPane.getChildren().remove(imagemCliente));
        return;
      }
      tempoPassado += fragmento;
    }
  }
  /* ***************************************************************
  * Metodo: clienteSaindoSatisfeito
  * Funcao: Metodo que simula o cliente saindo satisfeito da sala de espera.
    Ele move a imagem do cliente para a direita e depois para cima.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void clienteSaindoSatisfeito() {
    Platform.runLater(()-> {
      this.imagemCliente.setImage(imagemClienteAtendidoLado);
      this.imagemCliente.setVisible(true);
      this.imagemCliente.setLayoutX(470);
      this.imagemCliente.setLayoutY(246);
    });
    while (imagemCliente.getLayoutX() <= 606) {
      if (Thread.interrupted()) return;
      if (imagemCliente.getLayoutX() < 607) {
        Platform.runLater(() -> {
          imagemCliente.setLayoutX(imagemCliente.getLayoutX() + 2); // Move o cliente para a direita
        });
      }
      sleepThread();
    }
    Platform.runLater(()-> imagemCliente.setImage(imagemClienteAtendidoCostas));
    while (imagemCliente.getLayoutY() >= 136) {
      if (Thread.interrupted()) return;
      if (imagemCliente.getLayoutY() > 135) {
        Platform.runLater(() -> {
          imagemCliente.setLayoutY(imagemCliente.getLayoutY() - 2); // Move o cliente para cima
        });
      }
      sleepThread();
    }
    Platform.runLater(()-> { 
      this.imagemCliente.setVisible(false);
    });
  }
  /* ***************************************************************
  * Metodo: clienteSaindoBravo
  * Funcao: Metodo que simula o cliente saindo bravo da sala de espera.
    Ele move a imagem do cliente para a direita e depois para cima, mudando a imagem para "bravo".
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  public void clienteSaindoBravo() {
    Platform.runLater(()-> {
      imagemCliente.setImage(imagemClienteBravo);
      this.imagemCliente.setVisible(true);
      this.imagemCliente.setLayoutX(500);
      this.imagemCliente.setLayoutY(246);
    });
    try {
      Thread.sleep(900); // Espera 0.9 segundos
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      Platform.runLater(() -> controller.AnchorPane.getChildren().remove(imagemCliente));
      return; // Encerra a thread imediatamente
    }
    Platform.runLater(()-> imagemCliente.setImage(imagemClienteLado));
    while (imagemCliente.getLayoutX() <= 606) {
      if (Thread.interrupted()) return;
      if (imagemCliente.getLayoutX() < 607) {
        Platform.runLater(() -> {
          imagemCliente.setLayoutX(imagemCliente.getLayoutX() + 2); // Move o cliente para cima
        });
      }
      sleepThread();
    }
    Platform.runLater(()-> imagemCliente.setImage(imagemClienteBravo));
    try {
      Thread.sleep(900); // Espera 0.9 segundos
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      Platform.runLater(() -> controller.AnchorPane.getChildren().remove(imagemCliente));
      return; // Encerra a thread imediatamente
    }
    Platform.runLater(()-> imagemCliente.setImage(imagemClienteCostas));
    while (imagemCliente.getLayoutY() >= 136) {
      if (Thread.interrupted()) return;
      if (imagemCliente.getLayoutY() > 135) {
        Platform.runLater(() -> {
          imagemCliente.setLayoutY(imagemCliente.getLayoutY() - 2); // Move o cliente para cima
        });
      }
      sleepThread();
    }
    Platform.runLater(()-> { 
      this.imagemCliente.setVisible(false);
    });
  }
  /* ***************************************************************
  * Metodo: run
  * Funcao: Metodo que define o comportamento da thread Cliente.
    Ele simula o processo de um puffle entrando na sala de espera, 
    aguardando para ser atendido pelo barbeiro e saindo se a sala estiver cheia.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  @Override
  public void run() {
    try {
      // Solucao do problema classico "Barbeiro Dorminhoco"
      clienteEntrando();
      ControllerTelaSimulacao.mutex.acquire(); // O puffle entra na regiao critica
      if (ControllerTelaSimulacao.esperando < ControllerTelaSimulacao.CADEIRAS) { // Verifica se ha cadeiras disponiveis
        ControllerTelaSimulacao.esperando++; // Incrementa o contador de clientes esperando
        colocarClienteNaFila(); // Coloca o cliente na fila
        ControllerTelaSimulacao.clientes.release(); // Libera o semaforo de clientes para acordar o barbeiro
        ControllerTelaSimulacao.mutex.release(); // Libera a regiao critica
        ControllerTelaSimulacao.barbeiro.acquire(); // Espera pelo barbeiro para cortar o cabelo
        removerClienteDaFila();
        clienteSendoAtendido();
        clienteSaindoSatisfeito();
      } else {
        ControllerTelaSimulacao.mutex.release(); // Libera a regiao critica
        clienteSaindoBravo();
      }
      ControllerTelaSimulacao.todosClientes.remove(this); // Remove o cliente da lista de todos os clientes
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.println("Cliente interrompido.");
    } finally {
      ControllerTelaSimulacao.todosClientes.remove(this);
    }
  }
}