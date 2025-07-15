/* ***************************************************************
* Autor: Gabriel Marcone Magalhaes Santos
* Matricula........: 202410374
* Inicio...........: 09/06/2025
* Ultima alteracao.: 15/06/2025
* Nome.............: GeradorDeClientes.java
* Funcao...........: Criar e configurar a Thread que ira gerar clientes .
*************************************************************** */
package model;

import controller.ControllerTelaSimulacao;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class GeradorDeClientes extends Thread {
  private ImageView imagemCliente;
  private ControllerTelaSimulacao controller; // Controlador da tela de simulacao
  private Slider sliderVelocidade; // Slider para controlar a velocidade do gerador de clientes
  /* ***************************************************************
  * Metodo: GeradorDeClientes
  * Funcao: Construtor da classe GeradorDeClientes. Ele inicializa a thread que gera clientes.
  * Parametros: ControllerTelaSimulacao controller: Controlador da tela de simulacao.
    Slider sliderVelocidade: Slider para controlar a velocidade do gerador de clientes.
    ImageView imagemCliente: Imagem que representa o cliente na interface.
  * Retorno: void
  *************************************************************** */
  public GeradorDeClientes(ControllerTelaSimulacao controller, Slider sliderVelocidade, ImageView imagemCliente) {
    this.controller = controller; // Inicializa o controlador da tela de simulacao
    this.sliderVelocidade = sliderVelocidade; // Inicializa o slider de velocidade
    this.imagemCliente = imagemCliente;
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
  * Metodo: run
  * Funcao: Metodo que define o comportamento da thread GeradorDeClientes.
    Ele cria novos clientes continuamente, iniciando uma nova thread para cada cliente.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  @Override
  public void run() {
    while (!this.isInterrupted()) { // Loop infinito para gerar clientes continuamente
      try {
      // Verifica se o gerador de clientes esta pausado
      while (ControllerTelaSimulacao.clientesPausados) {
        Thread.sleep(100); // Espera 100ms e checa de novo
      }
      Cliente cliente = new Cliente(controller, imagemCliente, sliderVelocidade); // Cria um novo cliente
      cliente.setDaemon(true); // Define a thread do cliente como daemon, para que ela possa ser finalizada quando a aplicacao for encerrada
      cliente.start(); // Inicia a thread do cliente
      
      double valorSlider = getVelocidade();
      int tempoEspera = (int) (Math.random() * 8000 / valorSlider); // Calcula o tempo de espera baseado no valor do slider
      Thread.sleep(tempoEspera); // Faz a thread dormir por um tempo aleatorio baseado no slider de velocidade
      } catch (InterruptedException e) {
        System.out.println("Gerador de clientes interrompido.");
        break; // Sai do loop se a thread for interrompida
      }
    }
  }
}